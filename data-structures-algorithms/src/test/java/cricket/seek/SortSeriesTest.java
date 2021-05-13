package cricket.seek;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import cricket.seek.SortSeries.*;

import java.util.Arrays;

/**
 * Unit test for SortSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({SortSeriesTest.QuickSortTest.class})
public class SortSeriesTest {

    private static final SortSeries ss = new SortSeries();


    public static class QuickSortTest {

        @Test
        public void test() {
            int arr[]= {65,58,95,10,57,62,13,106,78,23,85};

            QuickSort qs = ss.new QuickSort();
            System.out.println("排序前："+ Arrays.toString(arr));

            qs.sort(arr,0,arr.length-1);

            System.out.println("排序后："+Arrays.toString(arr));
        }
    }
}
