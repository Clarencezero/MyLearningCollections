package leetcoce;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * abcabcbb
 * 3
 * 这个方法就是加入一个set,先判断是否重复,如果不重复,加入,并判断最大长度
 * 如果重复,则从头开始去掉字符,直到把重复字符去掉为止
 *
 * 优化考虑: 可以通过一个Map<Character, Integer>来记录元素的位置,这个遇到重复的数字的时候可以快速定位并删除往前的元素。
 */
public class LongestSubstring3SlidingWindow {
    public static void main(String[] args) {
        String                         s = "pwwkew";
        LongestSubstring3SlidingWindow l = new LongestSubstring3SlidingWindow();
        System.out.println(l.lengthOfLongestSubstring(s));
    }


    public int lengthOfLongestSubstring(String s) {
        Set<Character> set= new HashSet<>();
        int maxLength = 0;
        int i = 0,j = 0;
        int n = s.length();
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                maxLength = Math.max(maxLength, (j - i));
            } else {
                set.remove(s.charAt(i++));
            }
        }

        return maxLength;
    }

}
