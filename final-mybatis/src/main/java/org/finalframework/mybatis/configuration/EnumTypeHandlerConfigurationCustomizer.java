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

package org.finalframework.mybatis.configuration;


import org.apache.ibatis.session.Configuration;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.mybatis.handler.EnumTypeHandler;
import org.finalframework.mybatis.lang.FinalXMLLanguageDriver;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-29 16:11:22
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 * @since 1.0
 */
@SpringComponent
@SuppressWarnings("unused")
public class EnumTypeHandlerConfigurationCustomizer implements ConfigurationCustomizer {
    private static final Logger logger = LoggerFactory.getLogger(EnumTypeHandlerConfigurationCustomizer.class);

    @Override
    public void customize(Configuration configuration) {
        logger.info("setDefaultEnumTypeHandler:{}", EnumTypeHandler.class.getCanonicalName());
        configuration.addMapper(AbsMapper.class);
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
        configuration.getLanguageRegistry().setDefaultDriverClass(FinalXMLLanguageDriver.class);
    }
}

