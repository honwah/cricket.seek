package cricket.seek;

import java.util.ArrayList;
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
            TreeNode<T> r =  ele.findNode(val);
            if (r!=null) {
                return  r;
            }
        }
        return  null;
    }
}
