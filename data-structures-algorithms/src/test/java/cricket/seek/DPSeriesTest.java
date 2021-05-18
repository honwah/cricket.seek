package cricket.seek;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import cricket.seek.DPSeries.*;

import java.util.Arrays;

/**
 * Unit test for DPSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*DPSeriesTest.FibonacciTest.class*/})
public class DPSeriesTest {

    private static final DPSeries dps = new DPSeries();

    public static class FibonacciTest {

        @Ignore("已验证测试")
        @Test
        public void test() {
            int[] arr = {10, 45, 68, 32, 15};
            Fibonacci fib = dps.new Fibonacci();
            long startTime = System.currentTimeMillis();   //获取开始时间
            for (int i = 0; i < 0; i++) {
                System.out.print(fib.getFibonacci1(i) + "\t");
            }
            long endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("程序1运行时间： " + (endTime - startTime) + "ms");

            startTime = System.currentTimeMillis();   //获取开始时间
            for (int i = 0; i < 0; i++) {
                System.out.print(fib.getFibonacci2(i) + "\t");
            }
            endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("程序2运行时间： " + (endTime - startTime) + "ms");

            startTime = System.currentTimeMillis();   //获取开始时间
            for (int i = 0; i < 50; i++) {
                System.out.print(fib.getFibonacci3(i) + "\t");
            }
            endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("程序3运行时间： " + (endTime - startTime) + "ms");
        }

        @Ignore("已验证测试")
        @Test
        public void LIStest() {
            int[] arr = {6, 3, 5, 10, 11, 2, 9, 14, 13, 7, 4, 8, 12};
            LIS lis = dps.new LIS();
            long startTime = System.currentTimeMillis();   //获取开始时间
            for (int i = 0; i < 1000; i++) {
                System.out.print(lis.lengthOfLIS1(arr) + "\t");
            }
            long endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("\r\n程序1运行时间： " + (endTime - startTime) + "ms");

            startTime = System.currentTimeMillis();   //获取开始时间
            for (int i = 0; i < 1000; i++) {
                System.out.print(lis.lengthOfLIS2(arr) + "\t");
            }
            endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("\r\n程序2运行时间： " + (endTime - startTime) + "ms");

            startTime = System.currentTimeMillis();   //获取开始时间
            for (int i = 0; i < 1000; i++) {
                System.out.print(lis.lengthOfLIS3(arr) + "\t");
            }
            endTime = System.currentTimeMillis(); //获取结束时间
            System.out.println("\r\n程序3运行时间： " + (endTime - startTime) + "ms");
        }

        @Test
        public void LCStest() {
            String str1 = "13456778";
            String str2 = "357486782";
            LCS lcs = dps.new LCS();
            lcs.getLCS(str1, str2);
        }
    }
}
