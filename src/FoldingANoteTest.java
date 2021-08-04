public class FoldingANoteTest {

    public static void main(String[] args) {
        newTestBig();
    }

    private static void newTest() {
        FoldingANote solver = new FoldingANote(2);
        solver.init(0, "OA");
        solver.init(1, "LM");
        System.out.println(solver);

        FoldingANote after1 = solver.smartfold1();
        System.out.println(after1);
    }

    private static void newTestBig() {
        FoldingANote solver = new FoldingANote(4);
        solver.init(0, "1234");
        solver.init(1, "5678");
        solver.init(2, "9ABC");
        solver.init(3, "DEFG");
        System.out.println(solver);

        FoldingANote after1 = solver.smartfold1();
        System.out.println(after1);
        FoldingANote after2 = after1.smartfold2();
        System.out.println(after2);
    }

    private static void oldTest() {
        FoldingANote solver = new FoldingANote(2);
        solver.init(0, "OA");
        solver.init(1, "LM");
        System.out.println(solver);

        FoldingANote after1 = solver.fold1();
        System.out.println(after1);

        FoldingANote after2 = after1.fold2();
        System.out.println(after2);
    }

}
