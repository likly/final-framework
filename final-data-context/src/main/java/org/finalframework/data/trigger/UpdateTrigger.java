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

import org.finalframework.annotation.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Update;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-02 22:12:04
 * @since 1.0
 */
public interface UpdateTrigger<ID extends Serializable, T extends IEntity<ID>> {

    void beforeUpdate(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query);

    void afterUpdate(String tableName, Class<?> view, T entity, Update update, boolean selective, Collection<ID> ids, Query query, int rows);

}
