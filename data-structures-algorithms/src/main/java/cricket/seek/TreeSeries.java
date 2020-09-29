package cricket.seek;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 关于树的求解
 */
public class TreeSeries {
    @Override
    public String toString() {
        return "整理关于树的求解";
    }
}

//定义多叉树
class TreeNode<T> {

    private TreeNode<T> parent;//父节点

    private T content;//节点值

    private List<TreeNode<T>> children;//子节点

    public TreeNode(T content) {
        this.content = content;
        this.children = new ArrayList<>();
    }

    public TreeNode<T> addChild(TreeNode<T> child) {
        child.parent = this;
        this.children.add(child);
        return child;
    }

    public void addChildren(List<TreeNode<T>> children) {
        children.forEach(c -> c.parent = this);
        this.children.addAll(children);
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }


    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    //遍历树
    public void traverse(TreeNode<T> node, String appender) {
        System.out.println(appender + node.getContent());
        node.children.forEach(c -> traverse(c, appender + appender));
    }

    //查找根
    public TreeNode<T> getRoot() {
        if (parent == null) {
            return this;
        }
        return parent.getRoot();
    }

    //删除节点
    public void deleteNode() {
        if (parent != null) {
            int idx = this.parent.getChildren().indexOf(this);
            this.parent.getChildren().remove(this);
            //要删除节点的子节点需指向删除节点的父节点
            for (TreeNode<T> node : this.getChildren()) {
                node.setParent(this.parent);
            }
            //要删除节点的子节点加入删除节点父节点的子节点集合中
            this.parent.getChildren().addAll(idx, this.getChildren());
        } else {
            //如果删除节点是根节点，需要从根节点的子节点中找出一个新的根节点
            deleteRootNode();
        }
        this.getChildren().clear();
    }

    //删除根节点
    public TreeNode<T> deleteRootNode() {
        if (parent != null) {
            throw new IllegalStateException("当前要删除节点不是根节点。");
        }
        TreeNode<T> newRoot = null;
        if (!this.getChildren().isEmpty()) {
            newRoot = this.getChildren().get(0);
            newRoot.setParent(null);
            this.getChildren().remove(0);
            for (TreeNode<T> node : this.getChildren()) {
                node.setParent(newRoot);
            }
            newRoot.getChildren().addAll(this.getChildren());
        }
        this.getChildren().clear();
        return newRoot;
    }

    //获取深度
    public int getLevel() {
        if (this.parent == null) {
            return 0;
        } else {
            return this.parent.getLevel() + 1;
        }
    }

    //查找结点
    public TreeNode<T> findNode(Comparable<T> val) {
        if (val.compareTo(this.content) == 0) {
            return this;
        }
        for (TreeNode<T> ele : this.getChildren()) {
            TreeNode<T> r = ele.findNode(val);
            if (r != null) {
                return r;
            }
        }
        return null;
    }
}

//二叉查找树
class BinarySearchTree<T extends Comparable<? super T>> {

    BinaryNode<T> root;//根节点

    //查找节点
    public BinaryNode<T> findNode(T data) {
        BinaryNode<T> node = root;
        while (node != null && node.data != data) {
            if (data.compareTo(node.data) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return node;
    }


    //删除
    //假设要删除节点n，其父节点p
    //1.n是叶子，直接删除
    //2.n有一个子节点，则调整p指向n的子节点
    //3.n有两个子节点，则，n取n右子树最小的节点替代n的值，递归删除此替换节点
    public void remove(T data) {
        remove(data, root);
    }

    private BinaryNode<T> remove(T data, BinaryNode<T> node) {
        if (node == null) {
            System.out.println("未找到删除元素。");
        }
        int cr = data.compareTo(node.data);
        if (cr < 0) {
            node.left = remove(data, node.left);
        } else if (cr > 0) {
            node.right = remove(data, node.right);
        } else if (node.left != null && node.right != null) {//找到节点，并且有两个子节点
            //右子树最小元素替换要删除元素的值
            node.data = min(node.right).data;
            //移除替换节点
            node.right = remove(node.data, node.right);
        } else {
            //有一个子节点或是叶子
            node = (node.left != null) ? node.left : node.right;
        }
        return node;
    }

    //插入
    public void insert(T data) {
        root = insert(data, root);
    }

    private BinaryNode<T> insert(T data, BinaryNode<T> node) {
        if (node == null) {
            node = new BinaryNode<>(data);
        }
        //比较搜索
        int cr = data.compareTo(node.data);
        if (cr < 0) {//向左
            node.left = insert(data, node.left);
        } else if (cr > 0) { //向右
            node.right = insert(data, node.right);
        }
        //找到位置
        return node;
    }

    //找节点下最小值节点
    private BinaryNode<T> min(BinaryNode<T> node) {
        if (node == null) {
            return null;
        } else if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    //遍历树,层次遍历（蛇形打印）
    //从根节点root开始，设一个空队列，节点node不为空时，重复以下操作
    //1.访问node，将node左右子节点入队列
    //2.使node指向一个出队节点，重复1
    public void traverse(String appender) {
        //队列
        LinkedList<BinaryNode<T>> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        BinaryNode<T> node = root;
        while (node != null) {
            sb.append(appender + node.data);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
            node = queue.poll();
        }
        System.out.println(sb.toString());
    }


    public int size() {//节点数
        return size(root);
    }

    private int size(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        } else {
            int ls = size(node.left);
            int rs = size(node.right);
            return 1 + ls + rs;
        }
    }

    public int height() {//高度
        return height(root);
    }

    private int height(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        } else {
            int lh = height(node.left);
            int rh = height(node.right);
            return (lh > rh) ? (lh + 1) : (rh + 1);
        }
    }

    class BinaryNode<T> {

        public BinaryNode<T> left;//左节点

        public BinaryNode<T> right;//右节点

        public T data; //节点值

        public BinaryNode(T data) {
            this.data = data;
        }
    }
}


//平衡二叉树
class AvlTree<T extends Comparable<? super T>> {

    BinaryNode<T> root;//根节点

    //前序遍历,根左右
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(BinaryNode<T> node) {
        if (node != null) {
            System.out.print("\t" + node.data);
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    //中序遍历,左根右
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(BinaryNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print("\t" + node.data);
            inOrder(node.right);
        }
    }

    //后序遍历,左右根
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(BinaryNode<T> node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print("\t" + node.data);
        }
    }

    //层次遍历
    public void traverse(String prefix, boolean isTail) {
        System.out.println(getString(root, prefix, isTail));
    }

    private String getString(BinaryNode<T> node, String prefix, boolean isTail) {
        StringBuilder builder = new StringBuilder();

        builder.append(prefix + (isTail ? "└── " : "├── ") + "(" + node.height + ") " + node.data + "\n");
        List<BinaryNode<T>> children = null;
        if (node.left != null || node.right != null) {
            children = new ArrayList<BinaryNode<T>>(2);
            if (node.left != null)
                children.add(node.left);
            if (node.right != null)
                children.add(node.right);
        }
        if (children != null) {
            for (int i = 0; i < children.size() - 1; i++) {
                builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
            }
            if (children.size() >= 1) {
                builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "), true));
            }
        }
        return builder.toString();
    }

    //查找值对应节点
    public BinaryNode<T> get(T data) {
        return get(root, data);
    }


    private BinaryNode<T> get(BinaryNode<T> node, T data) {
        if (node == null) {
            return null;
        }
        int cmpval = data.compareTo(node.data);
        if (cmpval < 0) {
            return get(node.left, data);
        } else if (cmpval > 0) {
            return get(node.right, data);
        } else {
            return node;
        }
    }

    //删除
    public void remove(T data) {
        BinaryNode<T> del = get(data);
        root = remove(root, del);
    }

    //node 根节点，del 删除节点
    private BinaryNode<T> remove(BinaryNode<T> node, BinaryNode<T> del) {
        //根为空或未找到删除节点
        if (node == null || del == null) {
            return null;
        }
        int cmpval = del.data.compareTo(node.data);
        if (cmpval < 0) {
            //待删除节点在左子树
            node.left = remove(node.left, del);
            //失去平衡
            if (getBalanceFactor(node) == -2) {
                //右子树高度>左子树高度
                BinaryNode<T> rnode = node.right;
                if (height(rnode.left) > height(rnode.right)) {
                    node = rlRotate(node);
                } else {
                    node = rrRotate(node);
                }
            }
        } else if (cmpval > 0) {
            //待删除节点在右子树
            node.right = remove(node.right, del);
            //失去平衡
            if (getBalanceFactor(node) == 2) {
                //左子树高度>右子树高度
                BinaryNode<T> lnode = node.left;
                if (height(lnode.right) > height(lnode.left)) {
                    node = lrRotate(node);
                } else {
                    node = llRotate(node);
                }
            }
        } else {
            //node就是要删除节点
            //node的左右孩子都非空
            if ((node.left != null) && (node.right != null)) {
                if (height(node.left) > height(node.right)) {
                    //左子树高度>右子树高度,用"node的左子树中最大节点"做"node"的替身,删除"node的左子树中最大节点"之后，AVL树仍然是平衡的。
                    //(1)找出左子树最大节点
                    //(2)将最大节点值赋值给node
                    //(3)删除此最大节点
                    BinaryNode<T> lm = max(node.left);
                    node.data = lm.data;
                    node.left = remove(node.left, lm);
                } else {
                    //右子树高度>=左子树，用"tree的右子树中最小节点"做"tree"的替身，删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                    //(1)找出node右子树最小节点
                    //(2)将最小节点值赋值给node
                    //(3)删除该最小节点
                    BinaryNode<T> rm = min(node.right);
                    node.data = rm.data;
                    node.right = remove(node.right, rm);
                }
            } else {
                BinaryNode<T> tmp = node;
                node = (node.left != null) ? node.left : node.right;
                tmp = null;
            }
        }
        if (node!=null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
        return node;
    }


    //找出最小节点
    private BinaryNode<T> min(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    //查找最大节点
    private BinaryNode<T> max(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    //插入
    public void insert(T data) {
        root = insert(root, data);
    }


    //node 根节点，data 插入值
    private BinaryNode<T> insert(BinaryNode<T> node, T data) {
        if (node == null) {
            node = new BinaryNode<>(data);
        } else {
            int cmpval = data.compareTo(node.data);
            if (cmpval < 0) {
                //插入左子树
                node.left = insert(node.left, data);
                //失去平衡
                if (getBalanceFactor(node) == 2) {
                    //左子树高度>右子树
                    if (data.compareTo(node.left.data) < 0) {
                        node = llRotate(node);
                    } else {
                        node = lrRotate(node);
                    }
                }
            } else if (cmpval > 0) {
                //插入右子树
                node.right = insert(node.right, data);
                if (getBalanceFactor(node) == -2) {
                    //失去平衡
                    if (data.compareTo(node.right.data) > 0) {
                        node = rrRotate(node);
                    } else {
                        node = rlRotate(node);
                    }
                }
            }
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    //RL两次旋转，返回旋转后的根节点
    private BinaryNode<T> rlRotate(BinaryNode<T> a) {
        a.right = llRotate(a.right);
        return rrRotate(a);
    }

    //LR两次旋转，返回旋转后的根节点
    private BinaryNode<T> lrRotate(BinaryNode<T> a) {
        a.left = rrRotate(a.left);
        return llRotate(a);
    }


    //RR右旋转，返回右旋后根节点
    private BinaryNode<T> rrRotate(BinaryNode<T> a) {
        BinaryNode<T> b;
        b = a.right;
        a.right = b.left;
        b.left = a;
        a.height = Math.max(height(a.left), height(a.right)) + 1;
        b.height = Math.max(height(b.right), a.height) + 1;
        return b;
    }

    //LL左旋转，返回左旋转后根节点
    private BinaryNode<T> llRotate(BinaryNode<T> a) {
        BinaryNode<T> b;
        b = a.left;
        a.left = b.right;
        b.right = a;

        a.height = Math.max(height(a.left), height(a.right)) + 1;
        b.height = Math.max(height(b.left), a.height) + 1;
        return b;
    }

    //计算平衡因子,可用来判断是否树平衡
    int getBalanceFactor(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    //获取结点高度
    private int height(BinaryNode<T> node) {
        if (node == null)
            return 0;
        return node.height;
    }

    //计算节点高度
    int getHeight(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        int lh = getHeight(node.left);
        int rh = getHeight(node.right);
        return Math.max(lh, rh) + 1;
    }

    //节点
    class BinaryNode<T> {

        public int height;//树的高度

        public BinaryNode<T> left;//左节点

        public BinaryNode<T> right;//右节点

        public T data; //节点值

        public BinaryNode(T data) {
            this.data = data;
            this.height = 0;//树初始高度0
        }
    }
}