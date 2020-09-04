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

package org.finalframework.data.annotation;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 可视图的，实现根据业务场景而展示不同的视图，主要结合{@link JsonView}使用。
 *
 * @author likly
 * @version 1.0
 * @date 2019-09-06 15:50:41
 * @see JsonView
 * @since 1.0
 */
public interface Viewable<T> {

    Class<?> getView();

    T getValue();

}
