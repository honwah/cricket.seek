package cricket.seek;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Unit test for TreeSeries.
 */
//@Ignore("已验证测试")
@RunWith(Suite.class)
@Suite.SuiteClasses({TreeSeriesTest.TreeNodeTest.class})
public class TreeSeriesTest {

    public static class TreeNodeTest {
        @Test
        public void test() {
            TreeNode<String> root = new TreeNode<>("root");
            TreeNode<String> node1 = root.addChild(new TreeNode<String>("node 1"));

            TreeNode<String> node11 = node1.addChild(new TreeNode<String>("node 11"));
            TreeNode<String> node111 = node11.addChild(new TreeNode<String>("node 111"));
            TreeNode<String> node112 = node11.addChild(new TreeNode<String>("node 112"));

            TreeNode<String> node12 = node1.addChild(new TreeNode<String>("node 12"));
            TreeNode<String> node13 = node1.addChild(new TreeNode<String>("node 13"));

            TreeNode<String> node2 = root.addChild(new TreeNode<String>("node 2"));

            TreeNode<String> node21 = node2.addChild(new TreeNode<String>("node 21"));
            TreeNode<String> node22 = node2.addChild(new TreeNode<String>("node 22"));
            root.traverse(root, "\t");

            node11.deleteNode();
            root.getLevel();

            root.traverse(root, "\t");

            root = root.deleteRootNode();

            root.traverse(root, "\t");

            System.out.println(node22.getLevel());

            TreeNode<String> node =  root.findNode("node 21");
            Assert.assertNotNull(node);
        }
    }
}