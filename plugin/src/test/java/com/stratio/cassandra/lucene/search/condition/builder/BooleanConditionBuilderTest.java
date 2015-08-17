/*
 * Licensed to STRATIO (C) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  The STRATIO (C) licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.stratio.cassandra.lucene.search.condition.builder;

import com.stratio.cassandra.lucene.search.condition.BooleanCondition;
import com.stratio.cassandra.lucene.search.condition.Condition;
import org.junit.Test;

import static com.stratio.cassandra.lucene.search.SearchBuilders.all;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Class for testing {@link BooleanConditionBuilder}.
 *
 * @author Andres de la Pena {@literal <adelapena@stratio.com>}
 */
public class BooleanConditionBuilderTest extends AbstractConditionBuilderTest {

    @Test
    public void testBuild() {
        BooleanConditionBuilder builder = new BooleanConditionBuilder().boost(0.7f)
                                                                       .must(all())
                                                                       .must(all())
                                                                       .should(all(), all())
                                                                       .should(all(), all())
                                                                       .not(all(), all(), all())
                                                                       .not(all(), all(), all());
        BooleanCondition condition = builder.build();
        assertNotNull(condition);
        assertEquals(0.7f, condition.boost, 0);
        assertEquals(2, condition.must.size());
        assertEquals(4, condition.should.size());
        assertEquals(6, condition.not.size());
    }

    @Test
    public void testBuildDefaults() {
        BooleanConditionBuilder builder = new BooleanConditionBuilder();
        BooleanCondition condition = builder.build();
        assertNotNull(condition);
        assertEquals(Condition.DEFAULT_BOOST, condition.boost, 0);
        assertEquals(0, condition.must.size());
        assertEquals(0, condition.should.size());
        assertEquals(0, condition.not.size());
    }

    @Test
    public void testJsonSerialization() {
        BooleanConditionBuilder builder = new BooleanConditionBuilder().boost(0.7f).must(all());
        testJsonSerialization(builder, "{type:\"boolean\",boost:0.7,must:[{type:\"all\"}],should:[],not:[]}");
    }

    @Test
    public void testJsonSerializationDefaults() {
        BooleanConditionBuilder builder = new BooleanConditionBuilder();
        testJsonSerialization(builder, "{type:\"boolean\",must:[],should:[],not:[]}");
    }
}
