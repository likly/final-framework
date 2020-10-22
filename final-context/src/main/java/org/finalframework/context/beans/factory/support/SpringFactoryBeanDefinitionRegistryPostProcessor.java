package org.finalframework.context.beans.factory.support;


import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Load the custom bean into {@code spring} ioc context.
 *
 * @author likly
 * @version 1.0
 * @date 2019-11-06 18:27:39
 * @see SpringFactoriesLoader
 * @since 1.0
 */
public class SpringFactoryBeanDefinitionRegistryPostProcessor<T> implements BeanDefinitionRegistryPostProcessor {
    private final Class<T> factoryInterface;

    public SpringFactoryBeanDefinitionRegistryPostProcessor(Class<T> factoryInterface) {
        this.factoryInterface = factoryInterface;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) {
        List<String> factories = SpringFactoriesLoader.loadFactoryNames(factoryInterface, this.getClass().getClassLoader());
        for (String factory : factories) {
            try {
                Class<?> item = Class.forName(factory);
                BeanDefinitionReaderUtils.registerWithGeneratedName(new AnnotatedGenericBeanDefinition(item), registry);
            } catch (ClassNotFoundException e) {
                throw new ApplicationContextException(factory, e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) {
        // do nothing
    }
}

