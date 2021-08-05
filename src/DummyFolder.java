import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DummyFolder {

    private static final int[] data1 = {0, 0};
    private static final int[] data2 = {1, 0, 1, 1, 0, 1, 0, 0};
    private static final int[] data4 = {0, 1, 0, 2, 3, 2, 3, 1, 3, 0, 3, 3, 0, 3, 0, 0, 1, 0, 1, 3, 2, 3, 2, 0, 2, 1, 2, 2, 1, 2, 1, 1};
    private static final int[] data8 = {3, 2, 3, 5, 4, 5, 4, 2, 4, 1, 4, 6, 3, 6, 3, 1, 0, 1, 0, 6, 7, 6, 7, 1, 7, 2, 7, 5, 0, 5, 0, 2, 0, 3, 0, 4, 7, 4, 7, 3, 7, 0, 7, 7, 0, 7, 0, 0, 3, 0, 3, 7, 4, 7, 4, 0, 4, 3, 4, 4, 3, 4, 3, 3, 2, 3, 2, 4, 5, 4, 5, 3, 5, 0, 5, 7, 2, 7, 2, 0, 1, 0, 1, 7, 6, 7, 6, 0, 6, 3, 6, 4, 1, 4, 1, 3, 1, 2, 1, 5, 6, 5, 6, 2, 6, 1, 6, 6, 1, 6, 1, 1, 2, 1, 2, 6, 5, 6, 5, 1, 5, 2, 5, 5, 2, 5, 2, 2};
    private static final int[] data16 = {4, 5, 4, 10, 11, 10, 11, 5, 11, 2, 11, 13, 4, 13, 4, 2, 3, 2, 3, 13, 12, 13, 12, 2, 12, 5, 12, 10, 3, 10, 3, 5, 3, 6, 3, 9, 12, 9, 12, 6, 12, 1, 12, 14, 3, 14, 3, 1, 4, 1, 4, 14, 11, 14, 11, 1, 11, 6, 11, 9, 4, 9, 4, 6, 7, 6, 7, 9, 8, 9, 8, 6, 8, 1, 8, 14, 7, 14, 7, 1, 0, 1, 0, 14, 15, 14, 15, 1, 15, 6, 15, 9, 0, 9, 0, 6, 0, 5, 0, 10, 15, 10, 15, 5, 15, 2, 15, 13, 0, 13, 0, 2, 7, 2, 7, 13, 8, 13, 8, 2, 8, 5, 8, 10, 7, 10, 7, 5, 7, 4, 7, 11, 8, 11, 8, 4, 8, 3, 8, 12, 7, 12, 7, 3, 0, 3, 0, 12, 15, 12, 15, 3, 15, 4, 15, 11, 0, 11, 0, 4, 0, 7, 0, 8, 15, 8, 15, 7, 15, 0, 15, 15, 0, 15, 0, 0, 7, 0, 7, 15, 8, 15, 8, 0, 8, 7, 8, 8, 7, 8, 7, 7, 4, 7, 4, 8, 11, 8, 11, 7, 11, 0, 11, 15, 4, 15, 4, 0, 3, 0, 3, 15, 12, 15, 12, 0, 12, 7, 12, 8, 3, 8, 3, 7, 3, 4, 3, 11, 12, 11, 12, 4, 12, 3, 12, 12, 3, 12, 3, 3, 4, 3, 4, 12, 11, 12, 11, 3, 11, 4, 11, 11, 4, 11, 4, 4, 5, 4, 5, 11, 10, 11, 10, 4, 10, 3, 10, 12, 5, 12, 5, 3, 2, 3, 2, 12, 13, 12, 13, 3, 13, 4, 13, 11, 2, 11, 2, 4, 2, 7, 2, 8, 13, 8, 13, 7, 13, 0, 13, 15, 2, 15, 2, 0, 5, 0, 5, 15, 10, 15, 10, 0, 10, 7, 10, 8, 5, 8, 5, 7, 6, 7, 6, 8, 9, 8, 9, 7, 9, 0, 9, 15, 6, 15, 6, 0, 1, 0, 1, 15, 14, 15, 14, 0, 14, 7, 14, 8, 1, 8, 1, 7, 1, 4, 1, 11, 14, 11, 14, 4, 14, 3, 14, 12, 1, 12, 1, 3, 6, 3, 6, 12, 9, 12, 9, 3, 9, 4, 9, 11, 6, 11, 6, 4, 6, 5, 6, 10, 9, 10, 9, 5, 9, 2, 9, 13, 6, 13, 6, 2, 1, 2, 1, 13, 14, 13, 14, 2, 14, 5, 14, 10, 1, 10, 1, 5, 1, 6, 1, 9, 14, 9, 14, 6, 14, 1, 14, 14, 1, 14, 1, 1, 6, 1, 6, 14, 9, 14, 9, 1, 9, 6, 9, 9, 6, 9, 6, 6, 5, 6, 5, 9, 10, 9, 10, 6, 10, 1, 10, 14, 5, 14, 5, 1, 2, 1, 2, 14, 13, 14, 13, 1, 13, 6, 13, 9, 2, 9, 2, 6, 2, 5, 2, 10, 13, 10, 13, 5, 13, 2, 13, 13, 2, 13, 2, 2, 5, 2, 5, 13, 10, 13, 10, 2, 10, 5, 10, 10, 5, 10, 5, 5};

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            final int lines = Integer.parseInt(reader.readLine());

            if (lines != 1 && lines != 2 && lines != 4 && lines != 8 && lines != 16) {
                throw new IllegalStateException();
            }

            char[][] data = new char[lines][lines];
            for (int i = 0; i < lines; i++) {
                final char[] line = reader.readLine().toCharArray();
                for (int j = 0; j < lines; j++) {
                    if (j < line.length) {
                        data[i][j] = line[j];
                    } else {
                        data [i][j] = ' ';
                    }
                }
            }

            switch (lines) {
                case 1:  System.out.println(answer(data, data1)); break;
                case 2:  System.out.println(answer(data, data2)); break;
                case 4:  System.out.println(answer(data, data4)); break;
                case 8:  System.out.println(answer(data, data8)); break;
                case 16:  System.out.println(answer(data, data16)); break;
                default: throw new IllegalStateException();
            }

        }
    }

    private static String answer(char[][] data, int[] instructions) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instructions.length; i += 2) {
            sb.append(data[instructions[i]][instructions[i + 1]]);
        }
        return sb.toString();
    }

}

