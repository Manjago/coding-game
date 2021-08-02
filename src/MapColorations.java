import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;

// https://www.codingame.com/training/hard/map-colorations
public class MapColorations {

    private final Map<MemoKey, Long> memo = new HashMap<>();
    private final int colorsAvailable;

    public MapColorations(int colorsAvailable) {
        this.colorsAvailable = colorsAvailable;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            final int edges = Integer.parseInt(reader.readLine());
            final Graph initialGraph = new Graph();

            for (int i = 0; i < edges; i++) {
                final StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
                final String v0 = stringTokenizer.nextToken();
                final String v1 = stringTokenizer.nextToken();
                initialGraph.addEdge(v0, v1);
                initialGraph.addEdge(v1, v0);
            }

            final int colorSetCount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < colorSetCount; i++) {
                final int colorsAvailable = Integer.parseInt(reader.readLine());
                System.out.println(new MapColorations(colorsAvailable).solve(initialGraph));
            }

        }

    }

    private long solve(final Graph graph) {

        //System.out.println(graph);

        // пока есть несмежные вершины
        String[] vertexes = graph.twoNonAdj();
        //System.out.println("found " + Arrays.toString(vertexes));
        if (vertexes == null) {
            if (graph.vertexCount() == 0) {
                return 0;
            } else if (graph.vertexCount() == 1) {
                return colorsAvailable;
            } else {
                //         System.out.print("old graph " + graph + " colorsMax " + colorsAvailable);
                //         int solve = new MapColorations3(graph, colorsAvailable).solve();
                long solve = fastSolve(graph.vertexCount(), colorsAvailable);
                //         System.out.println(": " + solve + " " + fastSolve(graph.vertexCount(), colorsAvailable));
                return solve;
            }
        } else {
            return solve(graph.deleteEdgeAndMerge(vertexes[0], vertexes[1])) +
                    solve(graph.xcopy().addEdge(vertexes[0], vertexes[1]).addEdge(vertexes[1], vertexes[0]));

        }

    }

    private long fastSolve(int vertexCount, int colorCount) {
        if (colorCount < vertexCount) {
            return 0;
        }

        final MemoKey memoKey = new MemoKey(vertexCount, colorCount);
        if (memo.containsKey(memoKey)) {
            //  System.out.println("hit " + memoKey);
            return memo.get(memoKey);
        } else {
            // System.out.println("calc " + memoKey);
        }
        long result = 1;
        int mult = colorCount;
        for (int i = 0; i < vertexCount; i++) {
            result = result * mult;
            mult = mult - 1;
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

    static class Graph {

        private final Map<String, Set<String>> data = new HashMap<>();

        Graph xcopy() {
            final Graph result = new Graph();

            for (Map.Entry<String, Set<String>> entry : data.entrySet()) {
                for (String otherVertex : entry.getValue()) {
                    result.addEdge(entry.getKey(), otherVertex);
                }
            }

            return result;
        }

        Graph deleteEdgeAndMerge(String vertex0, String vertex1) {
            final Graph result = new Graph();
            for (Map.Entry<String, Set<String>> entry : data.entrySet()) {


                for (String otherVertex : entry.getValue()) {

                    final String root;
                    if (entry.getKey().equals(vertex1)) {
                        root = vertex0;
                    } else {
                        root = entry.getKey();
                    }


                    if (otherVertex.equals(vertex1)) {
                        result.addEdge(root, vertex0);
                    } else {
                        result.addEdge(root, otherVertex);
                    }
                }
            }
            return result;
        }


        String[] twoNonAdj() {
            for (Map.Entry<String, Set<String>> entry : data.entrySet()) {
                if (entry.getValue().size() == data.size() - 1) {
                    continue;
                }

                final String pretender = entry.getKey();
                for (String pretender2 : data.keySet()) {
                    if (!pretender2.equals(pretender) && !entry.getValue().contains(pretender2)) {
                        return new String[]{pretender, pretender2};
                    }
                }
                throw new IllegalStateException("bad logic nonAdj");
            }
            return null;
        }

        Graph addEdge(String vertex0, String vertex1) {
            final Set<String> edges;
            if (!data.containsKey(vertex0)) {
                edges = new HashSet<>();
                data.put(vertex0, edges);
            } else {
                edges = data.get(vertex0);
            }
            edges.add(vertex1);
            return this;
        }

        int vertexCount() {
            return data.size();
        }

        @Override
        public String toString() {
            return "Graph{" +
                    "data=" + data +
                    '}';
        }

    }
}


