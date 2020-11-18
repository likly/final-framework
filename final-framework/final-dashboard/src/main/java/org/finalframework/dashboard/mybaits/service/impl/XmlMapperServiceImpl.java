package org.finalframework.dashboard.mybaits.service.impl;

import org.finalframework.dashboard.mybaits.service.XmlMapperService;
import org.finalframework.dashboard.mybaits.service.query.XmlMapperQuery;
import org.finalframework.mybatis.sql.provider.SqlProviderHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 23:50:06
 * @since 1.0
 */
@Service
class XmlMapperServiceImpl implements XmlMapperService {

    @Override
    public String xml(XmlMapperQuery query) {
        try {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(SqlProviderHelper.PARAMETER_NAME_TABLE, query.getTable());
            parameters.put(SqlProviderHelper.PARAMETER_NAME_IGNORE, query.isIgnore());
            parameters.put(SqlProviderHelper.PARAMETER_NAME_SELECTIVE, query.isSelective());
            if (Objects.nonNull(query.getQuery())) {
                parameters.put(SqlProviderHelper.PARAMETER_NAME_QUERY, query.getQuery().getConstructor().newInstance());
            }

            return SqlProviderHelper.xml(query.getMapper(), query.getMethod(), parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
