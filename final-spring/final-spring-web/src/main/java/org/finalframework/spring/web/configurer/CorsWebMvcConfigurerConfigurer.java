package org.finalframework.spring.web.configurer;

import org.finalframework.spring.annotation.factory.SpringWebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:29
 * @since 1.0
 */
@SpringWebMvcConfigurer
public class CorsWebMvcConfigurerConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }


}
