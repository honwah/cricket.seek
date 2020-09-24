package cricket.seek;

/**
 * 关于线程的求解
 */
public class ThreadSeries {
    @Override
    public String toString() {
        return "整理关于线程的求解";
    }

    /**
     * t1、t2、t3这三个线程按照顺序去执行
     */
    class SeqExecution {
        private volatile int orderNum = 1;

        public synchronized void methodA() {
            while (orderNum != 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("A");
            orderNum = 2;
            notifyAll();
        }

        public synchronized void methodB() {
            while (orderNum != 2) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("B");
            orderNum = 3;
            notifyAll();
        }

        public synchronized void methodC() {
            while (orderNum != 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C");
            orderNum = 1;
            notifyAll();
        }
    }
}

/**
 * 三个售票窗口同时售20张票
 */
class Station extends Thread {

    //票数
    static int tick = 20;
    static Object ob = "ok";

    public Station(String name) {
        super(name);
    }

    @Override
    public void run() {

        while (tick > 0) {
            synchronized (ob) {
                if (tick > 0) {
                    System.out.println(getName() + "售出第" + tick + "张票。");
                    tick--;
                } else {
                    System.out.println("票售完了。");
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 龟兔赛跑，动物基类
 */
abstract class Animal extends Thread {
    //回调通知比赛结束
    public CallBack cb;
    //比赛长度
    int length = 2000;
    //比赛结束
    boolean exit = false;
    //速度
    int speed;

    @Override
    public void run() {
        super.run();
        while (!exit && length > 0) {
            running();
        }
    }

    //赛跑
    public abstract void running();

    public static interface CallBack {
        public void win(String notice);
    }
}

/**
 * 龟兔赛派，兔子
 */
class Rabbit extends Animal {

    public Rabbit() {
        setName("兔子");
        speed = 5;
    }

    @Override
    public void running() {
        length -= speed;
        System.out.println(getName() + "跑了" + speed + "米" + "距离终点还有" + length + "米。");
        if (length <= 0) {
            //通知乌龟
            cb.win(getName() + "赢了！");
        }
        try {
            if ((2000 - length) % 20 == 0) {
                sleep(1000);
            } else {
                sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 龟兔赛跑，乌龟
 */
class Tortoise extends Animal {

    public Tortoise() {
        setName("乌龟");
        speed = 2;
    }

    @Override
    public void running() {
        length -= speed;
        System.out.println(getName() + "跑了" + speed + "米" + "距离终点还有" + length + "米。");
        if (length <= 0) {
            //通知兔子
            cb.win(getName() + "赢了！");
        }
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 龟兔赛派，回调通知
 */
class NoticeRunner implements Animal.CallBack {

    Animal animal;

    public NoticeRunner(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void win(String notice) {
        System.out.println(notice);
        animal.exit = true;
    }
}


