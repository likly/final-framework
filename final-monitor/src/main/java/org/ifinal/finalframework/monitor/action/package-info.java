/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/**
 * Action模块，以AOP的方式实现各个功能点的调用记录，记录可写入日志，也可写入数据库或其他容器中。
 *
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.monitor.annotation.ActionMonitor
 * @see org.ifinal.finalframework.monitor.action.ActionListener
 * @since 1.0.0
 */

package org.ifinal.finalframework.monitor.action;
