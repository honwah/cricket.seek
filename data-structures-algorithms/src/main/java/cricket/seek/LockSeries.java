package cricket.seek;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 关于锁的求解
 */
public class LockSeries {
    @Override
    public String toString() {
        return "整理关于锁的求解";
    }

    /**
     * 基于ReentrantLock的可重入锁案例
     */
    class Notice implements Runnable {

        ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            sendSMS();
        }

        //发送短信
        public void sendSMS() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " called sendSMS.--外层方法");
                sendEmail();
            } finally {
                lock.unlock();
            }
        }

        //发送邮件
        public void sendEmail() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " called sendEmail.--内层方法");
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 不可重入锁，通过synchronized wait notify实现
     */
    class NonReentrantLock {
        //是否被锁
        private volatile boolean locked = false;

        public synchronized void lock() {
            //当某个线程获取锁后，本线程和其他线程无法再次获得锁
            while (locked) {
                try {
                    System.out.println(Thread.currentThread().getName() + "," + Thread.currentThread().getStackTrace()[2].getMethodName() + "--等待获取锁");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //加锁成功
            locked = true;
            System.out.println(Thread.currentThread().getName() + "," + Thread.currentThread().getStackTrace()[2].getMethodName() + "--获得锁");
        }

        //释放锁
        public synchronized void unlock() {
            locked = false;
            notify();
        }
    }

    //检验不可重入锁
    class NonReentrantLockLab implements Runnable {

        private final NonReentrantLock lock = new NonReentrantLock();

        @Override
        public void run() {
            sendSMS();
        }

        public void sendSMS() {
            System.out.println(Thread.currentThread().getName() + " --sendSMS请求获取锁");
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " called sendSMS --外层方法");
                //同一线程内调用另一方法
                sendEmail();
            } finally {
                lock.unlock();
            }
        }

        public void sendEmail() {
            System.out.println(Thread.currentThread().getName() + " --sendEmail请求获取锁");
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " called sendEmail --内层方法");
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * MCSLock,公平的自旋锁，它提供了不少很好的性质：比如FIFO、没有饥饿等等
     */
    class MCSLock {

        //队尾节点
        private  volatile AtomicReference<MCSNode> tail;
        //自己
        private volatile ThreadLocal<MCSNode> my;

        public MCSLock() {
            //队尾节点初始化null
            tail = new AtomicReference<>(null);
            my = new ThreadLocal<MCSNode>() {
                protected MCSNode initialValue() {
                    return new MCSNode();
                }
            };
        }

        public void lock() {
            //获得自己的节点
            MCSNode me = my.get();
            //获取队列尾部节点给pred，然后将自己设置到尾部（将mcsNode赋值给tail）
            MCSNode pre = tail.getAndSet(me);
            //如果pred为空，说明队列是空的，锁空闲，没有被线程占用，获取锁成功，不为空则自旋等待
            if (pre != null) {
                //如果pred不为空，设置自己锁状态为true，设置自己的前一个节点为pred
                me.locked = true;
                pre.next = me;
                //然后自旋，等待自己的前一节点通知（将自己的locked设置为false）
                while (me.locked) {
                    //线程让步
                    Thread.yield();
                }
            }
        }

        public void unlock() {
            MCSNode me = my.get();
            //释放自己时，检查下自己之后是否还有节点
            if (me.next == null) {
                //如果自己没有后续的节点，它是最后一个线程，只需要把尾结点置空，那么队列就为空了
                if (tail.compareAndSet(me, null)) {
                    return;
                }
                //如果置空失败，说明它不是最后一个线程，在置空过程中又有新线程加入，在进行加锁操作，新线程还未被设置为在释放锁的线程的后继
                while (me.next == null) {
                    //线程让步
                    Thread.yield();
                }
            }
            //如果存在后续的几点，将后续节点锁状态设为false，后续将获得锁
            me.next.locked = false;
            me.next = null;
        }

        private class MCSNode {
            private volatile boolean locked = true;
            private volatile MCSNode next = null;
        }
    }
}
