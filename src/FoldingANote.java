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

            System.out.println(answer(solver));
        }

    }

    public static String answer(FoldingANote start) {
        FoldingANote current = start;
        int direction = 1;

        while (current.height != 1 || current.width != 1) {
            switch (direction) {
                case 1:
                    current = current.smartfold1();
                    break;
                case 2:
                    current = current.smartfold2();
                    break;
                case 3:
                    current = current.smartfold3();
                    break;
                case 4:
                    current = current.smartfold4();
                    break;
                default:
                    throw new IllegalStateException("bad direction " + direction);
            }
            if (++direction > 4) {
                direction = 1;
            }
        }

        return current.answer();
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

    public FoldingANote smartfold2() {
        final int newSlices = slices * 2;
        final int newHeight = height / 2;
        final char[][][] newData = new char[newSlices][newHeight][width];

        for (int s = 0; s < slices; s++) {
            for (int i = 0; i < newHeight; i++) {
                for (int j = 0; j < width; j++) {
                    newData[s][i][j] = data[s][i][j];
                    newData[slices + s][i][j] = data[slices - 1 - s][height - 1 - i][j];
                }
            }
        }

        return new FoldingANote(newSlices, newHeight, width, newData);
    }

    public FoldingANote smartfold3() {
        final int newSlices = slices * 2;
        final int newWidth = width / 2;
        final char[][][] newData = new char[newSlices][height][newWidth];

        for (int s = 0; s < slices; s++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < newWidth; j++) {
                    newData[s][i][j] = data[s][i][j + newWidth];
                    newData[slices + s][i][j] = data[slices - 1 - s][i][newWidth - 1 - j];
                }
            }
        }

        return new FoldingANote(newSlices, height, newWidth, newData);
    }

    public FoldingANote smartfold4() {
        final int newSlices = slices * 2;
        final int newHeight = height / 2;
        final char[][][] newData = new char[newSlices][newHeight][width];

        for (int s = 0; s < slices; s++) {
            for (int i = 0; i < newHeight; i++) {
                for (int j = 0; j < width; j++) {
                    newData[s][i][j] = data[s][i + newHeight][j];
                    newData[slices + s][i][j] = data[slices - 1 - s][newHeight - 1 - i][j];
                }
            }
        }

        return new FoldingANote(newSlices, newHeight, width, newData);
    }

    public String answer() {
        if (height != 1 || width != 1) {
            throw new IllegalStateException("" + this);
        }
        final StringBuilder sb = new StringBuilder();
        for (int s = slices - 1; s >= 0; s--) {
            sb.append(data[s][0][0]);
        }
        return sb.toString();
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
