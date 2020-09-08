/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.aop;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.interceptor.BaseOperationInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:01:13
 * @since 1.0
 */
public class OperationInterceptor implements MethodInterceptor, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperationInterceptor.class);

    private final OperationConfiguration configuration;

    public OperationInterceptor(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public final Object invoke(MethodInvocation invocation) throws Throwable {
        return invoke(new BaseOperationInvocation(configuration, invocation));
    }

    public final Object invoke(OperationInvocation invocation) throws Throwable {
        final Collection<OperationContext<Operation>> contexts = invocation.getOperationContexts();

        if (Asserts.isEmpty(contexts)) {
            return invocation.proceed();
        }
        final OperationInvocationHandler handler = configuration.getInvocationHandler();
        Object operationValue = handler.handleBefore(contexts);
        if (operationValue != null) {
            return operationValue;
        }

        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invocation.proceed();
        } catch (Throwable e) {
            throwable = e;
        }

        if (throwable == null) {
            handler.handleAfterReturning(contexts, returnValue);
        } else {
            handler.handleAfterThrowing(contexts, throwable);
        }

        handler.handleAfter(contexts, returnValue, throwable);

        if (throwable != null) {
            throw throwable;
        }

        return returnValue;

    }
}
