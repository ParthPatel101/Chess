package board;

import chessp.ChessP;

public abstract class Board {
    public static ChessP[][] board;
    public static ChessP[] Whites;
    public static ChessP[] Blacks;
    public static ChessP WhiteKing;
    public static ChessP BlackKing;

    public static void initialize_board() {
        Board.board = new ChessP[8][8];
        Board.Whites = new ChessP[16];
        Board.Blacks = new ChessP[16];
        // initialize the Whites and Blacks arrays with their respective Chess Piece Objects
        // store reference to the white and black kings in Board.WhiteKing and Board.BlackKing
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                Board.board[row][col] = null;
                // initialize the board with white and black ChessP objects in correct locations
                // top 2 rows is Blacks
                // bottom 2 rows is Whites
            }
        }
        return;
    }

    public static boolean is_inCheck(boolean isWhite) {
        if (isWhite) {
            // check if white king is in check
            // return true or false
            return false;
        }
        // check if black king is in check
        // return true or false
        return false;
    }
}


