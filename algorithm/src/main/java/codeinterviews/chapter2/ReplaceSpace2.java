package codeinterviews.chapter2;


public class ReplaceSpace2 {
    public static void main(String[] args) {
        StringBuffer str = new StringBuffer(" we are happy");
        String result = replaceSpace(str);
        System.out.println(result);
    }

    private static String replaceSpace(StringBuffer str) {
        int index = str.indexOf(" ");

        if (index >= 0) {
            str.replace(index, index + 1, "%20");
            replaceSpace(str);
        }
        return str.toString();
    }
}
