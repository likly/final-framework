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

package org.finalframework.document.api.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.finalframework.core.Assert;
import org.finalframework.document.api.service.EnumService;
import org.finalframework.document.api.service.query.EnumQuery;
import org.finalframework.util.Classes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Service;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-10 22:03:08
 * @since 1.0
 */
@Service
public class EnumServiceImpl implements EnumService {
    public static final Logger logger = LoggerFactory.getLogger(EnumServiceImpl.class);

    private final Map<String, Class<?>> enums = SpringFactoriesLoader.loadFactoryNames(Enum.class, getClass().getClassLoader())
            .stream()
            .collect(Collectors.toMap(Function.identity(), Classes::forName));

    @Override
    public List<Class<?>> query(EnumQuery query) {
        return enums.values()
                .stream()
                .filter(clazz -> {
                    if (Assert.nonEmpty(query.getName())) {
                        return clazz.getCanonicalName().toUpperCase().contains(query.getName().toUpperCase());
                    } else {
                        return true;
                    }


                })
                .collect(Collectors.toList());
    }
}

