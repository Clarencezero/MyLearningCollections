package leetcoce;

public class No53MaximunSubarray {
    public static void main(String[] args) {
        int[] a = {-2,1,-3,4,-1,2,1,-5,4};
        int i = maxSubArray(a);
        System.out.println(i);
    }


    // 贪心算法
    // 就是前面几个数相加<0的丢弃
    public  static int maxSubArray(int[] nums) {
        int size = nums.length;
        int sum = Integer.MIN_VALUE;
        int temp = 0;
        for (int i = 0; i < size; i++) {
            temp += nums[i];
            sum = max(sum, temp);
            if (temp < 0)
                temp = 0;
        }
        return sum;
    }

    public static int max(int a , int b) {
        if (a > b)
            return a;
        else
            return b;
    }

}
