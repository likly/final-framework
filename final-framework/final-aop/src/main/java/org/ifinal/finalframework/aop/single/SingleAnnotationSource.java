package org.ifinal.finalframework.aop.single;

import org.ifinal.finalframework.aop.AnnotationBuilder;
import org.ifinal.finalframework.aop.AnnotationFinder;
import org.ifinal.finalframework.aop.AnnotationParser;
import org.ifinal.finalframework.aop.AnnotationSource;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleAnnotationSource<A extends Annotation, E> implements AnnotationSource<Collection<E>> {

    private final Map<Object, Collection<E>> cache = new ConcurrentHashMap<>(1024);

    private final AnnotationParser<Collection<E>> parser;

    public SingleAnnotationSource(final Class<A> annotationType, final AnnotationBuilder<A, E> builder) {

        this(new SingleAnnotationParser<>(annotationType, builder));
    }

    public SingleAnnotationSource(final AnnotationFinder<Collection<A>> finder, final AnnotationBuilder<A, E> builder) {

        this(new SingleAnnotationParser<>(finder, builder));
    }

    public SingleAnnotationSource(final AnnotationParser<Collection<E>> parser) {

        this.parser = parser;
    }

    @Override
    public Collection<E> getAnnotations(final Method method, final Class<?> targetClass) {

        if (method.getDeclaringClass() == Object.class) {
            return Collections.emptyList();
        }

        Object cacheKey = getCacheKey(method, targetClass);
        return this.cache.computeIfAbsent(cacheKey, key -> computeAnnotations(method, targetClass));
    }

    private Collection<E> computeAnnotations(final Method method, final @Nullable Class<?> targetClass) {

        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return Collections.emptyList();
        }

        List<E> annotations = new ArrayList<>();

        annotations.addAll(parser.parseAnnotations(targetClass));
        annotations.addAll(parser.parseAnnotations(method));


        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            annotations.addAll(parser.parseAnnotations(parameters[i], i));
        }

        return annotations;
    }

    protected boolean allowPublicMethodsOnly() {
        return false;
    }
}
