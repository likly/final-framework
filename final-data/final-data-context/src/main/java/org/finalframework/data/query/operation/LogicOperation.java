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

package org.finalframework.data.query.operation;

import org.finalframework.data.query.operation.function.Function;
import org.finalframework.data.query.operation.function.AndFunctionOperation;
import org.finalframework.data.query.operation.function.NotFunctionOperation;
import org.finalframework.data.query.operation.function.OrFunctionOperation;
import org.finalframework.data.query.operation.function.XorFunctionOperation;

/**
 * 逻辑运算符
 *
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:24:48
 * @since 1.0
 */
public enum LogicOperation implements Operation {
    AND, OR, NOT, XOR;

    public static <T> Function and(T value) {
        return new AndFunctionOperation(value);
    }

    public static <T> Function or(T value) {
        return new OrFunctionOperation(value);
    }

    public static Function not() {
        return new NotFunctionOperation();
    }

    public static <T> Function xor(T value) {
        return new XorFunctionOperation(value);
    }


}
