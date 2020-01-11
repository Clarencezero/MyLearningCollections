package leetcoce;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * abcabcbb
 * 3
 *
 * Brute Force 暴力解法: 全部遍历,并用Set集合判断是否存在重复字符串
 */
public class LongestSubstring {
    public static void main(String[] args) {
        String s = " ";
        LongestSubstring l = new LongestSubstring();
        System.out.println(l.lengthOfLongestSubstring(s));
    }


    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int sLength = s.length();
        for (int i = 0; i < sLength ; i++) {
            for (int j = i+1 ; j <= sLength; j++) {
                if (unique(s, i, j)) {
                    maxLength = Math.max(maxLength, (j - i));
                }
            }
        }
        return maxLength;
    }

    public boolean unique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character c = s.charAt(i);
            if (set.contains(c)) {
                return false;
            }
            set.add(c);
        }
        return true;
    }
}
