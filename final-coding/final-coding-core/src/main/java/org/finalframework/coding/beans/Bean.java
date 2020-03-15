package org.finalframework.coding.beans;


import org.finalframework.core.Streamable;
import org.finalframework.core.generator.NameGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import java.util.*;
import java.util.stream.Stream;

/**
 * start with {@link Bean#from(ProcessingEnvironment, TypeElement)}
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-15 13:50:18
 * @since 1.0
 */
public class Bean implements Streamable<PropertyDescriptor>, Iterable<PropertyDescriptor> {
    static final String ADD_PREFIX = "add";
    static final String REMOVE_PREFIX = "remove";
    static final String GET_PREFIX = "get";
    static final String SET_PREFIX = "set";
    static final String IS_PREFIX = "is";

    private final ProcessingEnvironment env;
    private final TypeElement typeElement;
    private final SetterAndGetterFilter setterAndGetterFilter;

    private final Map<String, VariableElement> fields = new HashMap<>();
    private final List<ExecutableElement> methods = new ArrayList<>();
    private final Map<ExecutableElement, Boolean> processed = new HashMap<>();
    private final Map<String, PropertyDescriptor> properties = new HashMap<>();

    private Bean(ProcessingEnvironment env, TypeElement typeElement) {
        this.env = env;
        this.typeElement = typeElement;
        this.setterAndGetterFilter = new SetterAndGetterFilter(env);
        this.init();
    }

    public static Bean from(ProcessingEnvironment env, TypeElement typeElement) {
        return new Bean(env, typeElement);
    }

    private void init() {
        List<? extends Element> elements = typeElement.getEnclosedElements();
        // init fields
        initFields(elements);
        // init methods
        initMethods(elements);
        // init properties
        initProperties();

    }

    private void initFields(List<? extends Element> elements) {
        ElementFilter.fieldsIn(elements).forEach(field -> fields.put(field.getSimpleName().toString(), field));
    }

    private void initMethods(List<? extends Element> elements) {
        ElementFilter.methodsIn(elements)
                .stream()
                .filter(method -> setterAndGetterFilter.matches(method, null))
                .forEach(methods::add);
    }

    private void initProperties() {

        // for each field
        fields.forEach((key, field) -> {
            this.properties.put(key, new PropertyDescriptor(this, Optional.of(field),
                    findSetter(key), findGetter(key)));
        });


        methods.stream()
                .filter(it -> Boolean.FALSE.equals(processed.get(it)))
                .filter(setterAndGetterFilter::isSetter)
                .forEach(setter -> {
                    String setterName = setter.getSimpleName().toString();
                    if (setterName.startsWith(SET_PREFIX)) {
                        String propertyName = setterName.substring(SET_PREFIX.length());
                        if (!properties.containsKey(propertyName)) {
                            this.properties.put(propertyName, new PropertyDescriptor(this, Optional.empty(),
                                    Optional.of(setter), findGetter(propertyName)));
                        }

                        if (properties.get(propertyName).getSetter() != setter) {
                            throw new IllegalArgumentException("");
                        }
                    }
                });

        methods.stream()
                .filter(it -> Boolean.FALSE.equals(processed.get(it)))
                .filter(setterAndGetterFilter::isGetter)
                .forEach(getter -> {
                    String getterName = getter.getSimpleName().toString();
                    String propertyName;
                    if (getterName.startsWith(IS_PREFIX)) {
                        propertyName = NameGenerator.decapitalize(getterName, IS_PREFIX);
                    } else if (getterName.startsWith(GET_PREFIX)) {
                        propertyName = NameGenerator.decapitalize(getterName, GET_PREFIX);
                    } else {
                        throw new IllegalArgumentException("不支持的 getter 方法" + getter.toString());
                    }

                    if (!properties.containsKey(propertyName)) {
                        this.properties.put(propertyName, new PropertyDescriptor(this, Optional.empty(),
                                findSetter(propertyName), Optional.of(getter)));
                    }

                    if (properties.get(propertyName).getGetter() != getter) {
                        throw new IllegalArgumentException("");
                    }
                });

    }

    private Optional<ExecutableElement> findSetter(String property) {
        String setterName = NameGenerator.capitalize(SET_PREFIX, property);
        Optional<ExecutableElement> setter = methods.stream()
                .filter(setterAndGetterFilter::isSetter)
                .filter(it -> it.getSimpleName().toString().equals(setterName))
                .findFirst();

        if (setter.isPresent()) {
            // TODO check parameter type
            processed.put(setter.get(), true);
        }

        return setter;
    }

    private Optional<ExecutableElement> findGetter(String property) {
        Optional<ExecutableElement> getter = methods.stream()
                .filter(setterAndGetterFilter::isGetter)
                .filter(it -> it.getSimpleName().toString()
                        .equals(NameGenerator.capitalize(GET_PREFIX, property)))
                .findFirst();

        if (getter.isPresent()) {
            // TODO check return type
            processed.put(getter.get(), true);
            return getter;
        }

        Optional<ExecutableElement> nextGetter = methods.stream()
                .filter(setterAndGetterFilter::isGetter)
                .filter(it -> it.getSimpleName().toString()
                        .equals(NameGenerator.capitalize(IS_PREFIX, property)))
                .findFirst();

        if (nextGetter.isPresent()) {
            // TODO check return type
            processed.put(nextGetter.get(), true);
            return nextGetter;
        }

        return Optional.empty();
    }


    @Override
    public Iterator<PropertyDescriptor> iterator() {
        return properties.values().iterator();
    }

    @Override
    public Stream<PropertyDescriptor> stream() {
        return properties.values().stream();
    }
}

