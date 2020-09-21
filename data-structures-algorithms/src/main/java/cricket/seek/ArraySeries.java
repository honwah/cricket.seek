package cricket.seek;

/**
 * 关于数组的求解
 */
public class ArraySeries {

    @Override
    public String toString() {
        return "整理关于数组的求解。";
    }

    class MaxProduct {
        /**
         * 计算一个整形数组里的连续两个元素乘积的最大值
         *
         * @param array 整形数组
         * @return 乘积最大值
         */
        public int adjacentElementsProduct(int[] array) {
            int iMax = Integer.MIN_VALUE;
            for (int i = 0; i < array.length - 1; i++) {
                iMax = Math.max(array[i] * array[i + 1], iMax);
            }
            return iMax;
        }
    }

    class MaxSP {
        /**
         * 连续子数组的最大和
         *
         * @param array 整形数组
         * @return 最大和
         */
        public int maxSum(int[] array) {
            int max = 0, sum = 0;
            for (int value : array) {
                if (sum <= 0) {
                    sum = value;
                } else {
                    sum += value;
                }
                max = Math.max(max, sum);
            }
            return max;
        }

        /**
         * 连续子数组的最大乘积
         * @param array 整形数组
         * @return 最大乘积
         */
        public int maxProduct(int[] array) {
            int last_max, last_min, max = array[0], min = array[0], result = 0;
            for (int i = 1; i < array.length; i++) {
                //最大值的来源有三种
                // 1.如果arr[i]是正数，肯定与前面的最大值相乘得到最大值。
                //2.如果arr[i]是负数就会与前面的最小值相乘产生最大值。
                //3.如果前面的为0或者负数，arr[i]本身可能是最大值。
                last_max = Max(max * array[i], min * array[i], array[i]);
                last_min = Min(max * array[i], min * array[i], array[i]);
                max = last_max;
                min = last_min;
                result = Math.max(max, result);
            }
            return result;
        }

        private int Max(int a, int b, int c) {
            return Math.max(Math.max(a, b), c);
        }

        private int Min(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
        }
    }
}
