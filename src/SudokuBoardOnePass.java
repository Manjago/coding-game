class SudokuBoardOnePass {

    public static void main(String[] args) {

        char[][] board = new char[][] {
                {'5','3','.','.','7','.','.','.','.'},
                {'6','.','.','1','9','5','.','.','.'},
                {'.','9','8','.','.','.','.','6','.'},
                {'8','.','.','.','6','.','.','.','3'},
                {'4','.','.','8','.','3','.','.','1'},
                {'7','.','.','.','2','.','.','.','6'},
                {'.','6','.','.','.','.','2','8','.'},
                {'.','.','.','4','1','9','.','.','5'},
                {'.','.','.','.','8','.','.','7','9'}
        };

        boolean res = new SudokuBoardOnePass().isValidSudoku(board);

        System.out.println(res);
    }
    private static final int[] DICT = {
            1, // '0'
            2, // '1'
            4, // '2'
            8, // '4'
            16, // '5'
            32, // '6'
            64, // '7'
            128, // '8'
            256, // '9'
    };

    private static final int N = 9;
    private static final int RECODER = 48 + 1;
    private static final int CELL_LEN = 3;

    public boolean isValidSudoku(char[][] board) {

        int[] checkerI = new int[N];
        int[] checkerJ = new int[N];
        int[][] checkerZ = new int[CELL_LEN][CELL_LEN];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {



            }
        }

        return true;
    }
}