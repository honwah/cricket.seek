package cricket.seek;

/**
 * 关于链表的求解
 */
public class ListSeries {
    @Override
    public String toString() {
        return "整理关于链表的求解";
    }

    /**
     * 单向链表
     */
    class OneWayLinkList<T extends Comparable<? super T>>{

        public Node head;//头结点

        public OneWayLinkList() {
            this.head = new Node(null);//初始化链表时创建头结点
        }

        //向链表头部添加
        public void insertHead(T val) {
            Node cur = new Node(val);//构造节点
            cur.next = head.next;
            head.next = cur;
        }

        //向链表尾添加
        public void insertTail(T val) {
            Node preNode = this.head;
            while (preNode.next != null) {
                preNode = preNode.next;
            }
            Node cur = new Node(val);
            preNode.next = cur;
            cur.next = null;
        }

        //向链表尾添加
        public void insertTail(Node val) {
            Node preNode = this.head;
            while (preNode.next != null) {
                preNode = preNode.next;
            }
            Node cur = val;
            preNode.next = cur;
        }

        //向指定位置添加
        public boolean insertPos(T val, int pos) {
            //0位置代表head
            if (pos <= 0 || pos > length()) {
                return false;
            } else {
                Node pre = this.head;
                //获取pos的上一节点
                for (int i = 0; i < pos - 1; i++) {
                    pre = pre.next;
                }
                Node cur = new Node(val);
                cur.next = pre.next;
                pre.next = cur;
                return true;
            }
        }

        //查找
        public Node get(int pos) {
            if (pos >= 0 && pos < length()) {
                Node pre = this.head;
                int idx = -1;
                while (pre.next != null) {
                    idx++;
                    pre = pre.next;
                    if (idx == pos) {
                        return pre;
                    }
                }
            }
            return null;
        }

        //移除
        public boolean remove(int pos) {
            if (pos < 0 || pos >= length()) {
                return false;
            } else {
                Node r = this.get(pos);
                if (r != null) {
                    Node pre = this.head;
                    for (int i = 0; i <= pos - 1; i++) {
                        pre = pre.next;
                    }
                    pre.next = r.next;
                    r = null;
                    return true;
                } else {
                    return false;
                }
            }
        }

        //链表反转,画图便于理解
        //反转前打印（节点8测试删除了）null->节点1->节点2->节点3->节点4->节点5->节点6->节点7->节点9->节点10->null
        //反转后打印null->节点10->节点9->节点7->节点6->节点5->节点4->节点3->节点2->节点1->null
        public Node reversal() {
            //链表为空
            if (this.head == null || this.head.next == null) {
                return this.head;
            }
            Node tmphead = this.head;//反转后还用起作为头
            Node pre = null;//前一个节点
            Node cur = this.head.next;//当前节点，从第一个节点开始反转，因为从头开始会产生两个null的尾节点
            Node next = null;//下一个节点
            while (cur != null) {
                next = cur.next;//next指向下一个节点
                cur.next = pre;//当前节点next域指向前一节点
                pre = cur;//pre指针向后移动
                cur = next;//cur指针向后移动
            }
            //修改头指针
            tmphead.next = pre;
            return tmphead;
        }

        //判断链表是否有环
        public boolean hasCycle(Node head) {
            if (head == null || head.next == null) {
                return false;
            }
            Node slow = head;
            Node fast = head.next.next;
            //如果快慢指针相同，则跳出循环，此时快指针追上了慢指针，表示链表有环
            while (slow != fast) {
                if (fast == null || fast.next == null) {
                    return false;
                }
                slow = slow.next;
                fast = fast.next.next;
            }
            return true;
        }

        //快速排序，注意调用时head不要用数据为null那个头，用第一个有数据的节点
        public void quickSort(Node head, Node end) {
            if (head != end) {
                Node node = partition(head, end);
                quickSort(head, node);
                quickSort(node.next, end);
            }
        }

        //辅助快速排序进行partition
        private Node partition(Node head, Node end) {
            Node p = head;
            Node q = head.next;
            //走到末尾
            while (q != end) {
                if (q.data.compareTo(head.data)<0) {
                    p = p.next;
                    //如果p前进一步后步进到了q，p和q相同，不用交换
                    if (p.data != q.data) {
                        T tmp = p.data;
                        p.data = q.data;
                        q.data = tmp;
                    }
                }
                q =q.next;
            }
            //  如果p和key相同
            if (p.data!= head.data) {
                T tmp = p.data;
                p.data = head.data;
                head.data = tmp;
            }
            return p;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node cur = this.head;
            while (cur != null) {
                sb.append(cur.data).append("->");
                cur = cur.next;
            }
            sb.append("null");
            return sb.toString();
        }


        //链表长度
        public int length() {
            int len = 0;
            Node preNode = this.head.next;
            while (preNode != null) {
                len++;
                preNode = preNode.next;
            }
            return len;
        }

        //节点
        class Node{
            T data;//数据
            private Node next;//下一节点

            public Node(T val) {
                this.data = val;
                this.next = null;
            }
        }
    }

    /**
     * 无环双向链表
     *
     * @param <T>
     */
    class TwoWayLinkList<T> {

        private Node head;//头结点

        public TwoWayLinkList() {
            //头结点不存储值
            this.head = new Node(null);
        }

        //查找,因是无环，所以无法从后往前找
        public Node get(int pos) {
            if (pos < 0 || pos > length()) {
                return null;
            }
            Node cur = this.head;
            for (int i = 0; i < pos; i++) {
                cur = cur.next;
            }
            return cur;
        }

        //插入头
        public void insertHead(T val) {
            Node cur = new Node(val);//构造节点
            cur.prev = this.head;//节点上一个指向
            cur.next = this.head.next;//节点下一个指向
            if (length() > 0) {//链表已有节点
                this.head.next.prev = cur;//头结点下一个节点的上一个指向
            }
            this.head.next = cur;//头结点下一个指向
        }

        //插入尾
        public void insertTail(T val) {
            Node cur = new Node(val);//构造节点
            //找到尾节点
            Node tail = this.get(length());
            cur.prev = tail;
            tail.next = cur;
        }

        //插入
        public boolean insertPos(T val, int pos) {
            if (pos < 0 || pos > length()) {
                return false;
            } else {
                //找到插入位置节点
                Node node = get(pos);
                Node cur = new Node(val);
                cur.prev = node.prev;
                cur.next = node;
                node.prev.next = cur;
                node.prev = cur;
                return true;
            }
        }

        //移除
        public boolean remove(int pos) {
            if (pos < 0 || pos >= length()) {
                return false;
            } else {
                Node cur = this.get(pos);
                cur.prev.next = cur.next;
                cur.next.prev = cur.prev;
                cur = null;
                return true;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb;
            sb = new StringBuilder();
            Node cur = this.head;
            while (cur != null) {
                sb.append(cur.data).append("->");
                cur = cur.next;
            }
            sb.append("null");
            return sb.toString();
        }

        //链表长度
        public int length() {
            int len = 0;
            Node cur = this.head.next;
            while (cur != null) {
                len++;
                cur = cur.next;
            }
            return len;
        }

        //节点
        class Node {
            T data;//数据
            private Node prev;//上一节点
            private Node next;//下一节点

            public Node(T val) {
                this.data = val;
                this.prev = null;
                this.next = null;
            }
        }
    }
}
