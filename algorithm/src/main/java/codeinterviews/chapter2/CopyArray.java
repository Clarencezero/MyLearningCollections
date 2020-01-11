package codeinterviews.chapter2;

public class CopyArray {
    public static void main(String[] args) {
        int[] numbers1 = new int[20];
        int a = 0;
        for (int i = 0; i < 10; i++) {
            if ((i) % 2 != 0) {
                numbers1[a] = i + 1;
                a++;
            }
        }

        int[] numbers2 = new int[]{1, 3, 5, 7, 9,9,11,13,15,15};

        insertArray(numbers1, numbers2, 5);
    }

    /**/
    public static int[] insertArray(int[] array1, int[] array2, int num) {

        int aLength = num + array2.length - 1;
        int array1Index = num - 1;
        int array2Index = array2.length - 1;
        while (array1Index>=0 && array2Index>=0)  {
            if (array1[array1Index] > array2[array2Index]) {
                array1[aLength--] = array1[array1Index--];
            } else if (array1[array1Index] < array2[array2Index]) {
                array1[aLength--] = array2[array2Index--];
            } else {
                array1[aLength] = array1[array1Index];
                array1[aLength--] = array2[array2Index];
            }
        }

        System.out.println(aLength + ":" + array2Index);
        while (array1Index >=0) {
            array1[aLength--] = array1[array1Index--];
        }
        while (array2Index >= 0) {
            array1[aLength--] = array2[array2Index--];
        }




        for (int a : array1) {
            System.out.println("--" + a);
        }

        return null;
    }

}
