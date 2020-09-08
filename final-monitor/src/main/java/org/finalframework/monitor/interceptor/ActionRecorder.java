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

package org.finalframework.monitor.interceptor;


import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.core.Asserts;
import org.finalframework.monitor.action.Action;
import org.finalframework.monitor.action.ActionListener;
import org.finalframework.monitor.executor.Recorder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:53:45
 * @since 1.0
 */
@Primary
@SpringComponent
public class ActionRecorder implements Recorder<Object> {

    private final List<ActionListener> listeners = new ArrayList<>();

    public ActionRecorder(ObjectProvider<List<ActionListener>> handlerProvider) {
        final List<ActionListener> handlers = handlerProvider.getIfAvailable();
        if (Asserts.nonEmpty(handlers)) {
            this.listeners.addAll(handlers);
        }
    }

    @Override
    public void record(Action<?> action) {
        if (Asserts.nonEmpty(listeners)) {
            for (ActionListener listener : listeners) {
                listener.handle(action);
            }
        }
    }

}
