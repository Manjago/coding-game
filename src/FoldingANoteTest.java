public class FoldingANoteTest {

    public static void main(String[] args) {
        validator1();
        validator2();
    }

    private static void ltest(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new IllegalStateException("expected " + expected +", actual" + actual);
        }
    }

    private static void validator1() {
        FoldingANote solver = new FoldingANote(2);
        solver.init(0, "OA");
        solver.init(1, "LM");
        ltest("LMAO", FoldingANote.answer(solver));
    }

    private static void validator2() {
        FoldingANote solver = new FoldingANote(4);
        solver.init(0, "uDuu");
        solver.init(1, "u!eu");
        solver.init(2, "uudu");
        solver.init(3, "uuuu");
        ltest("Duuuuuuuuuuuude!", FoldingANote.answer(solver));
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
        FoldingANote after3 = after2.smartfold3();
        System.out.println(after3);
        FoldingANote after4 = after3.smartfold4();
        System.out.println(after4);
        System.out.println(after4.answer());

    }

}
