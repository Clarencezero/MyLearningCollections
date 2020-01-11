package leetcoce;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * abcabcbb
 * 3
 *
 */
public class LongestSubstring4ASCII {
    public static void main(String[] args) {
        String                 s = "pwwkew";
        LongestSubstring4ASCII l = new LongestSubstring4ASCII();
        System.out.println(l.lengthOfLongestSubstring(s));
    }


    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128];
        for (int j = 0, i = 0; j < n; j++) {
            System.out.println(index[s.charAt(j)]);
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }

        return ans;
    }

}
