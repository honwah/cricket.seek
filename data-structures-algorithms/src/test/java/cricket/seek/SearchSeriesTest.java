package cricket.seek;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import cricket.seek.SearchSeries.*;

import java.util.Arrays;

/**
 * Unit test for SortSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*SearchSeriesTest.SearchTest.class*/})
public class SearchSeriesTest {
    private static final SearchSeries ss = new SearchSeries();


    public static class SearchTest {

        @Ignore("已验证测试")
        @Test
        public void test() {
            int[] arr = {10,45,68,32,15};
            Sequential_Search aSearch = ss.new Sequential_Search();
            System.out.println(aSearch.seqSearch(arr, 15));
            System.out.println(aSearch.seqSearch(arr, 45));
            System.out.println(aSearch.seqSearch(arr, 10));
            System.out.println(aSearch.seqSearch(arr, 100));
            arr = new int[]{10, 45, 68, 32, 15};
            System.out.println(aSearch.seqSearch2(arr, 15));
            arr = new int[]{10, 45, 68, 32, 15};
            System.out.println(aSearch.seqSearch2(arr, 45));
            arr = new int[]{10, 45, 68, 32, 15};
            System.out.println(aSearch.seqSearch2(arr, 10));
            arr = new int[]{10, 45, 68, 32, 15};
            System.out.println(aSearch.seqSearch2(arr, 100));
        }

        @Ignore("已验证测试")
        @Test
        public void testBinarySearch() {
            int[] a = {1,2,3,4,5,6,7,8,9};
            System.out.println(ss.binarySearch2(a,7));
        }

        @Ignore("已验证测试")
        @Test
        public void testInsertSearch() {
            int[] a = {1,2,3,4,5,6,7,8,9};
            System.out.println(ss.insertSearch(a,7));
        }

        @Test
        public void testFibonacciSearch() {
            int[] a = {1,16,24,35,47,59,62,73,88,99};
            System.out.println(ss.fibonacciSearch(a,99));
        }
    }
}
