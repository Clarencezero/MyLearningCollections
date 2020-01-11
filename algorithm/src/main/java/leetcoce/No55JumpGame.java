package leetcoce;

public class No55JumpGame {
    static int[] datasetTure = {2, 3, 1, 1, 4};
    static int[] datasetFalse = {3, 2, 1, 0, 4};

    public static void main(String[] args) {
        System.out.println(canJump(datasetTure));
        // System.out.println(canJump(datasetFalse));
    }

    public static boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) return false;
            max = Math.max(nums[i] + i, max);
            i = nums[i] - 1;
            System.out.println("Max:" + max);
        }
        return true;
    }
}
