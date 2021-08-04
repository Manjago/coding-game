import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FoldingANote {

    private final int slices;
    private final int height;
    private final int width;
    private final char[][][] data;

    public FoldingANote(int n) {
        this(1, n, n, new char[1][n][n]);
    }

    private FoldingANote(int slices, int height, int width, char[][][] data) {
        this.slices = slices;
        this.height = height;
        this.width = width;
        this.data = data;
    }

    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            final int lines = Integer.parseInt(reader.readLine());
            final FoldingANote solver = new FoldingANote(lines);

            for (int i = 0; i < lines; i++) {
                solver.init(i, reader.readLine());
            }

            System.out.println(solver.answer());
        }

    }

    public void init(int num, String strdata) {
        char[] chars = strdata.toCharArray();
        if (width >= 0) System.arraycopy(chars, 0, data[0][num], 0, width);
    }

    public FoldingANote smartfold1() {
        final int newSlices = slices * 2;
        final int newWidth = width / 2;
        final char[][][] newData = new char[newSlices][height][newWidth];

        for (int s = 0; s < slices; s++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < newWidth; j++) {
                    newData[s][i][j] = data[s][i][j];
                    newData[slices + s][i][j] = data[slices - 1 - s][i][width - 1 - j];
                }
            }
        }

        return new FoldingANote(newSlices, height, newWidth, newData);
    }

    public FoldingANote fold1() {

        final int newSlices = slices * 2;
        final int newWidth = width / 2;
        final char[][][] newData = new char[newSlices][height][newWidth];

        for (int s = 0; s < slices; s++) {
            for (int i = 0; i < height; i++) {
                System.arraycopy(data[s][i], 0, newData[s][i], 0, newWidth);
            }
        }

        int newS = slices;
        for (int s = slices - 1; s >= 0; s--) {
            for (int i = 0; i < height; i++) {
                int newJ = 0;
                for (int j = width - 1; j >= newWidth; j--) {
                    newData[newS][i][newJ] = data[s][i][j];
                    ++newJ;
                }
            }
            ++newS;
        }

        return new FoldingANote(newSlices, height, newWidth, newData);
    }

    public FoldingANote fold2() {

        final int newSlices = slices * 2;
        final int newHeight = height / 2;
        final char[][][] newData = new char[newSlices][newHeight][width];

        for (int s = 0; s < slices; s++) {
            for (int i = 0; i < newHeight; i++) {
                System.arraycopy(data[s][i], 0, newData[s][i], 0, width);
            }
        }

        int newS = slices;
        for (int s = slices - 1; s >= 0; s--) {
            for (int i = newHeight; i < height; i++) {
                System.arraycopy(data[s][i], 0, newData[newS][i - newHeight], 0, width);
            }
            ++newS;
        }

        return new FoldingANote(newSlices, newHeight, width, newData);
    }


    public String answer() {
        System.out.println(this);
        return "A";
    }

    @Override
    public String toString() {
        return "FoldingANote{" +
                "slices=" + slices +
                ", height=" + height +
                ", width=" + width +
                ", data=" + dataString() +
                '}';
    }

    private String dataString() {
        final StringBuilder sb = new StringBuilder();

        for (int s = 0; s < slices; s++) {
            sb.append("slice ");
            sb.append(s);
            sb.append(" [");
            sb.append('\n');
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    sb.append(data[s][i][j]);
                }
                sb.append('\n');
            }
            sb.append("]");
            sb.append('\n');
        }

        return sb.toString();
    }
}
