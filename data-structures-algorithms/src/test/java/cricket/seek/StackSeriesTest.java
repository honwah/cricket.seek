package cricket.seek;

import cricket.seek.StackSeries.StackViaArray;
import cricket.seek.StackSeries.StackViaList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;

/**
 * Unit test for StackSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*StackSeriesTest.StackViaListTest.class, StackSeriesTest.StackViaArrayTest.class*/})
public class StackSeriesTest {
    private static final StackSeries ss = new StackSeries();

    @FixMethodOrder(MethodSorters.NAME_ASCENDING)
    public static class StackViaArrayTest {
        private static StackViaArray sva;

        @BeforeClass
        public static void beforeClass() {
            sva = ss.new StackViaArray(5);
        }

        @Test
        public void test01push() throws Exception {
            boolean rtn = sva.push(5);
            Assert.assertTrue(rtn);
            sva.pop();
        }

        @Test
        public void test02pop() throws Exception {
            sva.push(5);
            sva.push(4);
            sva.push(3);
            sva.push(2);
            sva.push(1);
            int rtn = sva.pop();
            Assert.assertEquals(rtn, 1);
            rtn = sva.pop();
            Assert.assertEquals(rtn, 2);
            rtn = sva.pop();
            Assert.assertEquals(rtn, 3);
            rtn = sva.pop();
            Assert.assertEquals(rtn, 4);
            rtn = sva.pop();
            Assert.assertEquals(rtn, 5);
        }
    }

    @FixMethodOrder(MethodSorters.NAME_ASCENDING)
    public static class StackViaListTest {
        private static StackViaList svl;

        @BeforeClass
        public static void beforeClass() {
            svl = ss.new StackViaList();
        }

        @Test
        public void test01push() throws Exception {
            svl.push(5);
            svl.pop();
        }

        @Test
        public void test02pop() throws Exception {
            svl.push(5);
            svl.push(4);
            svl.push(3);
            svl.push(2);
            svl.push(1);
            int rtn = svl.pop();
            Assert.assertEquals(rtn, 1);
            rtn = svl.pop();
            Assert.assertEquals(rtn, 2);
            rtn = svl.pop();
            Assert.assertEquals(rtn, 3);
            rtn = svl.pop();
            Assert.assertEquals(rtn, 4);
            rtn = svl.pop();
            Assert.assertEquals(rtn, 5);
        }
    }
}