import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
2
1
3


 */
public class MapColorations3 {

    public static final int NO_COLOR = -1;
    private final Graph graph;
    private final int colorsAvailable;
    private final Map<String, Integer> vertexToColor = new HashMap<>();
    int answer = 0;

    public MapColorations3(Graph graph, int colorsAvailable) {
        this.graph = graph;
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
                System.out.println(solve(initialGraph, colorsAvailable));
            }

        }

    }

    private static int solve(final Graph graph, final int colorsAvailable) {

        // https://ru.wikipedia.org/wiki/%D0%A5%D1%80%D0%BE%D0%BC%D0%B0%D1%82%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B8%D0%B9_%D0%BC%D0%BD%D0%BE%D0%B3%D0%BE%D1%87%D0%BB%D0%B5%D0%BD

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
                return new MapColorations3(graph, colorsAvailable).solve();
            }
        } else {
            return solve(graph.deleteEdgeAndMerge(vertexes[0], vertexes[1]), colorsAvailable) +
                    solve(graph.xcopy().addEdge(vertexes[0], vertexes[1]).addEdge(vertexes[1], vertexes[0]), colorsAvailable);

        }

    }
    private int solve() {
        initialColor(graph.getAnyVertex(), new HashSet<>());
        if (vertexToColor.size() != graph.vertexCount()) {
            throw new IllegalStateException("it is not a simply connected graph");
        }

        List<String> num = new ArrayList<>(vertexToColor.keySet());

        int currentIndex = 0;
        String currentVertex = num.get(currentIndex);
        while (true) {
            int color = vertexToColor.get(currentVertex);
            ++color;
            if (color == colorsAvailable) {
                vertexToColor.put(currentVertex, NO_COLOR);

                if (currentIndex == 0) {
                    return answer;
                }
                --currentIndex;
                currentVertex = num.get(currentIndex);
                continue;
            }

            vertexToColor.put(currentVertex, color);
            ColorState colorState = graphIsGoodColored();
            switch (colorState) {
                case BAD:
                    break;
                case GOOD_INCOMPLETE:
                    ++currentIndex;
                    currentVertex = num.get(currentIndex);
                    break;
                case GOOD_COMPLETE:
                    ++answer;
                    break;
            }
        }
    }

    private ColorState graphIsGoodColored() {
        boolean noColorDetected = false;
        for (Map.Entry<String, Integer> entry : vertexToColor.entrySet()) {

            if (entry.getValue() == NO_COLOR) {
                noColorDetected = true;
                continue;
            }

            for (String otherVertex : graph.edges(entry.getKey())) {
                if (vertexToColor.get(otherVertex).equals(entry.getValue())) {
                    return ColorState.BAD;
                }
            }

        }
        return noColorDetected ? ColorState.GOOD_INCOMPLETE : ColorState.GOOD_COMPLETE;
    }

    private void initialColor(String vertex, Set<String> visited) {

        if (visited.contains(vertex)) {
            return;
        }
        visited.add(vertex);
        vertexToColor.put(vertex, NO_COLOR);
        Iterable<String> iterable = graph.edges(vertex);
        iterable.forEach(s -> initialColor(s, visited));
    }

    enum ColorState {
        BAD, GOOD_INCOMPLETE, GOOD_COMPLETE;
    }

    static class Graph {

        private final Map<String, Set<String>> data = new HashMap();



        String getAnyVertex() {
            return data.keySet().stream().findAny().get();
        }

        Graph xcopy() {
            final Graph result = new Graph();

            for (Map.Entry<String, Set<String>> entry : data.entrySet()) {
                for(String otherVertex: entry.getValue()) {
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


