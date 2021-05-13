package cricket.seek;

public class SortSeries {
    @Override
    public String toString() {
        return "整理关于排序的求解";
    }

    //快速排序，左右指针
    public class QuickSort {
        //arr 需要排序的数组
        public void sort(int[] arr, int left, int right) {
            // 递归终止条件
            if (left >= right) {
                return;
            }
            // 第一步，找出分区后枢轴的下标
            int pivotIndex = partition(arr, left, right);
            // 第二步，对左子数组排序
            sort(arr, left, pivotIndex - 1);
            // 第三步，对右子数组排序
            sort(arr, pivotIndex + 1, right);
        }

        //partition算法用途是：有一个数组array[]和其中任意一个数组元素x，修改数组，将数组中小于x的元素都移到x的左边，将大于x的元素都移动x的右边
        //返回值： 返回值为最终x在数组中的索引值。
        //left 左指针，用于从左往右扫描元素
        //right 右指针，用于从右往左扫描元素
        private int partition(int[] arr, int left, int right) {
            // 以起始下标作为枢轴元素
            int pivot = arr[left];
            int index = left;
            while (left < right) {
                //因枢轴元素在左边选：int pivot = arr[left]，所以要先从右走
                // 原因:假如数组{0,1,2,3},第一个数最小，如果先让左指针移动，左右指针将在1位置停止，交换数据后变成{1,0,2,3},并不能保证所有左边的值都小于枢轴元素
                // 从右往左扫描，寻找比枢轴小的元素
                while (left < right && arr[right] >= pivot) {//右指针遇到小于pivot的停下
                    right--;
                }
                // 从左往右扫描，寻找比枢轴大的元素
                while (left < right && arr[left] <= pivot) {//左指针遇到大于pivot的停下
                    left++;
                }
                //交换a[left]和a[right]
                swap(arr, left, right);
            }
            // 交换轴枢和right所在位置元素
            swap(arr, index, right);
            return right;
        }

        //交换数据
        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
