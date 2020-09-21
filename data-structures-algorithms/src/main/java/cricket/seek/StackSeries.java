package cricket.seek;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于堆栈的求解
 */
public class StackSeries {

    @Override
    public String toString() {
        return "整理关于堆栈的求解。";
    }

    /**
     * 使用数组定义一个堆栈
     */
    class StackViaArray {

        int[] data;
        int size;
        int idx;

        public StackViaArray(int size) {
            this.size = size;
            data = new int[size];
            idx = -1;
        }

        public boolean push(int ele) {
            if (idx+1 >= size) {
                return false;
            }
            this.data[++idx] = ele;
            return true;
        }

        public int pop() throws Exception {
            if (idx <= -1) {
                throw new Exception("栈已空。");
            }
            return this.data[idx--];
        }
    }

    /**
     * 使用集合定义一个堆栈
     */
    class StackViaList{
        List<Integer> list = new ArrayList<>();
        int idx = 0;

        public  void push(int ele){
            list.add(ele);
            idx++;
        }

        public int pop() throws Exception {
            if (!list.isEmpty()) {
                idx--;
                return list.remove(idx);
            } else {
                throw new Exception("栈已空。");
            }
        }
    }
}
