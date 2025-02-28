/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hertzbeat.common.queue.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Map;
import org.apache.hertzbeat.common.entity.message.CollectRep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link InMemoryCommonDataQueue}
 */
class InMemoryCommonDataQueueTest {

    private InMemoryCommonDataQueue queue;

    @BeforeEach
    void setUp() {
        queue = new InMemoryCommonDataQueue();
    }

    @Test
    void testMetricsData() throws InterruptedException {

        var metricsData = CollectRep.MetricsData.newBuilder().build();

        queue.sendMetricsData(metricsData);

        CollectRep.MetricsData polledMetricsData = queue.pollMetricsDataToAlerter();

        assertNotNull(polledMetricsData);
        assertEquals(metricsData, polledMetricsData);
    }

    @Test
    void testGetQueueSizeMetricsInfo() {

        Map<String, Integer> metricsInfo = queue.getQueueSizeMetricsInfo();
        
        assertEquals(0, metricsInfo.get("metricsDataToAlertQueue"));
        assertEquals(0, metricsInfo.get("metricsDataToStorageQueue"));
        
        queue.sendMetricsData(CollectRep.MetricsData.newBuilder().build());

        metricsInfo = queue.getQueueSizeMetricsInfo();
        
        assertEquals(1, metricsInfo.get("metricsDataToAlertQueue"));
        assertEquals(0, metricsInfo.get("metricsDataToStorageQueue"));
    }

    @Test
    void testDestroy() {
        
        queue.sendMetricsData(CollectRep.MetricsData.newBuilder().build());

        queue.destroy();

        Map<String, Integer> metricsInfo = queue.getQueueSizeMetricsInfo();
        
        assertEquals(0, metricsInfo.get("metricsDataToAlertQueue"));
        assertEquals(0, metricsInfo.get("metricsDataToStorageQueue"));
    }

}
