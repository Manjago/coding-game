import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

// https://www.codingame.com/training/hard/map-colorations
public class MapColorations {

    private final Map<MemoKey, Long> memo = new HashMap<>();
    private final Map<String, Long> graphMemo = new HashMap<>();
    private final int colorsAvailable;
    private long debugCounter = 0;

    public MapColorations(int colorsAvailable) {
        this.colorsAvailable = colorsAvailable;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            final int edges = Integer.parseInt(reader.readLine());

            final Renumer renumer = new Renumer();
            for (int i = 0; i < edges; i++) {
                final StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
                final String v0 = stringTokenizer.nextToken();
                final String v1 = stringTokenizer.nextToken();
                renumer.add(v0, v1);
            }

            final FastGraph initialGraph = new FastGraph(renumer.vertexCount());
            renumer.fill(initialGraph);

            final int colorSetCount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < colorSetCount; i++) {
                final int colorsAvailable = Integer.parseInt(reader.readLine());
                System.out.println(new MapColorations(colorsAvailable).solve(initialGraph, 0));
            }

        }

    }

    private long solve(final FastGraph graph, long parent) {

        final long me = ++debugCounter;
        //System.out.println("me " + me + "!!!");
        final String s = "!" + parent + "->" + me + ":";
        //System.out.println(s + "wanna solve " + graph);

        final String fingerprint = graph.fingerprint();
        if (graphMemo.containsKey(fingerprint)) {
            return graphMemo.get(fingerprint);
        }

        // пока есть несмежные вершины
        int[] vertexes = graph.twoNonAdj();
        //System.out.println(s + "got nonadj " + Arrays.toString(vertexes));

        long result;

        if (vertexes == null) {
            if (graph.vertexCount() == 0) {
                result = 0;
                //System.out.println(s + " no vertex result " + result);
            } else if (graph.vertexCount() == 1) {
                result = colorsAvailable;
                //System.out.println(s + " 1 vertex result allcolors " + result);
            } else {
                result = fastSolve(graph.vertexCount(), colorsAvailable);
                //System.out.println(s + " many vertex result combinatoric " + result);
            }
        } else {
            FastGraph graph1 = graph.xcopy().deleteEdgeAndMerge(vertexes[0], vertexes[1]);
            //System.out.println(s + "from " + graph + " merge " + vertexes[0] + "," + vertexes[1] + " and get " + graph1);

            FastGraph graph2 = graph.xcopy().addBiEdge(vertexes[0], vertexes[1]);
            //System.out.println(s + "to " + graph + " add " + vertexes[0] + "," + vertexes[1] + " and get " + graph2);

            result = solve(graph1, me) + solve(graph2, me);
            //System.out.println(s + " recusrsive result " + result);
        }
        graphMemo.put(fingerprint, result);
        return result;
    }

    private long fastSolve(int vertexCount, int colorCount) {
        if (colorCount < vertexCount) {
            return 0;
        }

        final MemoKey memoKey = new MemoKey(vertexCount, colorCount);
        if (memo.containsKey(memoKey)) {
            return memo.get(memoKey);
        }
        long result = 1;
        int mult = colorCount;
        for (int i = 0; i < vertexCount; i++) {
            result *= mult;
            mult -= 1;
        }
        memo.put(memoKey, result);
        return result;
    }

    static class MemoKey implements Comparable<MemoKey> {
        final int vertexCount;
        final int colorCount;

        MemoKey(int vertexCount, int colorCount) {
            this.vertexCount = vertexCount;
            this.colorCount = colorCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MemoKey memoKey = (MemoKey) o;
            return vertexCount == memoKey.vertexCount && colorCount == memoKey.colorCount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertexCount, colorCount);
        }

        @Override
        public String toString() {
            return "MemoKey{" +
                    "vertexCount=" + vertexCount +
                    ", colorCount=" + colorCount +
                    '}';
        }

        @Override
        public int compareTo(MemoKey o) {
            int compare = Integer.compare(vertexCount, o.vertexCount);
            if (compare != 0) {
                return compare;
            }
            return Integer.compare(colorCount, o.colorCount);
        }
    }

    static class FastGraph {
        private final int maxVertexCount;
        private final int[][] data;

        public FastGraph(int maxVertexCount, int[][] data) {
            this.maxVertexCount = maxVertexCount;
            this.data = data;
        }

        public FastGraph(int maxVertexCount) {
            this(maxVertexCount, new int[maxVertexCount + 1][maxVertexCount + 1]);
        }

        int vertexCount() {
            return data[0][0];
        }

        void addEdge(int vertex0, int vertex1) {
            int prev = data[vertex0][vertex1];

            if (prev == 0) {
                data[vertex0][vertex1] = 1;
                if (data[vertex0][0]++ == 0) {
                    ++data[0][0];
                }
            }
        }

        FastGraph addBiEdge(int v0, int v1) {
            addEdge(v0, v1);
            addEdge(v1, v0);
            return this;
        }

        FastGraph xcopy() {
            int[][] cp = new int[maxVertexCount + 1][maxVertexCount + 1];
            for (int i = 0; i <= maxVertexCount; i++) {
                System.arraycopy(data[i], 0, cp[i], 0, maxVertexCount + 1);
            }
            return new FastGraph(maxVertexCount, cp);
        }

        FastGraph deleteEdgeAndMerge(int vertex0, int vertex1) {
            --data[0][0];
            data[vertex1][0] = 0;

            int newSum = 0;
            for (int j = 1; j <= maxVertexCount; j++) {
                data[vertex0][j] = data[vertex1][j] | data[vertex0][j];
                newSum += data[vertex0][j];
            }
            data[vertex0][0] = newSum;

            for (int i = 1; i <= maxVertexCount; i++) {
                if (data[i][0] == 0 || data[i][vertex1] == 0) {
                    continue;
                }

                if (data[i][vertex1] != 0) {
                    data[i][vertex1] = 0;
                    --data[i][0];
                }
                if (data[i][vertex0] == 0) {
                    ++data[i][0];
                    data[i][vertex0] = 1;
                }

            }

            return this;
        }

        int[] twoNonAdj() {
            int vertexCount = data[0][0];
            for (int i = 1; i <= maxVertexCount; i++) {
                if (data[i][0] == vertexCount - 1 || data[i][0] == 0) {
                    continue;
                }

                for (int j = 1; j <= maxVertexCount; j++) {
                    if (i != j && data[i][j] == 0 && data[j][0] != 0) {
                        return new int[]{i, j};
                    }
                }
                throw new IllegalStateException("bad logic nonAdj " + this);
            }
            return null;
        }

        String fingerprint() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= maxVertexCount; i++) {
                if (data[i][0] == 0) {
                    continue;
                }
                sb.append(i);
                sb.append(':');
                for (int j = 1; j <= maxVertexCount; j++) {
                    if (data[i][j] != 0) {
                        sb.append(j);
                        sb.append(',');
                    }
                }
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(data[0][0]);
            int realVertex = 0;
            for (int i = 1; i <= maxVertexCount; i++) {
                if (data[i][0] == 0) {
                    continue;
                }
                ++realVertex;
                sb.append('[');
                sb.append(i);
                sb.append(':');
                for (int j = 1; j <= maxVertexCount; j++) {
                    if (data[i][j] != 0) {
                        sb.append(j);
                        sb.append(',');
                    }
                }
                sb.append("(");
                sb.append(data[i][0]);
                sb.append(")");
                sb.append(']');
            }
            if (realVertex != data[0][0]) {
                throw new IllegalStateException("bad graph " + sb);
            }
            return sb.toString();
        }
    }


    static class Renumer {

        private final Map<String, Integer> nums = new HashMap<>();
        private final List<Integer> left = new ArrayList<>();
        private final List<Integer> right = new ArrayList<>();
        private int counter = 0;


        int vertexCount() {
            return counter;
        }

        void add(String v0, String v1) {
            left.add(setnum(v0));
            right.add(setnum(v1));
        }

        void fill(FastGraph fastGraph) {
            for (int i = 0; i < left.size(); i++) {
                fastGraph.addBiEdge(left.get(i), right.get(i));
            }
        }

        int setnum(String v) {
            if (nums.containsKey(v)) {
                return nums.get(v);
            }
            int result = ++counter;
            nums.put(v, result);
            return result;
        }
    }
}


