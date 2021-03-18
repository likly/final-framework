package org.ifinal.finalframework.mybatis.sql.provider;

import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.data.query.QEntity;
import org.ifinal.finalframework.data.query.Query;
import org.ifinal.finalframework.data.query.QueryProvider;
import org.ifinal.finalframework.mybatis.sql.AbsMapperSqlProvider;

import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#selectIds(String, IQuery)
 * @since 1.0.0
 */
public class SelectIdsSqlProvider implements AbsMapperSqlProvider {

    public static final String QUERY_PARAMETER_NAME = "query";

    /**
     * @param context    context
     * @param parameters parameters
     * @return sql
     * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper#selectIds(String, IQuery)
     */
    @SuppressWarnings("unused")
    public String selectIds(final ProviderContext context, final Map<String, Object> parameters) {

        return provide(context, parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doProvide(final StringBuilder sql, final ProviderContext context,
        final Map<String, Object> parameters) {

        final Class<?> entity = getEntityClass(context.getMapperType());
        final QEntity<?, ?> properties = QEntity.from(entity);
        parameters.put("entity", properties);

        /*
         * <trim prefix="SELECT">
         *      columns
         * </trim>
         */
        sql.append("<trim prefix=\"SELECT\" suffixOverrides=\",\">");
        sql.append(properties.getIdProperty().getColumn());
        sql.append("</trim>");

        sql.append("<trim prefix=\"FROM\">")
            .append("${table}")
            .append("</trim>");

        Object query = parameters.get(QUERY_PARAMETER_NAME);

        if (query instanceof Query) {
            ((Query) query).apply(sql, QUERY_PARAMETER_NAME);
        } else if (query != null) {

            final QueryProvider provider = query(QUERY_PARAMETER_NAME, (Class<? extends IEntity<?>>) entity, query.getClass());

            if (Objects.nonNull(provider.where())) {
                sql.append(provider.where());
            }

            if (Objects.nonNull(provider.orders())) {
                sql.append(provider.orders());
            }

            if (Objects.nonNull(provider.limit())) {
                sql.append(provider.limit());
            }
        }
    }

}