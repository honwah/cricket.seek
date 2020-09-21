package cricket.seek;

import cricket.seek.ArraySeries.MaxProduct;
import cricket.seek.ArraySeries.MaxSP;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for ArraySeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*ArraySeriesTest.MaxSPTest.class*/})
public class ArraySeriesTest {
    private static final ArraySeries ase = new ArraySeries();

    public static class MaxProductTest {
        @Test
        public void adjacentElementsProduct() {
            MaxProduct mp = ase.new MaxProduct();
            int rtn = mp.adjacentElementsProduct(new int[]{9, 5, 10, 2, 24, -1, -48});
            Assert.assertEquals(rtn, 50);
        }
    }

    public static class MaxSPTest {
        private static MaxSP msp;

        @BeforeClass
        public static void beforeClass() {
            msp = ase.new MaxSP();
        }

        @Test
        public void maxSum() {
            int rtn = msp.maxSum(new int[]{1, 2, 3, -2, 4, -3});
            Assert.assertEquals(rtn, 8);
        }

        @Test
        public void maxProduct() {
            int rtn = msp.maxProduct(new int[]{1, 2, 3, -2, 4, 3});
            Assert.assertEquals(rtn, 12);
        }
    }
}
