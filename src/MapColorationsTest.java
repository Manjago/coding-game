import java.util.Arrays;

public class MapColorationsTest {
    public static void main(String[] args) {
        allTests();
    }

    private static void allTests() {
        test1();
    }

    private static void test2() {
           final MapColorations.FastGraph g = new MapColorations.FastGraph(3);
    }

    private static void test1() {
        /*
        A-B
        | |
        D-C
         */
        MapColorations.FastGraph fastGraph = new MapColorations.FastGraph(4);
        ltest(0, fastGraph.vertexCount());
        fastGraph.addBiEdge(1, 2);
        ltest(2, fastGraph.vertexCount());
        fastGraph.addBiEdge(2, 3);
        ltest(3, fastGraph.vertexCount());
        fastGraph.addBiEdge(3, 4);
        ltest(4, fastGraph.vertexCount());
        fastGraph.addBiEdge(4, 1);
        System.out.println(fastGraph.fingerprint());
        System.out.println(fastGraph);
        System.out.println(Arrays.toString(fastGraph.twoNonAdj()));
        ltest(4, fastGraph.vertexCount());

        MapColorations.FastGraph fastGraphClone = fastGraph.xcopy();
        ltest(4, fastGraphClone.vertexCount());
        System.out.println(fastGraphClone);

        fastGraph.deleteEdgeAndMerge(1, 3);
        ltest(3, fastGraph.vertexCount());
        System.out.println(fastGraph);

        System.out.println(Arrays.toString(fastGraph.twoNonAdj()));
        fastGraph.deleteEdgeAndMerge(2, 4);
        ltest(2, fastGraph.vertexCount());
        System.out.println(fastGraph);
        System.out.println(Arrays.toString(fastGraph.twoNonAdj()));

        final MapColorations.Renumer renumer = new MapColorations.Renumer();
        renumer.add("A", "B");
        renumer.add("C", "D");
        renumer.add("A", "D");
        renumer.add("C", "B");
        MapColorations.FastGraph testGraph = new MapColorations.FastGraph(renumer.vertexCount());
        renumer.fill(testGraph);
        System.out.println(testGraph);
    }

    private static void ltest(int expected, int actual) {
        if (expected != actual) {
            throw new IllegalStateException("expected " + expected + ", actual " + actual);
        }
    }
}
