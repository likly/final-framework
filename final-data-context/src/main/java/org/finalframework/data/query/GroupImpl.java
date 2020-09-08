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

package org.finalframework.data.query;


import org.finalframework.core.Asserts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-22 16:01:19
 * @since 1.0
 */
public class GroupImpl extends ArrayList<QProperty<?>> implements Group {

    private GroupImpl(Collection<QProperty<?>> properties) {
        this.addAll(properties);
    }

    public static Group by(Collection<QProperty<?>> properties) {
        return new GroupImpl(properties);
    }


    @Override
    public Group and(Group group) {
        Asserts.isNull(group, "Sort must not be null!");
        ArrayList<QProperty<?>> these = new ArrayList<>(this);

        for (QProperty<?> order : group) {
            these.add(order);
        }

        return GroupImpl.by(these);
    }

    @Override
    public Stream<QProperty<?>> stream() {
        return super.stream();
    }
}

