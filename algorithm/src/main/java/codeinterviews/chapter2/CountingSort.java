package codeinterviews.chapter2;


/**
 * 计数排序: 其实是桶排序的一种特殊情况。比如高考分值: 900, 那就可以分成901个桶,将这50万考生划分到这901个桶里。只是桶的大小粒度不一样。
 * 局限性: 计数排序只能用在数据范围不大的场景中, 数据范围K比要排序的数据n大很多,就不适合计数排序了。
 * 而且,计数排序只能给非负整数排序,如果要排序的数据是其他类型的,要将其不改变相对大小的情况下,转化为非负整数。
 *
 * 原理比较简单。时间复杂度: O(N)
 */
public class CountingSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        // 1. 遍历,获取数组元素中的最大值
        T max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (AlgorithmsUtils.less(max, array[i]))
                max = array[i];
        }

        // 2. 申请一个计数级数C,下标大小为[0, max]
        Integer[] c = new Integer[Integer.parseInt((String) max) + 1];
        for (int i = 0; i < array.length; i++) {
            int val = Integer.parseInt((String)array[i]);
            c[val]++;
        }

        // 3. 对C数组求和
        for (int i = 1; i < c.length; i++) {
            c[i] = c[i-1] + c[i];
        }

        // 4. 临时数组,存储排序后的结果
        Integer[] temp = new Integer[array.length];

        // 5. 对原数组进行后序遍历
        for (int i = array.length - 1; i > 0 ; i--) {
            int val = Integer.parseInt((String)array[i]);
            temp[c[val] - 1] = val;
            c[val]--;
        }

        // 6. 将结果烤贝回原数组
        for (int i = 0; i < array.length; i++) {
            array[i] = (T) temp[i];
        }
    }
}
