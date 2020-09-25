package cricket.seek;

import cricket.seek.QueueSeries.QueueViaStack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for QueueSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*QueueSeriesTest.SynchronousQueueLabTest.class*/})
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

    public static class StoreTest {

        @Test
        public void test() throws InterruptedException {
            Store s = Store.getInstance();
            Thread t1 = new Thread(s.new Customer());
            Thread t2 = new Thread(s.new Producer());
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }
    }

    public static class PriorityBlockingQueueLabTest {

        @Test
        public void test() throws InterruptedException {
            PriorityBlockingQueueLab pbl  = new PriorityBlockingQueueLab();
            Thread t1 = new Thread(pbl.new Producer());
            t1.start();
            t1.join();
            Thread t2 = new Thread(pbl.new Customer());
            t2.start();
            t2.join();
        }
    }

    public static class DelayedQueueLabTest {

        @Test
        public void test() throws InterruptedException {
            DelayedQueueLab dq  = new DelayedQueueLab();
            DelayedQueueLab.Task t1 = dq.new Task(5,"任务1", TimeUnit.SECONDS);
            DelayedQueueLab.Task t2 = dq.new Task(10,"任务2", TimeUnit.SECONDS);
            DelayedQueueLab.Task t3 = dq.new Task(15,"任务3", TimeUnit.SECONDS);
            DelayQueue<DelayedQueueLab.Task> queue = new DelayQueue<>();
            queue.put(t1);
            queue.put(t2);
            queue.put(t3);
            for (int i = 0; i < 3; i++) {
                DelayedQueueLab.Task task = queue.take();
                System.out.format("执行{%s},时间:{%s}\n",task,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        }
    }

    public static class SynchronousQueueLabTest {

        @Test
        public void test() throws InterruptedException, IOException {
            SynchronousQueue<Integer> queue = new SynchronousQueue<>();
            SynchronousQueueLab sq  = new SynchronousQueueLab(queue);
            SynchronousQueueLab.Consumer c = sq.new Consumer("消费者");
            SynchronousQueueLab.Producer cp= sq.new Producer("生产者");
            c.start();
            cp.start();
            System.in.read();
        }
    }
}