import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String N = in.next();

        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");

        char[] s = N.toCharArray();
        boolean inZero = false;
        int zLen = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length; i++) {
            char t = s[i];

            if (inZero) {
                if (t == '0') {
                  ++zLen;
                } else {
                    inZero = false;
                    if (zLen > maxLen) {
                        maxLen = zLen;
                    }
                    zLen = 0;
                }
            } else {
                if (t == '0') {
                    inZero = true;
                    ++zLen;
                }
            }

        }
        if (zLen > maxLen) {
            maxLen = zLen;
        }

        System.out.println(maxLen);
    }
}