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

package org.finalframework.data.trigger;

import org.finalframework.data.annotation.IEntity;
import org.finalframework.data.query.Query;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-03 13:49:20
 * @see org.finalframework.data.repository.Repository#select(String, Class, Collection, Query)
 * @since 1.0
 */
public interface SelectTrigger<ID extends Serializable, T extends IEntity<ID>> extends Trigger {

    void beforeSelect(String tableName, Class<?> view, Collection<ID> ids, Query query);

    void afterSelect(String tableName, Class<?> view, Collection<ID> ids, Query query, Collection<T> entities);

}
