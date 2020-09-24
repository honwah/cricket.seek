package cricket.seek;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

//ArrayBlockingQueue模拟生产者和消费者
class Store {
    private volatile static Store instance;
    private AtomicInteger idx;
    private ArrayBlockingQueue<Integer> queue;

    private Store() {
        idx = new AtomicInteger();
        queue = new ArrayBlockingQueue<>(10);
    }

    public static Store getInstance() {
        if (instance == null) {
            synchronized (Store.class) {
                if (instance == null) {
                    instance = new Store();
                }
            }
        }
        return instance;
    }

    public void put() {
        int a = idx.incrementAndGet();
        try {
            queue.put(a);
            System.out.println("生产：" + a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void take() {
        try {
            Integer a = queue.take();
            System.out.println("\t 消费：" + a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class Customer implements Runnable {

        private Store store;

        public Customer() {
            store = Store.getInstance();
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                store.take();
            }
        }
    }

    class Producer implements Runnable {

        private Store store;

        public Producer() {
            store = Store.getInstance();
        }

        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                store.put();
            }
        }
    }
}

//支持优先级的队列PriorityBlockingQueue
class PriorityBlockingQueueLab {

    private PriorityBlockingQueue<Task> pb;

    public PriorityBlockingQueueLab() {
        this.pb = new PriorityBlockingQueue<>();
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            Random r = new Random(47);
            for (int i = 0; i < 10; i++) {
                int tmp = r.nextInt(100);
                Task t = new Task(tmp, "任务" + tmp);
                pb.add(t);
                System.out.println("生产：" + t);
            }
        }
    }

    class Customer implements Runnable {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    Task t = pb.take();
                    System.out.println("消费：" + t);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Task implements Comparable<Task> {

        private int priority;
        private String name;

        public Task(int priority, String name) {
            this.name = name;
            this.priority = priority;
        }

        @Override
        public int compareTo(Task o) {
            return this.priority > o.priority ? 1 : (this.priority < o.priority ? -1 : 0);
        }

        @Override
        public String toString() {
            return this.priority + "\t" + this.name;
        }
    }
}

//延迟队列DelayedQueue
class DelayedQueueLab {

    class Task implements Delayed {

        //触发时间
        private long time;
        private String name;

        public Task(long time, String name, TimeUnit unit) {
            this.time = System.currentTimeMillis() + unit.toMillis(time);
            this.name = name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return time- System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            Task t = (Task) o;
            return this.time > t.time ? 1 : (this.time < t.time ? -1 : 0);
        }

        @Override
        public String toString() {
            return "Task{" + name + "," + time + "}";
        }
    }
}

//同步队列
class SynchronousQueueLab {

    private SynchronousQueue<Integer>  queue;
    public  SynchronousQueueLab(SynchronousQueue<Integer>  queue){
        this.queue = queue;
    }

    class Producer extends Thread {
        public Producer(String name) {
            super(name);
        }

        @Override
        public void run() {

            try {
                Thread.sleep(1000);
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+Thread.currentThread().getName()+"-等待数字2被消耗："+queue.offer(2));
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+Thread.currentThread().getName()+"-等待数字3被消耗：");
                queue.put(3);
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+Thread.currentThread().getName()+"-等待数字4被消耗："+queue.offer(4,1,TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer extends Thread {
        public Consumer(String name) {
            super(name);
        }

        @Override
        public void run() {

            try {
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+Thread.currentThread().getName()+"-消费了"+queue.take());//没有生产者会阻塞
                Thread.sleep(2000);
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+Thread.currentThread().getName()+"-消费了"+queue.take());
                Thread.sleep(1500);
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+Thread.currentThread().getName()+"-消费了"+queue.poll(1,TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
