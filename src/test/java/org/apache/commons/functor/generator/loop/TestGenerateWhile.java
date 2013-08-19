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
package org.apache.commons.functor.generator.loop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.generator.Generator;
import org.apache.commons.functor.range.IntegerRange;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Generate While class.
 * @version $Revision: 1376354 $ $Date: 2012-08-23 00:04:55 -0300 (Thu, 23 Aug 2012) $
 */
public class TestGenerateWhile
{

    @Before
    public void setUp() throws Exception {
        wrappedGenerator = IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10));
        generateWhile = new GenerateWhile<Integer>(wrappedGenerator, isLessThanFive);
    }

    @After
    public void tearDown() {
        wrappedGenerator = null;
        isLessThanFive = null;
        generateWhile = null;
    }

    // Tests
    // ------------------------------------------------------------------------

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullUnaryPredicate() {
            new GenerateWhile<Integer>(generateWhile, null);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullWrappedGenerator() {
            new GenerateWhile<Integer>(null, isLessThanFive);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorProhibitsNullUnaryPredicateOrNullWrappedGenerator() {
            new GenerateWhile<Integer>(null, null);
    }

    @Test
    public void testEquals() {
        Generator<Integer> anotherGenerate = new GenerateWhile<Integer>(
                IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10)), isLessThanFive);
        assertEquals(generateWhile, generateWhile);
        assertEquals(generateWhile, anotherGenerate);
        assertTrue(!generateWhile.equals((GenerateWhile<Integer>)null));

		Generator<Integer> aGenerateWithADifferentPredicate = new GenerateWhile<Integer>(
	        IteratorToGeneratorAdapter.adapt(new IntegerRange(1, 10)), new UnaryPredicate<Integer>() {
				public boolean test(Integer obj) {
					return obj < FIVE;
				}
			});

        assertTrue(!generateWhile.equals(aGenerateWithADifferentPredicate));

        Generator<Integer> aGenerateWithADifferentWrapped = new GenerateWhile<Integer>(
                IteratorToGeneratorAdapter.adapt(new IntegerRange(1,11)), isLessThanFive);
        assertTrue(!generateWhile.equals(aGenerateWithADifferentWrapped));
    }

    @Test
    public void testHashcode() {
        assertEquals(generateWhile.hashCode(), generateWhile.hashCode());
        assertEquals(generateWhile.hashCode(), new GenerateWhile<Integer>(wrappedGenerator, isLessThanFive).hashCode());
    }

    // Attributes
    // ------------------------------------------------------------------------
    private static final Integer FIVE = new Integer(5);

    private LoopGenerator<Integer> wrappedGenerator = null;
    private UnaryPredicate<Integer> isLessThanFive = new UnaryPredicate<Integer>()
    {
        public boolean test( Integer obj ) {
            return obj < FIVE;
        }
    };
    private LoopGenerator<Integer> generateWhile = null;

}
