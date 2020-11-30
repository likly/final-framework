package org.ifinal.finalframework.auto.data;

import lombok.NonNull;
import org.ifinal.finalframework.annotation.IEnum;
import org.ifinal.finalframework.annotation.data.*;
import org.ifinal.finalframework.auto.coding.beans.PropertyDescriptor;
import org.ifinal.finalframework.auto.coding.utils.Annotations;
import org.ifinal.finalframework.auto.coding.utils.TypeElements;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.Optionals;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;
import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationProperty implements Property {

    /**
     * 虚拟列前缀
     */
    private static final String VIRTUAL_PREFIX = "v_";
    private final ProcessingEnvironment processEnv;
    private final Elements elements;
    private final Types types;
    private final TypeElements typeElements;
    private final Lazy<Element> element;
    private final Lazy<TypeElement> typeHandler;
    private final Optional<VariableElement> field;
    private final Optional<PropertyDescriptor> descriptor;
    private final Optional<ExecutableElement> readMethod;
    private final Optional<ExecutableElement> writeMethod;
    private final Lazy<String> name;
    private final Lazy<String> column;
    private final Lazy<TypeMirror> type;
    private final Lazy<Boolean> isKeyword;
    private final Lazy<Boolean> isIdProperty;
    private final Lazy<Boolean> isReference;
    private final Lazy<Boolean> isVersion;
    private final Lazy<Boolean> isDefault;
    private final Lazy<Boolean> isFinal;
    private final Lazy<Boolean> isVirtual;
    private final Lazy<Boolean> isSharding;
    private final Lazy<Boolean> isWriteOnly;
    private final Lazy<Boolean> isReadOnly;
    private final Lazy<Boolean> isTransient;
    private final Lazy<Boolean> isCollection;
    private final Lazy<Boolean> isMap;
    private TypeElement javaTypeElement;
    private boolean placeholder = true;
    private List<String> referenceProperties;
    private ReferenceMode referenceMode;
    private Map<String, String> referenceColumns;

    public AnnotationProperty(ProcessingEnvironment processEnv, Optional<VariableElement> field,
                              Optional<PropertyDescriptor> descriptor) {
        this.processEnv = processEnv;
        this.elements = processEnv.getElementUtils();
        this.types = processEnv.getTypeUtils();
        this.typeElements = new TypeElements(processEnv.getTypeUtils(), processEnv.getElementUtils());
        this.field = field;
        this.descriptor = descriptor;

        if (descriptor.isPresent()) {
            this.readMethod = descriptor.get().getGetter();
            this.writeMethod = descriptor.get().getSetter();
        } else {
            this.readMethod = Optional.empty();
            this.writeMethod = Optional.empty();
        }

        this.element = Lazy.of(() -> withFieldOrMethod(Function.identity(), Function.identity(), Function.identity()));
        this.typeHandler = Lazy.of(this::initTypeHandler);
        this.name = Lazy
                .of(() -> withFieldOrDescriptor(it -> it.getSimpleName().toString(), PropertyDescriptor::getName));

        this.type = Lazy.of(() ->
                withFieldOrMethod(
                        Element::asType,
                        setter -> setter.getParameters().get(0).asType(),
                        ExecutableElement::getReturnType
                )
        );

        this.isCollection = Lazy.of(() -> isCollection(getType()));
        this.isMap = Lazy.of(() -> isMap(getType()));

        this.isTransient = Lazy.of(hasAnnotation(Transient.class));
        this.isIdProperty = Lazy.of(!isTransient() && hasAnnotation(PrimaryKey.class));
        this.isFinal = Lazy.of(!isTransient() && isAnnotationPresent(Final.class));
        this.isVirtual = Lazy.of(!isTransient() && isAnnotationPresent(Virtual.class));
        this.isSharding = Lazy.of(!isTransient() && hasAnnotation(Sharding.class));
        this.isReference = Lazy.of(!isTransient() && hasAnnotation(Reference.class));
        this.isVersion = Lazy.of(!isTransient() && isAnnotationPresent(Version.class));
        this.isDefault = Lazy.of(!isTransient() && isAnnotationPresent(Default.class));
        this.isWriteOnly = Lazy.of(!isTransient() && !isVirtual() && isAnnotationPresent(WriteOnly.class));
        this.isReadOnly = Lazy.of(!isTransient() && !isVirtual() && isAnnotationPresent(ReadOnly.class));



        PropertyJavaTypeVisitor propertyJavaTypeVisitor = new PropertyJavaTypeVisitor(processEnv);
        this.javaTypeElement = getType().accept(propertyJavaTypeVisitor, this);

        if (isReference()) {
            initReferenceColumn(getAnnotation(Reference.class));
        }
        this.column = Lazy.of(() -> isVirtual() ? VIRTUAL_PREFIX + initColumn() : initColumn());

        this.isKeyword = Lazy
                .of(() -> !isTransient() && (hasAnnotation(Keyword.class) || SqlKeyWords.contains(getColumn())));


    }

    private String initColumn() {
        List<? extends AnnotationMirror> annotationMirrors = getElement().getAnnotationMirrors();
        TypeElement column = typeElements.getTypeElement(Column.class);
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            if (typeElements.isSameType(annotationType, column.asType())) {
                return getColumnValue(annotationMirror);
            } else if (annotationType.asElement().getAnnotation(Column.class) != null) {
                return getColumnValue(annotationMirror);
            }
        }

        return getName();
    }

    private String getColumnValue(AnnotationMirror annotation) {
        Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotation.getElementValues();
        String realColumn = "";
        String value = "";
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
            ExecutableElement method = entry.getKey();
            AnnotationValue annotationValue = entry.getValue();
            if ("name".equals(method.getSimpleName().toString())) {
                realColumn = (String) annotationValue.getValue();
            } else if ("value".equals(method.getSimpleName().toString())) {
                value = (String) annotationValue.getValue();
            }
        }

        return Stream.of(value, realColumn).filter(Asserts::nonBlank).findFirst().orElse(getName());
    }

    private TypeElement initTypeHandler() {
        return null;
    }


    private void initReferenceColumn(Reference ann) {
        initReference(ann.mode(), ann.properties(), ann.delimiter());
    }

    private void initReference(ReferenceMode mode, String[] properties, String delimiter) {
        this.referenceMode = mode;
        List<String> referenceProperties = new ArrayList<>(properties.length);
        Map<String, String> referenceColumns = new HashMap<>(properties.length);
        for (String property : properties) {
            if (property.contains(delimiter)) {
                final String[] split = property.split(delimiter);
                referenceProperties.add(split[0]);
                referenceColumns.put(split[0], split[1]);
            } else {
                referenceProperties.add(property);
            }
        }
        this.referenceProperties = referenceProperties;
        this.referenceColumns = referenceColumns;
    }

    @Override
    @org.springframework.lang.NonNull
    public Element getElement() {
        return this.element.get();
    }


    @Override
    public String getName() {
        return name.get();
    }

    @Override
    public String getColumn() {
        return column.get();
    }



    @Override
    public boolean isDefault() {
        return isDefault.get();
    }

    @Override
    public boolean isFinal() {
        return isFinal.get();
    }

    @Override
    public boolean isVirtual() {
        return isVirtual.get();
    }


    @Override
    public boolean isReadOnly() {
        return isReadOnly.get();
    }

    @Override
    public boolean isWriteOnly() {
        return isWriteOnly.get();
    }


    @Override
    public TypeMirror getType() {
        return type.get();
    }

    @Override
    public TypeElement getJavaTypeElement() {
        return javaTypeElement;
    }


    @Override
    public boolean isKeyword() {
        return isKeyword.get();
    }

    @Override
    public boolean isIdProperty() {
        return isIdProperty.get();
    }

    @Override
    public boolean isVersion() {
        return isVersion.get();
    }


    @Override
    public boolean isCollection() {
        return isCollection.get();
    }


    @Override
    public boolean isMap() {
        return isMap.get();
    }


    @Override
    public boolean isReference() {
        return isReference.get();
    }

    @Override
    public ReferenceMode referenceMode() {
        return referenceMode;
    }

    @Override
    public List<String> referenceProperties() {
        return referenceProperties;
    }

    @Override
    public String referenceColumn(String property) {
        return referenceColumns == null ? null : referenceColumns.get(property);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return getElement().getAnnotation(annotationType);
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return Annotations.isAnnotationPresent(getElement(), annotationType);
    }


    @Override
    public boolean isTransient() {
        return isTransient != null && Boolean.TRUE.equals(isTransient.get());
    }

    private <T> T withFieldOrDescriptor(Function<? super VariableElement, T> field,
                                        Function<? super PropertyDescriptor, T> descriptor) {

        return Optionals.firstNonEmpty(//
                () -> this.field.map(field), //
                () -> this.descriptor.map(descriptor))//
                .orElseThrow(
                        () -> new IllegalStateException("Should not occur! Either field or descriptor has to be given"));
    }

    private <T> T withFieldOrMethod(Function<? super VariableElement, T> field,
                                    Function<? super ExecutableElement, T> setter,
                                    Function<? super ExecutableElement, T> getter
    ) {

        return Optionals.firstNonEmpty(//
                () -> this.field.map(field), //
                () -> this.writeMethod.map(setter),
                () -> this.readMethod.map(getter))//
                .orElseThrow(
                        () -> new IllegalStateException("Should not occur! Either field or descriptor has to be given"));
    }

    private boolean isEnum(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(IEnum.class).asType());
    }

    private boolean isCollection(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(Collection.class).asType());
    }

    private boolean isList(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(List.class).asType());
    }

    private boolean isSet(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(Set.class).asType());
    }

    private boolean isMap(TypeMirror type) {
        return types.isAssignable(types.erasure(type), getTypeElement(Map.class).asType());
    }


    private TypeElement getPrimitiveTypeElement(TypeKind kind) {
        switch (kind) {
            case BOOLEAN:
                return getTypeElement(Boolean.class);
            case BYTE:
                return getTypeElement(Byte.class);
            case SHORT:
                return getTypeElement(Short.class);
            case INT:
                return getTypeElement(Integer.class);
            case LONG:
                return getTypeElement(Long.class);
            case CHAR:
                return getTypeElement(Character.class);
            case FLOAT:
                return getTypeElement(Float.class);
            case DOUBLE:
                return getTypeElement(Double.class);
        }

        throw new IllegalArgumentException("不支持的类型：" + kind);
    }


    private TypeElement getTypeElement(Class<?> type) {
        return elements.getTypeElement(type.getCanonicalName());
    }


    private static final class PropertyJavaTypeVisitor extends SimpleTypeVisitor8<TypeElement, AnnotationProperty> {

        @NonNull
        private final ProcessingEnvironment pv;
        @NonNull
        private final Elements elements;
        @NonNull
        private final Types types;

        public PropertyJavaTypeVisitor(@NonNull ProcessingEnvironment pv) {
            this.pv = pv;
            this.elements = pv.getElementUtils();
            this.types = pv.getTypeUtils();

        }


        /**
         * Represents a primitive type.  These include {@code boolean}, {@code byte}, {@code short}, {@code int}, {@code
         * long}, {@code char}, {@code float}, and {@code double}.
         */
        @Override
        public TypeElement visitPrimitive(PrimitiveType type, AnnotationProperty property) {
            return property.getPrimitiveTypeElement(type.getKind());
        }

        @Override
        public TypeElement visitArray(ArrayType array, AnnotationProperty property) {
            TypeMirror type = array.getComponentType();
            TypeKind kind = type.getKind();
            if (kind.isPrimitive()) {
                return property.getPrimitiveTypeElement(kind);
            }

            if (type instanceof ArrayType || property.isCollection(type)) {
                throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
            }

            if (type instanceof DeclaredType) {
                DeclaredType declaredType = (DeclaredType) type;
                Element element = declaredType.asElement();
                if (element instanceof TypeElement) {
                    return (TypeElement) element;
                }
            }

            throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
        }

        @Override
        public TypeElement visitDeclared(DeclaredType type, AnnotationProperty property) {
            if (property.isMap()) {
                return property.getTypeElement(Map.class);
            }

            if (property.isCollection()) {
                TypeMirror elementType = type.getTypeArguments().get(0);
                TypeKind kind = elementType.getKind();
                if (kind.isPrimitive()) {
                    return property.getPrimitiveTypeElement(kind);
                }
                if (TypeKind.DECLARED == kind && !property.isCollection(elementType)) {
                    DeclaredType declaredType = (DeclaredType) elementType;
                    Element element = declaredType.asElement();
                    if (element instanceof TypeElement) {
                        return (TypeElement) element;
                    }
                }

            }

            Element element = type.asElement();
            if (element instanceof TypeElement) {
                return (TypeElement) element;
            }

            throw new IllegalArgumentException("不支持的Array类型：[]" + type.toString());
        }

    }
}
