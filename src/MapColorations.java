import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

// https://www.codingame.com/training/hard/map-colorations
public class MapColorations {

    public static final int NO_COLOR = -1;
    private final Graph graph;
    private final int colorsAvailable;
    private final Map<String, Integer> vertexToColor = new HashMap<>();
    int answer = 0;

    public MapColorations(Graph graph, int colorsAvailable) {
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
                System.out.println(new MapColorations(graph, colorsAvailable).solve());
            }

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
        while(true) {
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

    enum ColorState {
        BAD, GOOD_INCOMPLETE, GOOD_COMPLETE;
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

    static class Graph {

        private final Map<String, Set<String>> data = new HashMap<>();

        String getAnyVertex() {
            return data.keySet().stream().findAny().get();
        }

        Iterable<String> edges(String vertex) {
            if (data.containsKey(vertex)) {
                return data.get(vertex);
            }
            return new HashSet<>();
        }

        void addEdge(String vertex0, String vertex1) {
            final Set<String> edges;
            if (!data.containsKey(vertex0)) {
                edges = new HashSet<>();
                data.put(vertex0, edges);
            } else {
                edges = data.get(vertex0);
            }
            edges.add(vertex1);
        }

        int vertexCount() {
            return data.size();
        }
    }
}


