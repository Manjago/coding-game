public class FoldingANoteTest {
    public static void main(String[] args) {
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
