package leetcoce;

import java.util.HashSet;
import java.util.Set;

/**
 * Given a string, find the length of the longest substring without repeating characters.
 * abcabcbb
 * 3
 *
 * 比如先确定最大长度 5,判断是否重复,再确定最大长度4,判断是否有重复,如果有,则全部返回。
 * 但是时间还是超过了限制
 */
public class LongestSubstring2 {
    public static void main(String[] args) {
        String            s = "";
        LongestSubstring2 l = new LongestSubstring2();
        System.out.println(l.lengthOfLongestSubstring(s));
    }


    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        for (int i = s.length(); i > 0; i--) {
            for (int j = 0 ; j + i <= s.length(); j++) {
                if (unique(s, j, j+i)) {
                    return Math.max(maxLength, i) ;
                }
            }
        }
        return  maxLength;
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
