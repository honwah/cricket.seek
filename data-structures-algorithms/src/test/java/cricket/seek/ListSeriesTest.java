package cricket.seek;

import cricket.seek.ListSeries.OneWayLinkList;
import cricket.seek.ListSeries.TwoWayLinkList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for ListSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({ListSeriesTest.quickSortTest.class})
public class ListSeriesTest {

    private static final ListSeries ls = new ListSeries();

    public static class OneWayLinkListTest {
        @Test
        public void test() {
            OneWayLinkList<String> oll = ls.new OneWayLinkList<>();
            oll.insertHead("节点1");
            for (int i = 2; i < 9; i++) {
                oll.insertTail("节点"+i);
            }
            oll.insertTail("节点10");
            oll.insertPos("节点9", 9);
            System.out.println(oll);
            System.out.println("链表长度：" + oll.length());
            System.out.println(oll.get(0).data);
            oll.remove(7);
            System.out.println(oll);
            oll.reversal();
            System.out.println(oll);
        }
    }

    public static class TwoWayLinkListTest {
        @Test
        public void test() {
            TwoWayLinkList<String> tll = ls.new TwoWayLinkList<>();
            tll.insertHead("节点1");
            for (int i = 2; i < 9; i++) {
                tll.insertTail("节点"+i);
            }
            tll.insertTail("节点10");
            tll.insertPos("节点9", 9);
            System.out.println(tll);
            System.out.println(tll.get(1).data);
            tll.remove(8);
            System.out.println(tll);
        }
    }

    public static class IntersectNodeTest {

        @Test
        public void test() {
            OneWayLinkList<String> oll1 = ls.new OneWayLinkList<>();
            OneWayLinkList<String> oll2 = ls.new OneWayLinkList<>();
            //实例数据oll1：节点1->节点2->节点3->节点4->节点5->节点6->节点7->null，oll2：节点0->节点9->节点8->节点6->节点7->null，第一个相交节点为节点6
            oll1.insertTail("节点1");
            oll1.insertTail("节点2");
            oll1.insertTail("节点3");
            oll1.insertTail("节点4");
            oll1.insertTail("节点5");
            oll1.insertTail("节点6");
            oll1.insertTail("节点7");
            System.out.println(oll1);
            OneWayLinkList.Node node6=oll1.get(5);
            oll2.insertTail("节点0");
            oll2.insertTail("节点9");
            oll2.insertTail("节点8");
            oll2.insertTail(node6);
            System.out.println(oll2);
            //获得oll2的头节点（节点0，表示空的head）
            OneWayLinkList.Node link2FirstNode = oll2.get(0);
            System.out.println(link2FirstNode.data);
            //oll1的尾节点指向oll2的头节点
            oll1.insertTail(link2FirstNode);
            //有环就代表相交
            boolean isCycle = oll2.hasCycle(link2FirstNode);
            Assert.assertEquals(true, isCycle);
        }
    }

    public static class quickSortTest {

        @Test
        public void test() {
            OneWayLinkList<Integer> oll1 = ls.new OneWayLinkList<>();
            oll1.insertTail(4);
            oll1.insertTail(3);
            oll1.insertTail(2);
            oll1.insertTail(8);
            oll1.insertTail(0);
            oll1.insertTail(6);
            oll1.insertTail(4);
            oll1.insertTail(12);
            oll1.insertTail(9);
            oll1.insertTail(1);
            System.out.println(oll1);
            //排序前获取有数据的第一个节点
            OneWayLinkList.Node node6=oll1.get(0);
            oll1.quickSort(node6, null);
            System.out.println(oll1);
        }
    }
}