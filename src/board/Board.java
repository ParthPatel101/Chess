package board;

import chessp.ChessP;
import chessp.Queen;

public abstract class Board {
    public static ChessP[][] chessBoard;
    public static ChessP[] whitePieces;
    public static ChessP[] blackPieces;
    public static ChessP whiteKing;
    public static ChessP blackKing;

    public static void start_game() {
        initialize_board();
        print_board();
    }

    public static void initialize_board() {
        chessBoard = new ChessP[8][8];
        // initialize the Whites and Blacks arrays with their respective Chess Piece Objects and stored pieces based on game order
        blackPieces = new ChessP[16];
        // blackPieces[0] = new Rook(false, 7, 0);
        // blackPieces[1] = new Knight(false, 7, 1);
        // blackPieces[2] = new Bishop(false, 7, 2);
        // blackPieces[3] = new Queen(false, 7, 3);
        // blackPieces[4] = new King(false, 7, 4);
        // blackKing = blackPieces[4];
        // blackPieces[5] = new Bishop(false, 7, 5);
        // blackPieces[6] = new Knight(false, 7, 6);
        // blackPieces[7] = new Rook(false, 7, 7);
        // for (int i = 0; i < 8; i++) {
        //     blackPieces[i+8] = new Pawn(false, 6, i);
        // }

        // whitePieces = new ChessP[16]; // order this based on the game order [rook, knight, bishop, queen, king ..., pawn, pawn, pawn ...]
        // whitePieces[0] = new Rook(true, 0, 0);
        // whitePieces[1] = new Knight(true, 0, 1);
        // whitePieces[2] = new Bishop(true, 0, 2);
        // whitePieces[3] = new Queen(true, 0, 3);
        // whitePieces[4] = new King(true, 0, 4);
        // whiteKing = whitePieces[4];
        // whitePieces[5] = new Bishop(true, 0, 5);
        // whitePieces[6] = new Knight(true, 0, 6);
        // whitePieces[7] = new Rook(true, 0, 7);
        // for (int i = 0; i < 8; i++) {
        //     whitePieces[i+8] = new Pawn(true, 1, i);
        // }
        // store reference to the white and black kings in Board.WhiteKing and Board.BlackKing
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if (row == 7) {
                    chessBoard[row][col] = blackPieces[col];
                } else if (row == 6) {
                    chessBoard[row][col] = blackPieces[col + 8];
                } else if (row == 1) {
                    chessBoard[row][col] = whitePieces[col];
                } else if (row == 0) {
                    chessBoard[row][col] = whitePieces[col + 8];
                } else {
                    chessBoard[row][col] = null;
                }
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

    public static void print_board() {
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if (Board.chessBoard[row][col] == null) {
                    if ((row + col) % 2 == 0) {
                        System.out.print("## ");
                    } else {
                        // this is just for looking at index while we develop the moves will remove after
                        System.out.print(row + "" + col + " ");
                    }
                } else {
                    System.out.print(Board.chessBoard[row][col].printName() + " ");
                }
                if (col == 7) {
                    System.out.print((row + 1) + "\n");
                }
            }
        }
        System.out.println(" a  b  c  d  e  f  g  h");
    }
}


