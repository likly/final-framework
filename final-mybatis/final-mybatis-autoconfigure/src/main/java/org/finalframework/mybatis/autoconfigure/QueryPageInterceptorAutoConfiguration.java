package org.finalframework.mybatis.autoconfigure;

import org.apache.ibatis.session.SqlSessionFactory;
import org.finalframework.core.Assert;
import org.finalframework.mybatis.inteceptor.QueryPageInterceptor;
import org.finalframework.spring.coding.AutoConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 21:59:36
 * @since 1.0
 */
@AutoConfiguration
public class QueryPageInterceptorAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void addPageInterceptor() {
        Map<String, SqlSessionFactory> beansOfType = applicationContext.getBeansOfType(SqlSessionFactory.class);
        if (Assert.isEmpty(beansOfType)) return;
        QueryPageInterceptor interceptor = new QueryPageInterceptor();
        for (SqlSessionFactory sessionFactory : beansOfType.values()) {
            sessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}