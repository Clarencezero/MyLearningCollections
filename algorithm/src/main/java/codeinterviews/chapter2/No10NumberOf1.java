package codeinterviews.chapter2;

/**
 * 把一个整数减去1之后再和原来的整数做位与运算,
 * 得到的结果相当于是把整数的二进制表示中的最右边的一个1变成0,很多二进制的问题都可以用这个思路解决
 */
public class No10NumberOf1 {
    public static void main(String[] args) {
        int a = 1101;
        int count = 0;
        while (a > 0) {
            ++count;
            a = (a - 1) & a;
            System.out.println(a);
        }

        System.out.println("count: "  + count);

        System.out.println(1000 & 111);

        System.out.println(Integer.toBinaryString(9));

        byte b = 127;


        System.out.println(1 % 3); // 1
        System.out.println(2%3);
        System.out.println(4%3);

    }
}
