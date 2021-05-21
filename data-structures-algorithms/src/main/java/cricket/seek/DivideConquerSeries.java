package cricket.seek;

import java.util.Arrays;

public class DivideConquerSeries {

    @Override
    public String toString() {
        return "整理关于分治法的求解";
    }

    //循环赛日程表
    class RoundRobinSchedule {

        //输出日程表
        public void printTable(int[][] array, int n) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    System.out.print(array[i][j] + " ");
                }
                System.out.println();
            }
        }

        //k：n=2^k个运动员
        //arr：循环赛日程表，使用时小标从1开始，所以传入数组大小应为arr[2^k+1][2^k+1]
        public void table(int k, int[][] arr) {
            //2^1个选手比赛日程可直接求得，得到左上角元素
            arr[1][1] = 1;
            arr[1][2] = 2;
            arr[2][1] = 2;
            arr[2][2] = 1;
            for (int t = 1; t < k; t++) {
                int l = pow(2, t);
                int m = pow(2, t + 1);
                //左下角
                for (int i = 1 + l; i <= m; i++) {
                    for (int j = 1; j <= l; j++) {
                        arr[i][j] = arr[i - l][j] + l;
                    }
                }
                //右上角
                for (int i = 1; i <= l; i++) {
                    for (int j = 1 + l; j <= m; j++) {
                        arr[i][j] = arr[i + l][j - l];
                    }
                }
                //右下角
                for (int i = 1 + l; i <= m; i++) {
                    for (int j = 1 + l; j <= m; j++) {
                        arr[i][j] = arr[i - l][j - l];
                    }
                }
            }
        }

        //求x的n次幂
        public int pow(int x, int y) {
            int result;
            if (y == 1)
                return x;
            if (y % 2 == 0)
                result = pow(x, y / 2) * pow(x, y / 2);
            else
                result = pow(x, (y + 1) / 2) * pow(x, (y - 1) / 2);
            return result;
        }
    }


    //汉诺塔问题
    class TowersOfHanoi {
        int m = 0;//标记移动次数

        //实现移动的函数
        public void move(int disks, char N, char M) {
            System.out.println("第" + (++m) + " 次移动 : " + " 把 " + disks + " 号圆盘从 " + N + " ->移到->  " + M);
        }

        //递归实现汉诺塔的函数
        public void hanoi(int n, char A, char B, char C) {
            if (n == 1)//圆盘只有一个时，只需将其从A塔移到C塔
                move(1, A, C);//将编号为1的圆盘从A移到C
            else {//否则
                hanoi(n - 1, A, C, B);//把A塔上编号1~n-1的圆盘移到B上，以C为辅助塔
                move(n, A, C);//把A塔上编号为n的圆盘移到C上
                hanoi(n - 1, B, A, C);//把B塔上编号1~n-1的圆盘移到C上，以A为辅助塔
            }
        }
    }

    //棋盘覆盖问题
    class Chess {

        int title = 1;//L型骨牌号,也可理解为被覆盖后显示的数字
        int[][] board;//棋盘

        //打印棋盘
        //dx 不可覆盖棋子x坐标
        //dy 不可覆盖棋子y坐标
        //size 棋盘大小
        public void printBoard(int dx, int dy, int size) {
            board = new int[size][size];
            // 设置特殊位置
            board[dx][dy] = -1;
            chessBord(0, 0, dx, dy, size);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.printf("%5s", board[i][j]);
                }
                System.out.println();
            }
        }

        //tx 棋盘左上角x坐标
        //ty 棋盘左上角y坐标
        //dx 不可覆盖棋子x坐标
        //dy 不可覆盖棋子y坐标
        //size 棋盘大小
        private void chessBord(int tx, int ty, int dx, int dy, int size) {
            if (size == 1) {
                return;
            }
            int t = title++;  //注意这里一定要吧title存在当前的递归层次里，否则进入下一层递归全局变量会发生改变
            int s = size / 2; //分割棋盘
            // 覆盖左上
            if (dx < tx + s && dy < ty + s) {
                // 特殊方格在棋盘左上
                chessBord(tx, ty, dx, dy, s);
            } else {
                //棋盘左上无特殊方格，可以覆盖
                board[tx + s - 1][ty + s - 1] = t;//覆盖小棋盘右下角,以形成L型
                chessBord(tx, ty, tx + s - 1, ty + s - 1, s);
            }

            // 覆盖左下
            if (dx >= tx + s && dy < ty + s) {
                // 特殊方格在棋盘左下
                chessBord(tx + s, ty, dx, dy, s);
            } else {
                //棋盘左下无特殊方格，可以覆盖
                board[tx + s][ty + s - 1] = t;//覆盖小棋盘右上角,以形成L型
                chessBord(tx + s, ty, tx + s, ty + s - 1, s);
            }

            //覆盖右上
            if (dx < tx + s && dy >= ty + s) {
                // 特殊方格在棋盘右上
                chessBord(tx, ty + s, dx, dy, s);
            } else {
                //棋盘右上无特殊方格，可以覆盖
                board[tx + s - 1][ty + s] = t;//覆盖小棋盘左下角 ,以形成L型
                chessBord(tx, ty + s, tx + s - 1, ty + s, s);
            }
            //覆盖右下
            if (dx >= tx + s && dy >= ty + s) {
                // 特殊方格在棋盘右下
                chessBord(tx + s, ty + s, dx, dy, s);
            } else {
                //棋盘右下无特殊方格，可以覆盖
                board[tx + s][ty + s] = t;//覆盖小棋盘左上角,以形成L型
                chessBord(tx + s, ty + s, tx + s, ty + s, s);
            }
        }
    }

    //众数问题
    class Majority {

        int ans = 0; //众数的重数
        int idx = 0; //众数的下标

        //分治算法求众数
        //arr有序数组
        //l,左边起始界限
        //r,右边终止界限
        private void split(int[] arr, int l, int r) {
            if (l > r) return;
            int ll = l; //记录原来的l位置
            int rr = r; //记录原来的r位置
            int mid = (l + r) >> 1;
            for (; l < mid && arr[l] != arr[mid]; l++) ; //寻找众数的最左边
            for (; r > mid && arr[r] != arr[mid]; r--) ; //寻找众数的最右边
            //经过两个for循环后，众数个数就是r-l+1
            if (ans <= r - l + 1) //更新答案
            {
                if (ans == r - l + 1) {
                    idx = Math.min(mid, idx);//当有多个同样重数的众数，优先输出数值更小的数的众数
                } else
                    idx = mid;
                ans = r - l + 1;
            }
            if (l - 1 - ll + 1 >= ans) //剪枝,判断是否有必要搜寻,如果左边个数（l - 1）-ll+1还没有目前重数大，那就没必要继续搜寻了
                split(arr, ll, l - 1); // 对左边部分分治
            if (rr - r - 1 + 1 >= ans) //剪枝
                split(arr, r + 1, rr); // 对右边部分分治
        }

        //输出众数和重数
        public void printMajority(int[] arr) {
            Arrays.sort(arr);//先排序，使用分治策略解决众数问题需要原集合有序
            split(arr, 0, arr.length - 1);
            System.out.printf("%d %d \r\n", arr[idx], ans);
        }
    }
}
