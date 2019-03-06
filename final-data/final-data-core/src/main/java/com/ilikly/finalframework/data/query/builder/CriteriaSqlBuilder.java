package com.ilikly.finalframework.data.query.builder;


import com.ilikly.finalframework.data.query.*;
import com.ilikly.finalframework.data.query.enums.AndOr;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-06 14:59:55
 * @since 1.0
 */
public class CriteriaSqlBuilder implements SqlBuilder<Collection<Criteria>> {
    private static final CriterionOperationRegistry criterionOperationRegistry = CriterionOperationRegistry.getInstance();
    private final Collection<Criteria> criteria;

    public CriteriaSqlBuilder(Collection<Criteria> criteria) {
        this.criteria = criteria;
    }

    private static Class getPropertyJavaType(QProperty property) {
        return property.isCollectionLike() ? property.getComponentType() : property.getType();
    }

    @Override
    public String build() {
        return joinCriteria(criteria, AndOr.AND);
    }

    private String joinCriteria(Collection<Criteria> criteria, AndOr andOr) {
        final StringBuilder sb = new StringBuilder();
        if (criteria.size() > 1) {
            sb.append("(");
        }
        sb.append(
                criteria.stream().map(
                        it -> it.chain()
                                ? joinCriteria(it.stream().collect(Collectors.toList()), it.andOr())
                                : joinCriteriaSet(it.criterionStream().collect(Collectors.toList()), it.andOr()))
                        .collect(Collectors.joining(String.format(" %s ", andOr)))
        );

        if (criteria.size() > 1) {
            sb.append(")");
        }

        return sb.toString();
    }

    private String joinCriteriaSet(Collection<Criterion> criteriaSets, AndOr andOr) {
        final StringBuilder sb = new StringBuilder();

        if (criteriaSets.size() > 1) {
            sb.append("(");
        }

        sb.append(
                criteriaSets.stream()
                        .map(criterion -> {
                            final Class javaType = getPropertyJavaType(criterion.property());
                            CriterionOperation criterionOperation = criterionOperationRegistry.getCriterionOperation(criterion.operator(), javaType);
                            return criterionOperation.format(criterion);
                        })
                        .collect(Collectors.joining(String.format(" %s ", andOr.name())))
        );


        if (criteriaSets.size() > 1) {
            sb.append(")");
        }
        return sb.toString();
    }


}
