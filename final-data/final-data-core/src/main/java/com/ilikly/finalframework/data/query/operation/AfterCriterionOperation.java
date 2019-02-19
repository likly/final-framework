package com.ilikly.finalframework.data.query.operation;

import com.ilikly.finalframework.data.query.CriterionOperations;
import com.ilikly.finalframework.data.query.QProperty;
import com.ilikly.finalframework.data.query.SingleCriterionOperation;

import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:52:04
 * @since 1.0
 */
public class AfterCriterionOperation extends AbsCriterionOperation<Date> implements SingleCriterionOperation<Date> {
    public static final AfterCriterionOperation INSTANCE = new AfterCriterionOperation();

    @Override
    public String name() {
        return CriterionOperations.AFTER.name();
    }

    @Override
    public String format(QProperty property, String operation, Date value) {
        final String column = getPropertyColumn(property);
        return String.format("%s > '%s'", column, format(value));
    }
}
