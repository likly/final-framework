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
import org.finalframework.data.query.operation.function.ConcatFunctionOperation;
import org.finalframework.data.query.operation.function.LowerFunctionOperation;
import org.finalframework.data.query.operation.function.UpperFunctionOperation;


/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public enum StringOperation implements Operation {
    CONCAT, LOWER, UPPER;

    public static Function concat(String prefix, String suffix) {
        return new ConcatFunctionOperation(prefix, suffix);
    }

    public static Function lower() {
        return new LowerFunctionOperation();
    }

    public static Function upper() {
        return new UpperFunctionOperation();
    }

}

