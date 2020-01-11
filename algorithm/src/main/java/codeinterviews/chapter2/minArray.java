package codeinterviews.chapter2;

/*
* 旋转数组的最小数字
* */
public class minArray {
    public static void main(String[] args) {
        int[] ints = {3,4,5,6,7,1,0,1,2};
        int result = min(ints);
        System.out.println(result);
    }

    private static int min(int[] ints) {
        return min(ints,0,ints.length - 1);
    }

    private static int min(int[] ints, int lo, int hi) {
        int i = lo,j = hi;

        while (!((i+1)==j)) {
            int mid = (i+j)/2;
            if (less(ints[i], ints[mid])) i = mid;
            else if (less(ints[mid], ints[j])) j = mid;
        }
        return ints[j];


    }

    private static boolean less(int anInt, int anInt1) {
        return anInt1 > anInt;
    }
}
