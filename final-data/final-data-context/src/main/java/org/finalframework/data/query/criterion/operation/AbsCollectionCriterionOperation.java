package org.finalframework.data.query.criterion.operation;

import org.finalframework.data.query.criterion.CollectionCriterion;
import org.finalframework.data.query.criterion.CollectionCriterionOperation;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class AbsCollectionCriterionOperation<E> extends AbsCriterionOperation<Collection<E>> implements CollectionCriterionOperation<E> {

    @Override
    public final String format(CollectionCriterion<E> criterion) {
        return format(criterion.property(), criterion.functions(), criterion.operator(), criterion.value());
    }
}