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

package org.finalframework.test.jvm;


/**
 * @author likly
 * @version 1.0
 * @date 2020-08-10 13:53:22
 * @since 1.0
 */
public class MinorGC {
    private static final int MB = 1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     *
     * @param args
     */
    public static void main(String[] args) {
        byte[] allocation1 = new byte[2 * MB];
        byte[] allocation2 = new byte[2 * MB];
        byte[] allocation3 = new byte[2 * MB];
        // 出现一次Moinor GC
        byte[] allocation4 = new byte[4 * MB];
    }
}

