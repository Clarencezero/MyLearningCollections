package leetcoce;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 0214
 * @createTime 2019/1/21
 * @description
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] tar = {2, 7, 11, 15};
        int target = 13;
        int[] result = twoSum2(tar, target);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    // 方法一: 时间复杂度为O(n*n)
    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length; j++) {
                int b = nums[j];
                if (nums[i] + b == target) {
                    return new int[] {i,j};
                }
            }
        }
        throw new IllegalArgumentException("No Two sum solution");
    }

    // 方法二: 时间复杂度为O(n)。用空间换时间
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer>  map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int targetNum =target - nums[i];
            if (map.containsKey(targetNum) && map.get(targetNum) != i) {
                return new int[] {i, map.get(targetNum)};
            }
        }


        throw new IllegalArgumentException("No Two sum solution");
    }




    /**
     * 分析一:
     *  语句int num1, num2;的频度为1
     *  语句i=0;的频度为1；
     *  语句i<n; i++; num1+=1; j=1; 的频度为n；
     *  语句j<=n; j*=2; num2+=num1;的频度为n*log2n；
     *  T(n) = 2 + 4n + 3n*log2n
     * 分析二:
     *  忽略掉T(n)中的常量、低次幂和最高次幂的系数
     *  f(n) = n*log2n
     *
     * 分析三:
     *  lim(T(n)/f(n)) = (2+4n+3n*log2n) / (n*log2n)
     *                 = 2*(1/n)*(1/log2n) + 4*(1/log2n) + 3
     *  当n趋向于无穷大，1/n趋向于0，1/log2n趋向于0,所以极限等于3
     * 结论: T(n) = O(n*log2n)
     */
    public static void analyze() {
        int n = 100;
        int num1 = 0, num2 = 0;
        for (int i = 0; i < n; i++) {
            num1 +=1;
            for (int j = 1; j <= n; j*=2) {
                num2 += num1;
            }
        }
    }
}
