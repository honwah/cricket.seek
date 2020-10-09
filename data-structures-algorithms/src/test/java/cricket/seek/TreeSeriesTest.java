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
@Suite.SuiteClasses({TreeSeriesTest.TrieTreeTest.class})
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

            TreeNode<String> node = root.findNode("node 21");
            Assert.assertNotNull(node);
        }
    }

    public static class BinarySearchTreeTest {
        @Test
        public void test() {
            BinarySearchTree<Integer> bst = new BinarySearchTree<>();
            bst.insert(80);
            bst.insert(90);
            bst.insert(70);
            bst.insert(60);
            bst.insert(40);
            bst.insert(50);
            bst.insert(75);
            bst.insert(72);
            bst.insert(78);
            bst.traverse("\t");
            bst.remove(70);
            bst.traverse("\t");
            BinarySearchTree<Integer>.BinaryNode<Integer> node = bst.findNode(78);
            Assert.assertNotNull(node);
            Assert.assertEquals(5, bst.height());
            Assert.assertEquals(8, bst.size());
        }
    }

    public static class AvlTreeTest {

        @Test
        public void test() throws InterruptedException {
            AvlTree<Integer> avl = new AvlTree<>();
            //Integer[] arr = {30, 20, 26, 45, 66, 40, 70, 90};
            Integer[] arr = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};
            System.out.printf("依次添加: ");
            for (int i = 0; i < arr.length; i++) {
                System.out.printf("%d ", arr[i]);
                avl.insert(arr[i]);
            }
            System.out.printf("\n前序遍历: ");
            avl.preOrder();
            System.out.printf("\n中序遍历: ");
            avl.inOrder();
            System.out.printf("\n后序遍历: ");
            avl.postOrder();
            System.out.println("\n层次遍历: ");
            avl.traverse("", true);
            avl = new AvlTree<>();
            arr = new Integer[]{30, 20, 26, 45, 66, 40, 70, 90};
            System.out.printf("依次添加: ");
            for (int i = 0; i < arr.length; i++) {
                System.out.printf("%d ", arr[i]);
                avl.insert(arr[i]);
            }
            System.out.printf("\n前序遍历: ");
            avl.preOrder();
            System.out.printf("\n中序遍历: ");
            avl.inOrder();
            System.out.printf("\n后序遍历: ");
            avl.postOrder();
            System.out.println("\n层次遍历: ");
            avl.traverse("", true);
            avl.remove(20);
            avl.traverse("", true);
        }
    }


    public static class TrieTreeTest {

        @Test
        public void test() {
            TrieTree tt = new TrieTree();
            tt.add("人工智能");
            tt.add("物联网");
            tt.add("大数据");
            tt.add("区块链");
            tt.add("flink");
            tt.add("netty");
            tt.add("mysql");
            tt.add("redis");
            tt.add("人工智能");
            tt.add("物联网");
            tt.add("大数据");
            tt.add("区块链");
            tt.add("flink");
            tt.add("netty");
            tt.add("mysql");
            tt.add("redis");
            tt.add("kmp");
            tt.add("物联网时代");
            tt.add("my");
            tt.traverse("", null, true);
            boolean isHave = tt.contains("flink");
            Assert.assertEquals(true, isHave);
            int count = tt.query("人工智能");
            Assert.assertEquals(2, count);
            boolean isSuccess = tt.remove("flink");
            Assert.assertEquals(true, isSuccess);
            tt.traverse("", null, true);
            isSuccess = tt.remove("flink");
            Assert.assertEquals(true, isSuccess);
            tt.traverse("", null, true);
            isSuccess = tt.remove("my");
            Assert.assertEquals(true, isSuccess);
            tt.traverse("", null, true);
        }
    }
}