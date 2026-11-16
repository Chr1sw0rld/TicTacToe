
import java.util.Scanner;

public class TicTacToe {

    // Board constants and shared game board
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String[][] board = new String[ROWS][COLS];

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        boolean playAgain;

        // Start a new game and repeat if requested.
        do {
            clearBoard();
            String player = "X";
            int moveCount = 0;
            boolean gameOver = false;

            while (!gameOver) {
                display();
                System.out.println("Player " + player
                        + ", choose your move.");

                int row;
                int col;

                // Get coordinates and reject occupied cells.
                do {
                    row = SafeInput.getRangedInt(
                            console, "Row", 1, ROWS) - 1;
                    col = SafeInput.getRangedInt(
                            console, "Column", 1, COLS) - 1;

                    if (!isValidMove(row, col)) {
                        System.out.println(
                                "That space is occupied. Please choose another move.");
                    }
                } while (!isValidMove(row, col));

                // Record the move.
                board[row][col] = player;
                moveCount++;

                // Check for a win or tie after enough moves.
                if (moveCount >= 5 && isWin(player)) {
                    display();
                    System.out.println("Player " + player + " wins!");
                    gameOver = true;
                } else if (moveCount >= 5 && isTie()) {
                    display();
                    System.out.println("It's a tie!");
                    gameOver = true;
                } else {
                    // Switch between X and O.
                    player = player.equals("X") ? "O" : "X";
                }
            }

            playAgain = SafeInput.getYNConfirm(
                    console, "Play again?");
        } while (playAgain);

        System.out.println("Thanks for playing!");
        console.close();
    }

    // Clear all cells before starting a game.
    private static void clearBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = " ";
            }
        }
    }

    // Display the board with row and column numbers.
    private static void display() {
        System.out.println("\n    1   2   3");

        for (int row = 0; row < ROWS; row++) {
            System.out.print((row + 1) + "   ");

            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col]);

                if (col < COLS - 1) {
                    System.out.print(" | ");
                }
            }

            System.out.println();

            if (row < ROWS - 1) {
                System.out.println("   ---+---+---");
            }
        }

        System.out.println();
    }

    // Check whether the selected cell is available.
    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS
                && col >= 0 && col < COLS
                && board[row][col].equals(" ");
    }

    // Check all possible winning directions.
    private static boolean isWin(String player) {
        return isRowWin(player)
                || isColWin(player)
                || isDiagnalWin(player);
    }

    // Check for three matching marks in a row.
    private static boolean isRowWin(String player) {
        for (int row = 0; row < ROWS; row++) {
            boolean allMatch = true;

            for (int col = 0; col < COLS; col++) {
                if (!board[row][col].equals(player)) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                return true;
            }
        }

        return false;
    }

    // Check for three matching marks in a column.
    private static boolean isColWin(String player) {
        for (int col = 0; col < COLS; col++) {
            boolean allMatch = true;

            for (int row = 0; row < ROWS; row++) {
                if (!board[row][col].equals(player)) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                return true;
            }
        }

        return false;
    }

    // Check both diagonal winning combinations.
    private static boolean isDiagnalWin(String player) {
        boolean forward = true;
        boolean backward = true;

        for (int i = 0; i < ROWS; i++) {
            if (!board[i][i].equals(player)) {
                forward = false;
            }

            if (!board[i][COLS - 1 - i].equals(player)) {
                backward = false;
            }
        }

        return forward || backward;
    }

    // Check whether all possible winning lines are blocked.
    private static boolean isTie() {
        if (isWin("X") || isWin("O")) {
            return false;
        }

        for (int row = 0; row < ROWS; row++) {
            if (lineOpen(board[row][0],
                    board[row][1], board[row][2])) {
                return false;
            }
        }

        for (int col = 0; col < COLS; col++) {
            if (lineOpen(board[0][col],
                    board[1][col], board[2][col])) {
                return false;
            }
        }

        if (lineOpen(board[0][0],
                board[1][1], board[2][2])) {
            return false;
        }

        if (lineOpen(board[0][COLS - 1],
                board[1][1], board[2][0])) {
            return false;
        }

        return true;
    }

    // Determine whether either player can still win a line.
    private static boolean lineOpen(String a, String b, String c) {
        boolean hasX = a.equals("X")
                || b.equals("X") || c.equals("X");

        boolean hasO = a.equals("O")
                || b.equals("O") || c.equals("O");

        return !(hasX && hasO);
    }
}
