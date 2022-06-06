package Main;

import java.util.stream.IntStream;

public class Main_old {

    private static int BOARD_START_INDEX = 0;
    private static int BOARD_SIZE = 9;
    private static int NO_VALUE = 0;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 9;
    private static int SUBSECTION_SIZE = 3;


    public static void main(String[] args) {
        int[][] board = {
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 2, 0, 0, 3},
                {0, 0, 0, 0, 0, 0, 5, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 0 ,0},
                {6, 0, 1, 7, 0, 0, 0, 0, 0},
                {0, 0, 4, 1, 0 ,0, 0, 0, 0},
                {0, 5, 0, 0, 0, 0, 2, 0, 0},
                {0, 0, 0, 0, 8, 0, 0, 6, 0},
                {0, 3, 0, 9, 1, 0, 0, 0, 0}
        };

    }

    private boolean solve(int[][] board){
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++){
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++){
                if (board[row][column] == NO_VALUE){
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++){
                        board[row][column] = k;
                        if (isValid(board, row, column) && solve(board)){
                            return true;
                        }
                        board[row][column] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isValid(int[][] board, int row, int column){
        return (rowConstraint(board, row)
                && columnConstraint(board, column)
                && subsectionConstraint(board, row, column));
    }

    private boolean rowConstraint(int[][] board, int row){
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE).allMatch(column -> checkConstraint(board, row, constraint,
                column));
    }

    private boolean columnConstraint(int[][] board, int column){
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(row -> checkConstraint(board, row, constraint, column));
    }
    private boolean subsectionConstraint(int[][] board, int row, int column){
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;
        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++){
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++){
                if (!checkConstraint(board, r, constraint, c)){
                    return false;
                }
            }

        }
        return true;
    }

    boolean checkConstraint(
            int[][] board,
            int row,
            boolean[] constraint,
            int column){
        if (board[row][column] != NO_VALUE){
            if (!constraint[board[row][column] - 1]){
                constraint[board[row][column] - 1] = true;
            }
            return false;
        }
        return true;
    }
}
