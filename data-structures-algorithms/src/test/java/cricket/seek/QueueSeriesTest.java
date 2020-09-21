package cricket.seek;

import cricket.seek.QueueSeries.QueueViaStack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for QueueSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*QueueSeriesTest.QueueViaStackTest.class*/})
public class QueueSeriesTest {
    private static final QueueSeries qs = new QueueSeries();

    public static class QueueViaStackTest {
        @Test
        public void test() {
            QueueViaStack mp = qs.new QueueViaStack(5);
            boolean rtn = mp.offer(1);
            Assert.assertTrue(rtn);
            rtn = mp.offer(2);
            Assert.assertTrue(rtn);
            rtn = mp.offer(3);
            Assert.assertTrue(rtn);
            rtn = mp.offer(4);
            Assert.assertTrue(rtn);
            rtn = mp.offer(5);
            Assert.assertTrue(rtn);
            rtn = mp.offer(6);
            Assert.assertFalse(rtn);
            int size = 1;
            while (!mp.empty()) {
                int tmp = mp.peek();
                Assert.assertEquals(tmp, size);
                tmp = mp.poll();
                Assert.assertEquals(tmp, size);
                size++;
            }
        }
    }
}