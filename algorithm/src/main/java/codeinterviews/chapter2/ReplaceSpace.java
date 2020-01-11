package codeinterviews.chapter2;

import java.util.Arrays;

public class ReplaceSpace {
    public static void main(String[] args) {
        String string = " we are happy.  ";
        char[] chars = string.toCharArray();
        replace(chars, chars.length);

    }

    public static void replaceBycris1313(char[] chars,int cLength) {
        // ①安全检查
        if (chars == null) return;

        int numberOfChar = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '\u0000')
                numberOfChar++;
        }
        if (numberOfChar != cLength) return;


    }

    public static void replace(char[] chars, int cLength) {
        int spaceNum = 0;

        // ①遍历String,查看空格数
        for (char c : chars) {
            if (c == ' ')
                spaceNum++;
        }

        char[] newChars = new char[3 * spaceNum + cLength - spaceNum];

        int index = newChars.length - 1;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == ' ') {
                newChars[index--] = '0';
                newChars[index--] = '2';
                newChars[index--] = '%';
            } else if (chars[i] != ' ') {
                newChars[index] = chars[i];
                index--;
            }
        }

        System.out.println(Arrays.toString(newChars));

    }
}
