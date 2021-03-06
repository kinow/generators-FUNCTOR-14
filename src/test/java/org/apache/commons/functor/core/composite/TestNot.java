/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul 2012) $
 */
public class TestNot extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new Not(Constant.TRUE);
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTest() throws Exception {
        Predicate truePred = new Not(Constant.FALSE);
        assertTrue(truePred.test());
        Predicate falsePred = new Not(Constant.TRUE);
        assertFalse(falsePred.test());
    }

    @Test
    public void testEquals() throws Exception {
        Not p = new Not(Constant.TRUE);
        assertEquals(p,p);
        assertObjectsAreEqual(p,new Not(Constant.TRUE));
        assertObjectsAreEqual(p,Not.not(Constant.TRUE));
        assertObjectsAreNotEqual(p,new Not(Constant.FALSE));
        assertObjectsAreNotEqual(p,Constant.TRUE);
        assertTrue(!p.equals(null));
    }

    @Test
    public void testNotNull() throws Exception {
        assertNull(Not.not(null));
    }

    @Test
    public void testNotNotNull() throws Exception {
        assertNotNull(Not.not(Constant.TRUE));
    }
}
