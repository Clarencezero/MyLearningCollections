package leetcoce;

public class No58LengthOfLastWorld {
    public static void main(String[] args) {
        String h = "h";
        System.out.println(lengthOfLastWord(h));
    }

    public static int lengthOfLastWord(String s) {
        int index = s.length() - 1;
        int count = 0;
        for (int i = index; i >= 0; i--) {
            if (s.charAt(i) != ' ')  count++;
            else break;
        }
        return count;
    }


}
