/*
https://leetcode.com/problems/valid-sudoku/
Runtime: 3 ms, faster than 49.87% of Java online submissions for Valid Sudoku.
Memory Usage: 42.8 MB, less than 18.92% of Java online submissions for Valid Sudoku.
 */
class SudokuBoardOnePass {

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
        int prevChecker;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                int index = board[i][j] - RECODER;
                if (index < 0) {
                    continue;
                }
                int digit = DICT[index];

                prevChecker = checkerI[i];
                checkerI[i] = checkerI[i] | digit;
                if (prevChecker == checkerI[i]) {
                    return false;
                }

                prevChecker = checkerJ[j];
                checkerJ[j] = checkerJ[j] | digit;
                if (prevChecker == checkerJ[j]) {
                    return false;
                }

                int zonedI = i / CELL_LEN;
                int zonedJ = j / CELL_LEN;

                prevChecker = checkerZ[zonedI][zonedJ];
                checkerZ[zonedI][zonedJ] = checkerZ[zonedI][zonedJ] | digit;
                if (prevChecker == checkerZ[zonedI][zonedJ]) {
                    return false;
                }
            }
        }

        return true;
    }
}