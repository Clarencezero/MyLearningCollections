package codeinterviews.chapter2;


/**
 * 快速排序也是采用了分治思想,时间复杂度O(NlongN),空间复杂度O(N)
 * 它将一个数组分成两个数组,将部分独立地排序。
 * 快速排序和归并排序是互补的: 归并排序将数组分成两个子数组分别排序,并将有序的子数组归并以将整个数组排序;
 * 而快速排序将数组排序的方式则是当两个子数组都有序时整个数组也就自然有序了
 *
 *
 */
public class QuickSort {

    /**
     * 算法一： 这个是最常见的快速排序算法： 只需要维护两个指针即可
     */
    public static <T extends Comparable<? super T>> void sortByTwopoint(T[] array) {
        // 1.
        sortByTwopoint(array, 0, array.length - 1);
    }

    private static <T extends Comparable<? super T>> void sortByTwopoint(T[] array, int left, int right) {
        // 1. 难点一: 会忘记这个
        if (left >= right) return;

        // 2. 选分区
        int index = partition(array, left, right);

        // 3. 左快排
        sortByTwopoint(array, left, index);

        // 4. 右快排
        sortByTwopoint(array, index + 1, right);
    }

    /**
     * 这个分组： 左边选择大于val， 右边选择小于val
     */
    private static <T extends Comparable<? super T>> int partition(T[] array, int left, int right) {
        // 1. 选取最左边为切分点
        T val = array[left];
        int i = left;
        int j = right + 1;
        for (; ; ) {
            // ++i： 排除第一项
            // val < 左边数据
            while (AlgorithmsUtils.less(array[++i], val)) {
                // 左指针元素小于val， 则只需要判断指针是否越界就行了
                if (i == right) break;
            }

            // val > 右边数据
            while (AlgorithmsUtils.less(val, array[--j])) {
                if (j == left) break;
            }

            if (i >= j) break;

            AlgorithmsUtils.exchange(i, j, array);

        }

        AlgorithmsUtils.exchange(left, j, array);
        return j;
    }

    /**
     * 算法二： 三向切分的快速排序
     */
    public static <T extends Comparable<? super T>> void sortByThreepoint(T[] array) {
        sortByThreepoint(array, 0, array.length - 1);
    }

    /**
     * 三向切分的原则是：
     * 维护一个指针1t 使得 a[lo...1t-1]中的元素都小于v
     * 维护一个指针gt 使得 a[gt+1...hi]中的元素都大于v
     * 维护一个指针i  使得 a[i...gt]中的元素都等于v
     * <p>
     * 故需要处理以下几种情况：
     * 1. a[i] < v, 和左边1t交换，即a[1t]和a[i]交换，1t++,i++
     * 2. a[i> > v, 和右边gt交换，即a[i]和a[gt}交换，i++, gt--
     * 3. a[i] = v, i++
     */
    private static <T extends Comparable<? super T>> void sortByThreepoint(T[] array, int left, int right) {
        if (left >= right) return;
        T val = array[left];
        int leftPoint = left;
        int rightPoint = right;
        int i = leftPoint + 1;

        // 难点二: i不能<=right,这样就相当于遍历了一遍了。正确的是当指针i和rightPoint相遇就停止
        while (i <= rightPoint){
            int comResult = array[i].compareTo(val);
            // 难点二: a[i] 小,则和左边交换
            if (comResult < 0) AlgorithmsUtils.exchange(i++, leftPoint++, array);

                // 难点一: a[i] 大, 则和右边交换,i是不用自增的。如果自增了,那小于v的值就会被跳过,不能正确排序,所以还需要指针i指向该小于V的值
            else if (comResult > 0) AlgorithmsUtils.exchange(i, rightPoint--, array);
            else i++;
        }

        // 难点三: 左节点和右节点指针确定。都需要根据point-1,和+1
        sortByThreepoint(array, left, leftPoint- 1);
        sortByThreepoint(array, rightPoint +1, right);
    }


}
