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

package org.finalframework.data.repository;


import org.finalframework.data.service.AbsService;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.beans.factory.ObjectProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-06 15:33:23
 * @since 1.0
 */
@SpringComponent
public class RepositoryManager {

    private static Map<Class<? extends Repository>, RepositoryHolder> repositoryHolders = new HashMap();

    public RepositoryManager(ObjectProvider<List<Repository<?, ?>>> repositories) {
        final List<Repository<?, ?>> list = repositories.getIfAvailable();
        list.stream()
                .filter(it -> !(it instanceof AbsService))
                .map(RepositoryHolder::from)
                .filter(Objects::nonNull)
                .forEach(it -> repositoryHolders.put(it.getRepositoryClass(), it));


        System.out.println();
    }

    public static RepositoryHolder from(Class<? extends Repository> repository) {
        return repositoryHolders.get(repository);
    }

}

