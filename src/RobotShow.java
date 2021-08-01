import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.max;

// https://www.codingame.com/training/easy/robot-show
/*
Input
10
2
2 6

Output
8

Example

In a duct of length 10m two bots move face-to-face

    >       <
+-+-+-+-+-+-+-+-+-+-+  init state
0 1 2 3 4 5 6 7 8 9 10

        X              after 2 sec
+-+-+-+-+-+-+-+-+-+-+  bump at 4, bounce
0 1 2 3 4 5 6 7 8 9 10

<               >      after 4 sec
+-+-+-+-+-+-+-+-+-+-+  the L bot is exiting
0 1 2 3 4 5 6 7 8 9 10

                    >  after 2 sec
+-+-+-+-+-+-+-+-+-+-+  the R bot is exiting
0 1 2 3 4 5 6 7 8 9 10


Total bot running time: 8 sec
 */
class RobotShow {
    private final int length;
    private final int n;
    private final int[] bots;

    private RobotShow(int length, int n, int[] bots) {
        this.length = length;
        this.n = n;
        this.bots = bots;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final int l = Integer.parseInt(reader.readLine());
            final int n = Integer.parseInt(reader.readLine());

            final StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
            final int[] bots = new int[n];
            for (int i = 0; i < n; i++) {
                bots[i] = Integer.parseInt(stringTokenizer.nextToken());
            }

            System.out.println(new RobotShow(l, n, bots).solve());
        }
    }

    private int solve() {

        int pretender = 0;
        for (int i = 0; i < n; i++) {
            final int bot = bots[i];
            final int maxToBorder = max(length - bot, bot);
            pretender = max(pretender, maxToBorder);
        }

        return pretender;
    }

}
