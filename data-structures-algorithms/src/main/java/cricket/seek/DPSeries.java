package cricket.seek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DPSeries {
    @Override
    public String toString() {
        return "整理关于动态规划的求解";
    }

    //斐波那契数列
    class Fibonacci {

        public int getFibonacci1(int n) {//递归
            if (n == 0 || n == 1)
                return n;
            else
                return getFibonacci1(n - 1) + getFibonacci1(n - 2);
        }

        //优化，如果单纯使用递归，重复计算次数太多，用arr数组保存已计算过的斐波那契数
        private int[] arr = new int[100];//假设最多计算到100个斐波那契数

        public int getFibonacci2(int n) {
            if (n == 0 || n == 1)
                return n;
            else {
                if (arr[n] != 0) {
                    return arr[n];
                } else {
                    arr[n] = getFibonacci1(n - 1) + getFibonacci1(n - 2);
                    return arr[n];
                }
            }
        }

        //使用递推来求解斐波那契数列
        public int getFibonacci3(int n) {
            if (n == 0 || n == 1)
                return n;
            int f0 = 0, f1 = 1, sum = 0;
            for (int i = 2; i <= n; i++) {
                sum = f0 + f1;
                f0 = f1;
                f1 = sum;
            }
            return sum;
        }
    }

    //最长上升子序列-不连续
    class LIS {
        //朴素算法
        public int lengthOfLIS1(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            int dp[] = new int[arr.length];//以i为结尾的最长递增子序列的长度
            Arrays.fill(dp, 1); //初始化的最大值就是单个元素的长度1
            int max = 1; //保存到当前位置为止最大长度
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < i; j++) { // 内层循环遍历从 0 到 i
                    if (arr[j] < arr[i]) { // 只有小于nums[i]的元素才考虑进来
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
                max = Math.max(max, dp[i]);//更新最大长度
            }
            return max;
        }

        //贪心 + 二分查找
        public int lengthOfLIS2(int[] arr) {
            int len = 0;
            int[] dp = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                int sit = Arrays.binarySearch(dp, 0, len, arr[i]);//找到比nums[i]大的元素的位置
                sit = sit < 0 ? -sit - 1 : sit;
                dp[sit] = arr[i];//替换第一个比他大的位置
                if (sit == len) {//如果正好比子序列中所有的数都大，那么最长递增子序列长度增一
                    len++;
                }
            }
            return len;
        }


        //二分算法之扑克牌理解
        public int lengthOfLIS3(int[] nums) {
            int[] top = new int[nums.length];
            //牌堆初始化为0
            int piles = 0;
            for (int i = 0; i < nums.length; i++) {
                //要处理的扑克牌
                int poker = nums[i];

                //搜索左侧边界的二分查找
                int left = 0, right = piles;
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (top[mid] > poker) {
                        right = mid;
                    } else if (top[mid] < poker) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                //没找到合适的牌堆，新建一堆
                if (left == piles) {
                    piles++;
                }
                //把这张牌放到牌堆顶
                top[left] = poker;
            }
            //牌堆数就是LIS长度
            return piles;
        }
    }

    //最长公共子序列
    class LCS{
        //LCS,两个字符串最长公共子序列
        public void getLCS(String str1, String str2) {
            int str1Len = str1.length();
            int str2Len = str2.length();
            int[][] cLenNUm = new int[str1.length() + 1][str2.length() + 1];//默认赋值，[0][?],[?][0]默认两侧皆0,类似公式中0的场景
            //构造一个LCS长度数组
            for (int i = 1; i <= str1Len; i++) {
                for (int j = 1; j <= str2Len; j++) {
                    if (str1.charAt(i - 1) == str2.charAt(j - 1)) {//对应公式第二条相等
                        cLenNUm[i][j] = cLenNUm[i - 1][j - 1] + 1;
                    } else {//对应公式第三条不相等
                        cLenNUm[i][j] = Math.max(cLenNUm[i][j - 1], cLenNUm[i - 1][j]);
                    }
                }
            }

            //反推结果
            int i = str1Len;
            int j = str2Len;
            StringBuffer sb = new StringBuffer();//作为结果
            while (i > 0 && j > 0) {//这里其实处理了i=0,j=0的，对应公式0的反推场景
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {//反推公式中相等的场景
                    //该值一定是被选取到的，根据之前的公式知道两个字符串的下标都后推一位
                    sb.append(str1.charAt(i - 1));
                    i--;
                    j--;
                } else {//对应公式中不相等的反推场景
                    if (cLenNUm[i][j - 1] > cLenNUm[i - 1][j]) {//找大的那个方向，此处是左边大于上面，则该处的结果是来自左边
                        j--;
                    } else if (cLenNUm[i][j - 1] < cLenNUm[i - 1][j]) {//找大的那个方向，此处是上面大于左边，则该处的结果是来自上面
                        i--;
                    } else if (cLenNUm[i][j - 1] == cLenNUm[i - 1][j]) {
                        //对于有分支的可能时，我们选取单方向
                        i--;   //此结果对于结果1所选取方向，str1的下标左移一位.替换为j--，则结果对应与结果2选取的方向
                    }
                }
            }
            //由于是从后往前加入字符的，需要反转才能得到正确结果
            System.out.println(sb.reverse().toString());
        }
    }
}
