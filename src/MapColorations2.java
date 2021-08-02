import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

// https://www.codingame.com/training/hard/map-colorations
/*
4
A B
A D
C B
C D
1
3

 */
public class MapColorations2 {

    public static final int NO_COLOR = -1;
    private final Graph graph;
    private final int colorsAvailable;
    private final Map<String, Integer> vertexToColor = new HashMap<>();
    int answer = 0;

    public MapColorations2(Graph graph, int colorsAvailable) {
        this.graph = graph;
        this.colorsAvailable = colorsAvailable;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            final int edges = Integer.parseInt(reader.readLine());
            final Graph graph = new Graph();

            for (int i = 0; i < edges; i++) {
                final StringTokenizer stringTokenizer = new StringTokenizer(reader.readLine());
                final String v0 = stringTokenizer.nextToken();
                final String v1 = stringTokenizer.nextToken();
                graph.addEdge(v0, v1);
                graph.addEdge(v1, v0);
            }

            final int colorSetCount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < colorSetCount; i++) {
                final int colorsAvailable = Integer.parseInt(reader.readLine());
                System.out.println(solve(graph, colorsAvailable));
            }

        }
    }

    private static int solve(Graph graph, int colorsAvailable) {

        // https://ru.wikipedia.org/wiki/%D0%A5%D1%80%D0%BE%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B9_%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D1%87%D0%BB%D0%B5%D0%BD

        System.out.println(graph);

        // пока есть несмежные вершины
        String[] vertexes = graph.twoNonAdj();
        if (vertexes == null) {
            if (graph.vertexCount() == 0) {
                return 0;
            } else if (graph.vertexCount() == 1) {
                return colorsAvailable;
            } else {
                return 0;
            }
        } else {
            return solve(graph.deleteEdgeAndMerge(vertexes[0], vertexes[1]), colorsAvailable) +
                    solve(graph.xcopy().addEdge(vertexes[0], vertexes[1]).addEdge(vertexes[1], vertexes[0]), colorsAvailable);

        }

    }

    static class Graph {

        private final Map<String, Set<String>> data;

        public Graph() {
            this(new HashMap<>());
        }

        public Graph(Map<String, Set<String>> data) {
            this.data = data;
        }

        String getAnyVertex() {
            return data.keySet().stream().findAny().get();
        }

        Graph xcopy() {
            return new Graph(data);
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
                    return null;
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

        Iterable<String> edges(String vertex) {
            if (data.containsKey(vertex)) {
                return data.get(vertex);
            }
            return new HashSet<>();
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


