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
package org.apache.commons.functor.core.collection;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.core.Constant;
import org.junit.Test;

/**
 * @version $Revision: 1363514 $ $Date: 2012-07-19 17:13:49 -0300 (Thu, 19 Jul 2012) $
 */
public class TestIsElementOf extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new IsElementOf<Integer, Collection<Integer>>();
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test
    public void testTestCollection() throws Exception {
        List<Integer> list = new ArrayList<Integer>();
        list.add(new Integer(5));
        list.add(new Integer(10));
        list.add(new Integer(15));

        UnaryPredicate<Integer> p = IsElementOf.instance(list);
        assertTrue(p.test(new Integer(5)));
        assertTrue(p.test(new Integer(10)));
        assertTrue(p.test(new Integer(15)));

        assertTrue(!p.test(new Integer(4)));
        assertTrue(!p.test(new Integer(11)));

    }

    @Test
    public void testTestArray() throws Exception {
        int[] list = new int[] { 5, 10, 15 };

        UnaryPredicate<Integer> p = IsElementOf.instance(list);
        assertTrue(p.test(new Integer(5)));
        assertTrue(p.test(new Integer(10)));
        assertTrue(p.test(new Integer(15)));

        assertTrue(!p.test(new Integer(4)));
        assertTrue(!p.test(new Integer(11)));
    }

    @Test
    public void testTestArrayWithNull() throws Exception {
        assertTrue(! IsElementOf.instance().test(null,new int[] { 5, 10, 15 }));
        assertTrue(IsElementOf.instance().test(null,new Integer[] { new Integer(5), null, new Integer(15) }));
        assertTrue(IsElementOf.instance().test(new Integer(15),new Integer[] { new Integer(5), null, new Integer(15) }));
    }

    @Test
    public void testWrapNull() {
        try {
            IsElementOf.instance(null);
            fail("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void testWrapNonCollection() {
        try {
            IsElementOf.instance(new Integer(3));
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test(expected = NullPointerException.class)
    public void testTestNull() {
        IsElementOf.instance().test(new Integer(5),null);
    }

    @Test
    public void testTestNonCollection() {
        try {
            IsElementOf.instance().test(new Integer(5),new Long(5));
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testEquals() throws Exception {
        IsElementOf<Integer, Collection<Integer>> p1 = new IsElementOf<Integer, Collection<Integer>>();
        assertObjectsAreEqual(p1, p1);
        assertObjectsAreEqual(p1, new IsElementOf<Integer, Collection<Integer>>());
        assertObjectsAreEqual(p1, IsElementOf.instance());
        assertSame(IsElementOf.instance(), IsElementOf.instance());
        assertObjectsAreNotEqual(p1, Constant.falsePredicate());
    }
}
