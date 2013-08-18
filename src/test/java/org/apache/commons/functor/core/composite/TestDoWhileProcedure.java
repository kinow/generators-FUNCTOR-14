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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.Predicate;
import org.apache.commons.functor.Procedure;
import org.apache.commons.functor.adapter.BoundPredicate;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.apache.commons.functor.core.collection.IsEmpty;
import org.junit.Test;

/**
 * @version $Revision: 1345136 $ $Date: 2012-06-01 09:47:06 -0300 (Fri, 01 Jun 2012) $
 */
public class TestDoWhileProcedure extends BaseFunctorTest {

    // Functor Testing Framework
    // ------------------------------------------------------------------------

    @Override
    protected Object makeFunctor() {
        return new DoWhileProcedure(NoOp.INSTANCE, Constant.FALSE);
    }

    // Tests
    // ------------------------------------------------------------------------
    public class ListRemoveFirstProcedure implements Procedure {
        protected List<Object> list;


        public ListRemoveFirstProcedure(List<Object> list) {
            this.list=list;
        }


        public void run() {
            list.remove(0);
        }
    }


    private List<Object> getList() {
        List<Object> list = new LinkedList<Object>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        return list;
    }


    @Test
    public void testLoopWithAction() throws Exception {
        List<Object> list=getList();

        Procedure action=new ListRemoveFirstProcedure(list);
        Predicate condition=new Not(new BoundPredicate(new IsEmpty<List<Object>>(), list));
        Procedure procedure=new DoWhileProcedure(action, condition);

        assertTrue("The condition should be true before running the loop", condition.test());
        assertFalse("The list should not be empty then", list.isEmpty());
        procedure.run();
        assertFalse("The condition should be false after running the loop", condition.test());
        assertTrue("The list should be empty then", list.isEmpty());

        list=getList();
        action=new ListRemoveFirstProcedure(list);
        condition=new Predicate() {
                      private int count=2;

                      public boolean test() {
                          return count-- > 0;
                      }
                  };
        procedure=new DoWhileProcedure(action, condition);
        procedure.run();
        assertFalse("The list should not contain \"a\" anymore", list.contains("a"));
        assertFalse("The list should not contain \"b\" anymore", list.contains("b"));
        assertFalse("The list should not contain \"c\" anymore", list.contains("c"));
        assertTrue("The list should still contain \"d\"", list.contains("d"));
    }

    @Test
    public void testLoopForNothing() {
        List<Object> list=getList();
        Procedure action=new ListRemoveFirstProcedure(list);
        Procedure procedure=new DoWhileProcedure(action, Constant.FALSE);
        assertTrue("The list should contain 4 elements before runnning the loop", list.size()==4);
        procedure.run();
        assertTrue("The list should contain 3 elements after runnning the loop", list.size()==3);
    }
}
