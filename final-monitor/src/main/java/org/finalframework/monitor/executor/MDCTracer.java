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

package org.finalframework.monitor.executor;

import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.core.Asserts;
import org.finalframework.core.generator.TraceGenerator;
import org.finalframework.core.generator.UUIDTraceGenerator;
import org.finalframework.monitor.context.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Primary;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 16:58
 * @since 1.0
 */
@Primary
@SpringComponent
public class MDCTracer implements Tracer {
    private static final Logger logger = LoggerFactory.getLogger(MDCTracer.class);
    private static final String mdcTracer = "mdcTracer";

    private TraceGenerator traceGenerator = UUIDTraceGenerator.instance;

    @Override
    public void start(TraceContext context) {
        String trace = context.getTrace();
        String value = MDC.get(trace);
        if (Asserts.isBlank(value)) {
            value = traceGenerator.generate(null);
            MDC.put(trace, value);
            MDC.put(mdcTracer, Boolean.TRUE.toString());
            logger.info("put trace: trace={},value={}", trace, value);
        }
    }

    @Override
    public void stop(TraceContext context) {
        if (Boolean.TRUE.equals(Boolean.valueOf(MDC.get(mdcTracer)))) {
            String trace = context.getTrace();
            String value = MDC.get(trace);
            logger.info("remove trace: trace={},value={}", trace, value);
            MDC.remove(trace);
        }
    }
}
