package cricket.seek;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import cricket.seek.DivideConquerSeries.*;

import java.util.Arrays;

/**
 * Unit test for DivideConquerSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({DivideConquerSeriesTest.RoundRobinScheduleTest.class})
public class DivideConquerSeriesTest {

    private static final DivideConquerSeries dcs = new DivideConquerSeries();

    public  static  class RoundRobinScheduleTest{

        @Ignore("已验证测试")
        @Test
        public void tabletest() {
            RoundRobinSchedule rrs = dcs.new RoundRobinSchedule();
            int k = 3;//注意，n才是选手的人数，k只是问题要划分的子问题规模数，即n=2^k
            // 创建二维数组作为日程表
            int n =rrs.pow(2,k);
            int[][] array = new int[n + 1][n + 1];
            rrs.table(k,array);
            System.out.println("输出日程表：");
            rrs.printTable(array,n);
        }

        @Ignore("已验证测试")
        @Test
        public void  hanoitest() {
            TowersOfHanoi  th= dcs.new TowersOfHanoi();
            char A = 'A';
            char B = 'B';
            char C = 'C';
            //圆盘个数
            int n =5;
            th.hanoi(n, A, B, C);
            System.out.println(">>移动了" + th.m + "次，把A上的圆盘都移动到了C上");
        }

        @Ignore("已验证测试")
        @Test
        public void  chesstest() {
            Chess  chess= dcs.new Chess();
            chess.printBoard(5,3,8);
        }

        @Test
        public void  majoritytest() {
            int[] arr = {1,2,2,2,3,5};
            Majority  majority= dcs.new Majority();
            majority.printMajority(arr);
            arr = new int[] {1,5,8,2,1,4,7,4,2,5,9,6,3,2,1,4,7,8,5,5,8,8,5,8};
            majority.printMajority(arr);//5和8重数相同，这里输出5
        }
    }
}
