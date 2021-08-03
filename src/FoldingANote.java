import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FoldingANote {

    private final int slices;
    private final int height;
    private final int width;
    private final char[][][] data;

    public FoldingANote(int height, int width) {
        this.slices = 1;
        this.height = height;
        this.width = width;
        data = new char[slices][height][width];
    }

    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            final int lines = Integer.parseInt(reader.readLine());
            final FoldingANote solver = new FoldingANote(lines, lines);

            for (int i = 0; i < lines; i++) {
                solver.init(i, reader.readLine());
            }

            System.out.println(solver.answer());
        }

    }

    private void init(int num, String strdata) {
        char[] chars = strdata.toCharArray();
        if (width >= 0) System.arraycopy(chars, 0, data[0][num], 0, width);
    }

    private String answer() {
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
