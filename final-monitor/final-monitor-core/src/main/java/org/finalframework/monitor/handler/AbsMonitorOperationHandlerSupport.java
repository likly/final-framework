package org.finalframework.monitor.handler;


import org.finalframework.core.Assert;
import org.finalframework.monitor.MonitorOperationExpressionEvaluator;
import org.finalframework.monitor.MonitorOperationHandlerSupport;
import org.finalframework.monitor.OperatorContext;
import org.finalframework.monitor.interceptor.DefaultMonitorOperationExpressionEvaluator;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.interceptor.AbsOperationHandlerSupport;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 11:39:29
 * @since 1.0
 */
public class AbsMonitorOperationHandlerSupport extends AbsOperationHandlerSupport implements MonitorOperationHandlerSupport {
    private final MonitorOperationExpressionEvaluator evaluator;

    public AbsMonitorOperationHandlerSupport() {
        this(new DefaultMonitorOperationExpressionEvaluator());
    }

    public AbsMonitorOperationHandlerSupport(MonitorOperationExpressionEvaluator evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public Object generateOperator(String operator, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
        if (Assert.nonBlank(operator)) {
            if (isExpression(operator)) {
                return evaluator.operator(generateExpression(operator), metadata.getMethodKey(), evaluationContext);
            }
            return operator;
        }

        return OperatorContext.get();
    }

    @Override
    public Object generateTarget(String target, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
        if (Assert.isBlank(target)) return null;
        if (isExpression(target)) {
            return evaluator.target(generateExpression(target), metadata.getMethodKey(), evaluationContext);
        }
        return target;
    }

    @Override
    public Object generateAttribute(String attribute, OperationMetadata<? extends Operation> metadata, EvaluationContext evaluationContext) {
        if (Assert.nonBlank(attribute) && isExpression(attribute)) {
            return evaluator.attribute(generateExpression(attribute), metadata.getMethodKey(), evaluationContext);
        }
        return attribute;
    }
}