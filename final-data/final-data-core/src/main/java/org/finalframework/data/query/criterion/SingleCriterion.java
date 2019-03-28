package org.finalframework.data.query.criterion;

import org.finalframework.data.query.Criterion;
import org.finalframework.data.query.CriterionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.QProperty;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-19 20:49:00
 * @since 1.0
 * @see SingleCriterionOperation
 */
public interface SingleCriterion<T> extends Criterion<T> {

    static <T> SingleCriterion.Builder<T> builder() {
        return SingleCriterionImpl.builder();
    }

    T value();

    interface Builder<T> extends Criterion.Builder<SingleCriterion<T>> {

        @Override
        Builder<T> property(QProperty property);

        @Override
        Builder<T> function(FunctionCriterion function);

        @Override
        Builder<T> function(Collection<FunctionCriterion> functions);

        @Override
        Builder<T> operator(CriterionOperator operation);


        Builder<T> value(T value);
    }

}
