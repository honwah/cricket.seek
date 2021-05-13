package cricket.seek;

import java.math.BigInteger;

public class HashMapSeries {

    @Override
    public String toString() {
        return "整理关于哈希表的求解";
    }

    //模拟一下使用开发地址法解决hash冲突的问题
    class OpenAddressingHashTable {

        private Info[] arr;

        /**
         * 默认的构造方法
         */
        public OpenAddressingHashTable() {
            arr = new Info[100];
        }

        /**
         * 指定数组初始化大小
         */
        public OpenAddressingHashTable(int maxSize) {
            arr = new Info[maxSize];
        }

        /**
         * 插入数据
         */
        public void insert(Info info) {
            //获得关键字
            String key = info.getKey();
            //关键字所自定的哈希数
            int hashVal = hashCode(key);
            //如果这个索引已经被占用，而且里面是一个未被删除的数据
            while (arr[hashVal] != null && arr[hashVal].getValue() != null) {
                //进行递加，避免漏找
                ++hashVal;
                //循环
                hashVal %= arr.length;
            }
            arr[hashVal] = info;
        }

        /**
         * 查找数据
         */
        public Info find(String key) {
            int hashVal = hashCode(key);
            while (arr[hashVal] != null) {
                if (arr[hashVal].getKey().equals(key)) {
                    return arr[hashVal];
                }
                ++hashVal;
                hashVal %= arr.length;
            }
            return null;
        }

        /**
         * 删除数据
         */
        public Info delete(String key) {
            int hashVal = hashCode(key);
            //循环查找，数组中下标为hashVal的值，没有找到返回null
            while (arr[hashVal] != null) {
                if (arr[hashVal].getKey().equals(key)) {
                    Info tmp = arr[hashVal];
                    tmp.setValue(null);
                    return tmp;
                }
                ++hashVal;            //由于数组的值是连续的，为了避免漏找，需要依次往下找
                hashVal %= arr.length;
            }
            return null;
        }

        /**
         * 获得关键字的hash值
         * (letterToNumber*Math.pow(27,0)+letterToNumber*Math.pow(27,1)+...)%arrayLength
         */
        public int hashCode(String key) {

            BigInteger hashVal = new BigInteger("0");
            BigInteger pow27 = new BigInteger("1");
            for (int i = key.length() - 1; i >= 0; i--) {
                int letter = key.charAt(i) - 96;
                BigInteger letterB = new BigInteger(String.valueOf(letter));
                hashVal = hashVal.add(letterB.multiply(pow27));
                pow27 = pow27.multiply(new BigInteger(String.valueOf(27)));
            }
            return hashVal.mod(new BigInteger(String.valueOf(arr.length))).intValue();
        }
    }

    //模拟一下使用链地址法解决hash冲突的问题
    class LinkAddressHashTable {

        private LinkList[] arr;

        /**
         * 默认的构造方法
         */
        public LinkAddressHashTable() {
            arr = new LinkList[100];
        }

        /**
         * 指定数组初始化大小
         */
        public LinkAddressHashTable(int maxSize) {
            arr = new LinkList[maxSize];
        }

        /**
         * 插入数据
         */
        public void insert(Info info) {
            String key = info.getKey();
            // 获取关键字的自定义hash函数
            int hashVal = hashCode(key);

            if (arr[hashVal] == null) {     //如果数组某个单元的位置为空，则需要重新构造一个linkList
                arr[hashVal] = new LinkList();
            }
            arr[hashVal].insertFirst(info);
        }

        /**
         * 查找数据
         */
        public Info find(String key) {
            int hashVal = hashCode(key);
            return arr[hashVal].find(key).info;
        }

        /**
         * 删除数据
         */
        public Info delete(String key) {
            int hashVal = hashCode(key);
            return arr[hashVal].delete(key).info;
        }


        /**
         * 自定义计算hash的函数
         */
        public int hashCode(String key) {

            BigInteger hashVal = new BigInteger("0");
            BigInteger pow27 = new BigInteger("1");
            for (int i = key.length() - 1; i >= 0; i--) {
                int letter = key.charAt(i) - 96;
                BigInteger letterB = new BigInteger(String.valueOf(letter));
                hashVal = hashVal.add(letterB.multiply(pow27));
                pow27 = pow27.multiply(new BigInteger(String.valueOf(27)));
            }
            return hashVal.mod(new BigInteger(String.valueOf(arr.length))).intValue();
        }
    }


    class Info {
        private String key;            //关键字

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private String value;        //值域

        public Info(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    class Node {

        // 数据域
        public Info info;
        // 指针域,指向对下一个节点引用
        public Node next;

        public Node(Info info) {
            this.info = info;
        }

    }

    //模拟linkedList
    class LinkList {

        // 头结点
        private Node first;

        public LinkList() {
            first = null;
        }

        // 插入一个节点,在first位置插入，已有节点挂在新插入节点后
        public void insertFirst(Info info) {
            Node node = new Node(info);
            node.next = first;
            first = node;
        }

        // 删除一个节点，在头结点后进行删除
        public Node deleteFirst() {
            Node temp = first;
            first = temp.next;
            return temp;
        }

        /**
         * 查找方法
         */
        public Node find(String key) {
            Node current = first;
            while (!key.equals(current.info.getKey())) {
                if (current.next == null) {
                    return null;
                }
                current = current.next;
            }
            return current;
        }

        /**
         * 删除方法
         */
        public Node delete(String key) {
            Node current = first;
            Node previous = first;
            while (!key.equals(current.info.getKey())) {
                if (current.next == null) {
                    return null;
                }
                previous = current;
                current = current.next;
            }

            if (current == first) {
                first = first.next;
            } else {
                previous.next = current.next;
            }
            return current;
        }
    }
}