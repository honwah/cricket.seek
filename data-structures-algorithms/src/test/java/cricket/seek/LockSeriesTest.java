package cricket.seek;

import cricket.seek.LockSeries.MCSLock;
import cricket.seek.LockSeries.NonReentrantLockLab;
import cricket.seek.LockSeries.Notice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit test for LockSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({LockSeriesTest.MCSLockTest.class})
public class LockSeriesTest {

    private static final LockSeries ls = new LockSeries();

    public static class NoticeTest {

        @Test
        public void test() {
            Notice notice = ls.new Notice();
            Thread t1 = new Thread(notice, "t1");
            Thread t2 = new Thread(notice, "t2");
            t1.start();
            t2.start();
        }
    }

    public static class NonReentrantLockLabTest {

        @Test
        public void test() {
            NonReentrantLockLab lockLab = ls.new NonReentrantLockLab();
            Thread t1 = new Thread(lockLab, "t1");
            t1.start();
        }
    }

    public static class MCSLockTest {
        MCSLock lock = ls.new MCSLock();

        void increment(int t) {
            lock.lock();
            System.out.println("increment~thread~" + t + "++");
            lock.unlock();
        }

        void decrement(int t) {
            lock.lock();
            System.out.println("decrement~thread~" + t + "--");
            lock.unlock();
        }

        @Test
        public void test() {
            ExecutorService es1 = Executors.newFixedThreadPool(10);
            ExecutorService es2 = Executors.newFixedThreadPool(10);
            es1.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        increment(i);
                    }
                }
            });
            es2.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {
                        decrement(i);
                    }
                }
            });
            es1.shutdown();
            es2.shutdown();
        }
    }
}