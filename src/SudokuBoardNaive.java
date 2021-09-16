/*
https://leetcode.com/problems/valid-sudoku/
Runtime: 2 ms, faster than 76.95% of Java online submissions for Valid Sudoku.
Memory Usage: 42.6 MB, less than 19.94% of Java online submissions for Valid Sudoku.
 */
class SudokuBoardNaive {

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

        int checker;
        int prevChecker;

        for (int i = 0; i < N; i++) {
            checker = 0;
            for (int j = 0; j < N; j++) {
                int index = board[i][j] - RECODER;
                if (index < 0) {
                    continue;
                }
                int digit = DICT[index];

                prevChecker = checker;
                checker |= digit;
                if (prevChecker == checker) {
                    return false;
                }
            }
        }

        for (int j = 0; j < N; j++) {
            checker = 0;
            for (int i = 0; i < N; i++) {
                int index = board[i][j] - RECODER;
                if (index < 0) {
                    continue;
                }
                int digit = DICT[index];
                prevChecker = checker;
                checker |= digit;
                if (prevChecker == checker) {
                    return false;
                }
            }
        }

        for (int superI = 0; superI < N; superI = superI + CELL_LEN) {
            for (int superJ = 0; superJ < N; superJ = superJ + CELL_LEN) {

                checker = 0;
                for (int i = superI; i < superI + CELL_LEN; i++) {
                    for (int j = superJ; j < superJ + CELL_LEN; j++) {
                        int index = board[i][j] - RECODER;
                        if (index < 0) {
                            continue;
                        }
                        int digit = DICT[index];
                        prevChecker = checker;
                        checker |= digit;
                        if (prevChecker == checker) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}