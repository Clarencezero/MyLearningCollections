package codeinterviews.chapter2;

import cn.hutool.core.util.RandomUtil;

import java.util.Random;

public class AlgorithmsUtils {
    /**
     * 比较
     * @param a
     * @param b
     * @return
     */
    public static boolean less(Integer a, Integer b) {
        return a.compareTo(b) < 0;
    }

    public static <T extends Comparable<? super T>> boolean less(T val1, T val2) {
        return val1.compareTo(val2) < 0;
    }

    public static void exchange(int one, int two, int[] array) {
        int temp = array[one];
        array[one] = array[two];
        array[two] = temp;
    }

    public static <T extends Comparable<? super T>> void exchange(int index01, int index02, T[] array) {
        T temp = array[index01];
        array[index01] = array[index02];
        array[index02] = temp;
    }

    public static void printIntArray(int[] array) {
        for (int i : array) {
            System.out.println(i);
        }
    }


    public static void printArray(Integer[] array) {
        for (Integer integer : array) {
            System.out.println(integer);
        }
    }


    public static Integer[] dataReadyReturnIntArray(int size) {
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = RandomUtil.randomInt();
        }
        return integers;
    }

    public static Integer[] dataReadySmallIntArray() {
        Integer[] integers = new Integer[10];
        int a = 10;
        for (int i = 0; i < 10; i++) {
            integers[i] = a--;
        }
        return integers;
    }

}
