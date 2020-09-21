package cricket.seek;

import cricket.seek.StringSeries.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for StringSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({/*StringSeriesTest.LevenshteinTest.class*/})
public class StringSeriesTest {

    private static final StringSeries ss = new StringSeries();

    public static class StringAdditionTest {
        @Test
        public void Addition() {
            StringAddition mp = ss.new StringAddition();
            String rtn = mp.Addition("99999999999999999999", "101");
            Assert.assertEquals(rtn, "100000000000000000100");
        }
    }

    public static class LevenshteinTest {
        @Test
        public void getSimilarityRatio() {
            Levenshtein ls = ss.new Levenshtein();
            float rtn = ls.getSimilarityRatio("亲爱的朋友们，大家早上好。", "亲爱的朋友们，大家晚上好。");
            Assert.assertEquals(rtn, 0.92307F,0.0001F);//delta允许的误差
        }
    }
}