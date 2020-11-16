package org.finalframework.boot.autoconfigure.web.response;

import org.finalframework.auto.spring.factory.annotation.SpringAutoConfiguration;
import org.finalframework.web.response.advice.ResponsibleResponseBodyAdvice;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/26 20:37:12
 * @see ResponsibleResponseBodyAdvice
 * @since 1.0
 */
@Configuration
@SpringAutoConfiguration
@ConditionalOnClass(ResponsibleResponseBodyAdvice.class)
@EnableConfigurationProperties(ResponseProperties.class)
public class ResponseAutoConfiguration implements ApplicationContextAware {


    private final ResponseProperties properties;

    public ResponseAutoConfiguration(ResponseProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Optional.of(applicationContext.getBean(ResponsibleResponseBodyAdvice.class))
                .ifPresent(it -> it.setSyncStatus(properties.isSyncStatus()));
    }
}
