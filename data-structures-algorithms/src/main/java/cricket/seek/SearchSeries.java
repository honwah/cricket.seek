package cricket.seek;

import java.util.Arrays;

public class SearchSeries {
    @Override
    public String toString() {
        return "整理关于查找的求解";
    }

    // 顺序表查找
    class Sequential_Search {

        // 顺序查找
        public int seqSearch(int[] arr, int key) {
            int n = arr.length;
            for (int i = 0; i < n; i++) {
                if (key == arr[i])
                    return i;
            }
            return -1;
        }

        //顺序查找优化，带哨兵
        public int seqSearch2(int[] arr, int key) {
            if (key == arr[0])
                return 0;
            int i = arr.length - 1;
            arr[0] = key;  //将arr[0]设为哨兵
            while (arr[i] != key)
                i--;
            return i == 0 ? -1 : i;  //返回0说明查找失败
        }
    }

    //二分查找
    public  int binarySearch2(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int middle = low + ((high - low) >> 1);//右移运算符，num >> 1,相当于num除以2,代替 (low + high) >> 1，是因为数组很长时避免溢出
            if (value == array[middle]) {
                return middle;
            }
            if (value > array[middle]) {
                low = middle + 1;
            }
            if (value < array[middle]) {
                high = middle - 1;
            }
        }
        return -1;
    }

    //插值查找
    public  int insertSearch(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int middle = low + ((value - array[low]) / (array[high] - array[low])) * (high - low);/*插值*/
            if (value == array[middle]) {
                return middle;
            }
            if (value > array[middle]) {
                low = middle + 1;
            }
            if (value < array[middle]) {
                high = middle - 1;
            }
        }
        return -1;
    }

    //斐波那契查找
    public int fibonacciSearch(int[] array, int key) {
        int length = array.length;
        int k = 0;
        while (length > fibonacci(k) - 1) {//获取k值
            k++;
        }
        int[] temp = Arrays.copyOf(array, fibonacci(k) - 1);
        for (int i = length; i < temp.length; i++) {//对数组中新增的位置进行赋值
            temp[i] = array[length - 1];//用原数组最后的值填充
        }
        int low = 0;
        int hight = length - 1;//high不用等于fibonacci(k)-1，效果相同
        while (low <= hight) {
            int middle = low + fibonacci(k-1) - 1;
            if (temp[middle] > key) {//要查找的值在前半部分
                hight = middle - 1;
                k = k - 1;
            } else if (temp[middle] < key) {//要查找的值在后半部分
                low = middle + 1;
                k = k - 2;
            } else {
                if (middle <= hight) {
                    return middle;
                } else {
                    return hight;//middle位于新增的数组中时，返回hight,因为后面值都是用最后值填充的
                }
            }
        }
        return -1;
    }

    //斐波那契数列
    private int fibonacci(int n) {
        if (n == 0 || n == 1)
            return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
