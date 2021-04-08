package org.ifinal.finalframework.mybatis.sql.provider;

import java.awt.Point;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.annotation.data.YN;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.ifinal.finalframework.query.Update;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class UpdateSqlProviderTest {

    /**
     * @throws NoSuchMethodException exception
     * @see UpdateSqlProvider#update(ProviderContext, Map)
     * @see AbsMapper#update(String, Class, IEntity, Update, boolean, Collection, IQuery)
     */
    @Test
    void update() throws NoSuchMethodException {

        final Method update = AbsMapper.class
            .getMethod("update", String.class, Class.class, IEntity.class, Update.class, boolean.class,
                Collection.class, IQuery.class);
        /*
         * @see ProviderSqlSource
         */
        final ProviderSqlSource providerSqlSource = new ProviderSqlSource(new Configuration(),
            update.getAnnotation(UpdateProvider.class), PersonMapper.class,
            update);
        final HashMap<String, Object> parameters = new HashMap<>();

        parameters.put("table", "person");
        parameters.put("view", null);
        parameters.put("selective", false);
//        Person person = new Person();
//        person.setAge(12);
//        person.setName("haha");
//        person.setYn(YN.NO);
//        parameters.put("entity", person);

        parameters.put("update",Update.update().set("name","haha"));

        final PersonQuery query = new PersonQuery();
        query.setLocation(new Point());
        query.setDistance(1L);
        parameters.put("query", query);

        final BoundSql boundSql = providerSqlSource.getBoundSql(parameters);

        final String sql = boundSql.getSql();
        logger.info(sql);
        Assertions.assertNotNull(sql);

    }

}
