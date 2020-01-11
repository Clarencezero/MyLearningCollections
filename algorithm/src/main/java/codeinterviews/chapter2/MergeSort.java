package codeinterviews.chapter2;

/**
 * 归并排序： 时间复杂度O(NlongN)。空间复杂度O(N)
 *
 */
public class MergeSort {
    // 难点一： 泛型数组
    // 难点二： right指数组坐标
    public static <T extends Comparable<? super T>> void mergeSort(T[] a) {
        T[] temArray = (T[]) new Comparable[a.length];
        mergeSort(a, temArray, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> void mergeSort(T[] a, T[] temArray, int left, int right) {
        if (left < right) {
            int center = (left + right) >> 1;

            // 错误一： 这个left 不能指定为0
            mergeSort(a, temArray,left, center);
            mergeSort(a, temArray, center+1, right);

            // 合并
            merge(a, temArray, left, center , right);
        }
    }

    /**
     * 合并两个数组，
     * 难点一： 合并两个数组，需要临时数组参与。存在三种情况。两边都有值，则互相比较。
     *          左边有值，右边没有值，则左边依次加入临时数组
     *          右边有值，左边没有值，则右边依次加入临时数组
     * 难点二： 将临时数组添加复制到源数组中
     */
    private static <T extends Comparable<? super T>> void merge(T[] a, T[] temArray, int left, int center, int right) {
        int pointerOne = left;
        int pointerTwo = center + 1;
        int temPointer = 0;

        while (pointerOne <= center && pointerTwo <= right) {
            // 非左即右
            if (a[pointerOne].compareTo(a[pointerTwo]) < 0) {
                temArray[temPointer++] = a[pointerOne++];
            } else {
                temArray[temPointer++] = a[pointerTwo++];
            }
        }

        while (pointerOne <= center)
            temArray[temPointer++] = a[pointerOne++];

        while (pointerTwo <= right)
            temArray[temPointer++] = a[pointerTwo++];

        temPointer = 0;

        while (left <= right) {
            a[left++] = temArray[temPointer++];
        }

    }
}
