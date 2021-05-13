package cricket.seek;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import cricket.seek.HashMapSeries.*;

/**
 * Unit test for HashMapSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({HashMapSeriesTest.OpenAddressingHashTableTest.class})
public class HashMapSeriesTest {

    private static final HashMapSeries hms = new HashMapSeries();


    public static class OpenAddressingHashTableTest {

        @Test
        public void test() {
            OpenAddressingHashTable oht = hms.new OpenAddressingHashTable();
            oht.insert(hms.new Info("a", "北京市"));
            oht.insert(hms.new Info("ct", "上海市"));
            oht.insert(hms.new Info("c", "天津市"));
            oht.insert(hms.new Info("cv", "杭州市"));
            System.out.println(oht.find("a").getValue());
            System.out.println(oht.find("ct").getValue());
            System.out.println(oht.find("c").getValue());
            System.out.println(oht.find("cv").getValue());
            System.out.println(oht.hashCode("a"));
            System.out.println(oht.hashCode("ct"));
            System.out.println(oht.hashCode("c"));
            System.out.println(oht.hashCode("cv"));

            LinkAddressHashTable lht = hms.new LinkAddressHashTable();
            lht.insert(hms.new Info("a", "北京市"));
            lht.insert(hms.new Info("ct", "上海市"));
            lht.insert(hms.new Info("c", "天津市"));
            lht.insert(hms.new Info("cv", "杭州市"));
            System.out.println(lht.find("a").getValue());
            System.out.println(lht.find("ct").getValue());
            System.out.println(lht.find("c").getValue());
            System.out.println(lht.find("cv").getValue());
            System.out.println(lht.hashCode("a"));
            System.out.println(lht.hashCode("ct"));
            System.out.println(lht.hashCode("c"));
            System.out.println(lht.hashCode("cv"));
        }
    }
}