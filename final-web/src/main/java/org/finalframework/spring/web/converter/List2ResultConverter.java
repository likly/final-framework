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

package org.finalframework.spring.web.converter;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.finalframework.annotation.result.Pagination;
import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.core.Assert;
import org.finalframework.core.converter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * @author likly
 * @version 1.0
 * @date 2020-07-21 17:39:57
 * @since 1.0
 */
public class List2ResultConverter<T> implements Converter<List<T>, Result<List<T>>> {

    public static final Logger logger = LoggerFactory.getLogger(List2ResultConverter.class);

    @Override
    public Result<List<T>> convert(List<T> source) {

        List<T> list = source instanceof Page ? ((Page<T>) source).getResult() : source;

        final Result<List<T>> result = R.success(list);

        if (source instanceof Page) {
            result.setPagination(buildPageInfo((Page<T>) source));
        }

        if (Assert.nonEmpty(list)) {
            final T entity = list.get(0);

//            if (entity instanceof IEntity) {
//                final QEntity<?, ?> properties = QEntity.from(entity.getClass());
//                result.setMetadata(
//                        properties.stream()
//                                .sorted()
//                                .map(property -> {
//                                    final Metadata metadata = new Metadata();
//
//                                    metadata.setName(property.getName());
//                                    metadata.setPath(property.getPath());
//                                    metadata.setHeader(property.getName());
//
//                                    return metadata;
//                                }).collect(Collectors.toList())
//                );
//            }


        }
        return result;
    }

    private Pagination buildPageInfo(Page<T> page) {
        final PageInfo<T> pageInfo = page.toPageInfo();
        final Pagination result = new Pagination();
        result.setPage(pageInfo.getPageNum());
        result.setSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setFirstPage(pageInfo.isIsFirstPage());
        result.setLastPage(pageInfo.isIsLastPage());
        return result;
    }
}
