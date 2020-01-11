package codeinterviews.chapter2;

public class twoDimensionalArray {
    public static void main(String[] args) {
        // ① 准备好数组
        int[][] array = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};

        //②选取数组的右上角的数
        // array.length 是行数 array[0].length是列数
        System.out.println(findNumber(array,2));
        StringBuffer str = new StringBuffer();
    }

    public static boolean findNumber(int[][] array,int number)  {

        if (array == null) {
            return false;
        }

        int i = 0, j = array[i].length - 1;
        while (i < array.length && j >=0) {
            if (array[i][j] > number)
                //如果右上角 > 待测的数,就-1
                j--;
            else if (array[i][j] < number)
                i++;
            else
                return true;
        }

        return false;
    }

}
