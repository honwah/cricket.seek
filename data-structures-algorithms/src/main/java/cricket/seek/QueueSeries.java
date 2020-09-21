package cricket.seek;

import java.util.Stack;

/**
 * 关于队列的求解
 */
public class QueueSeries {
    @Override
    public String toString() {
        return "整理关于队列的求解";
    }

    /**
     * 用栈实现队列
     */
    class QueueViaStack {

        int size;
        Stack<Integer> ast;
        Stack<Integer> bst;

        public QueueViaStack(int size) {
            this.size = size;
            ast = new Stack<>();
            bst = new Stack<>();
        }

        /**
         * 添加元素
         *
         * @param e 元素
         * @return 是否成功
         */
        public boolean offer(int e) {
            if (length() < this.size) {
                //先把 B 中的元素转入 A 中
                while (!bst.isEmpty()) {
                    int tmp = bst.pop();
                    ast.push(tmp);
                }
                //再把新元素入 A
                ast.push(e);
                return true;
            } else {
                return false;
            }
        }

        public Integer poll() {
            if (empty()) {
                return null;
            }
            while (!ast.isEmpty()) {
                int tmp = ast.pop();
                bst.push(tmp);
            }
            return bst.pop();
        }

        public Integer peek() {
            if (empty()) {
                return null;
            }
            while (!ast.isEmpty()) {
                int tmp = ast.pop();
                bst.push(tmp);
            }
            return bst.peek();
        }

        int length() {
            return ast.size() + bst.size();
        }

        public boolean empty() {
            return ast.isEmpty() && bst.isEmpty();
        }
    }
}
