package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CollectionCriterionOperation;
import com.ilikly.finalframework.data.query.CriterionOperator;
import com.ilikly.finalframework.data.query.CriterionOperators;
import com.ilikly.finalframework.data.query.QProperty;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 14:25:39
 * @since 1.0
 */
public class InCriterionOperation<E> extends AbsCollectionCriterionOperation<E> implements CollectionCriterionOperation<E> {
    public static final InCriterionOperation INSTANCE = new InCriterionOperation();

    @Override
    public CriterionOperator operator() {
        return CriterionOperators.IN;
    }

    @Override
    public String format(QProperty property, CriterionOperator operator, Collection<E> value) {
        final String column = getPropertyColumn(property);
        return String.format("%s IN %s", column, buildInString(value));
    }
}
