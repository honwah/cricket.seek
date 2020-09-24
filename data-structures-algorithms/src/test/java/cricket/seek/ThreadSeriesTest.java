package cricket.seek;

import cricket.seek.ThreadSeries.SeqExecution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for ThreadSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*ThreadSeriesTest.AnimalTest.class*/})
public class ThreadSeriesTest {

    private static final ThreadSeries ts = new ThreadSeries();

    public static class SeqExecutionTest {

        @Test
        public void test() {
            final SeqExecution se = ts.new SeqExecution();
            for (int i = 0; i < 3; i++) {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        se.methodA();
                    }
                });
                Thread t2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        se.methodB();
                    }
                });
                Thread t3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        se.methodC();
                    }
                });
                t3.start();
                t1.start();
                t2.start();
            }
        }
    }

    public static class StationTest {

        @Test
        public void test() throws InterruptedException {
            Station s1 = new Station("窗口1");
            Station s2 = new Station("窗口2");
            Station s3 = new Station("窗口3");
            s1.start();
            s2.start();
            s3.start();
            s1.join();
            s2.join();
            s3.join();
        }
    }

    public static class AnimalTest {

        @Test
        public void test() throws InterruptedException {
            Rabbit r = new Rabbit();
            Tortoise t = new Tortoise();
            NoticeRunner n1 = new NoticeRunner(r);
            NoticeRunner n2 = new NoticeRunner(t);
            r.cb = n2;
            t.cb = n1;
            r.start();
            t.start();
            r.join();
            t.join();
        }
    }
}