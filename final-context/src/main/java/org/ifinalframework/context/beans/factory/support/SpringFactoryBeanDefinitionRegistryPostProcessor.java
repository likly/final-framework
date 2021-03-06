/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.context.beans.factory.support;

import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Registry {@link BeanDefinition} into {@link BeanDefinitionRegistry} from {@link SpringFactoriesLoader}.
 *
 * @author likly
 * @version 1.0.0
 * @see SpringFactoriesLoader
 * @since 1.0.0
 */
@Slf4j
@SuppressWarnings("unused")
public class SpringFactoryBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private final Class<?> springFactory;

    private final boolean supportCustom;

    public SpringFactoryBeanDefinitionRegistryPostProcessor(final Class<?> springFactory) {
        this(springFactory, false);
    }

    public SpringFactoryBeanDefinitionRegistryPostProcessor(final Class<?> springFactory, final boolean supportCustom) {
        this.springFactory = springFactory;
        this.supportCustom = supportCustom;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(final @NonNull BeanDefinitionRegistry registry) {

        if (supportCustom) {
            final HashSet<String> springFactories = new HashSet<>(
                SpringFactoriesLoader.loadFactoryNames(springFactory, registry.getClass().getClassLoader()));

            for (String annotationName : springFactories) {
                try {
                    final Class<?> factoryClass = Class.forName(annotationName);
                    doPostProcessBeanDefinitionRegistry(factoryClass, registry);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        } else {
            doPostProcessBeanDefinitionRegistry(springFactory, registry);
        }

    }

    private void doPostProcessBeanDefinitionRegistry(final Class<?> factoryClass, final BeanDefinitionRegistry registry) {
        final List<String> factories = SpringFactoriesLoader.loadFactoryNames(factoryClass, this.getClass().getClassLoader());
        for (String factory : factories) {
            try {
                final Class<?> item = Class.forName(factory);
                if (logger.isDebugEnabled()) {
                    logger.debug("register bean definition: {}", factory);
                }
                BeanDefinitionReaderUtils.registerWithGeneratedName(new AnnotatedGenericBeanDefinition(item), registry);
            } catch (ClassNotFoundException e) {
                throw new ApplicationContextException(factory, e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(final @NonNull ConfigurableListableBeanFactory beanFactory) {
        // do nothing
    }

}

