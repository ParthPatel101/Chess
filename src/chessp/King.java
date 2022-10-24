package chessp;

import board.Board;

public class King extends ChessP {
    public int castling = 0; // 0 means not yet castled, 1 means they have castled, -1 means they are not allowed to castle
    public boolean inCheck = false;
    public King(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() { return this.isWhite ? "wK" : "bK"; }

    @Override
    public boolean is_legalMove(int col, int row) {
        // check if location is on the board
        if (row > 7 || col > 7) {
            return false;
        }

        // check if location is current location (meaning no movement)
        if (col == this.col && row == this.row) {
            return false;
        }

        // check if king is in check and see if moving would block the check, return false if it doesn't block the check
        if (this.inCheck) {
            throw new RuntimeException();
        }

        // check if the move will put king in check
        // if (willPutinCheck) {
        // return false;
        // }

        // check if king has not castled yet
        if (castling == 0) {
            // if the move is a castling move && it works
            // castling = 1
            // return true;

            // else if the move is a not castling move && it works
            // castling = -1
            // return true;
        }
        else {
            // check if position is use and is of same color
            if (Board.chessBoard[row][col] != null && Board.chessBoard[row][col].isWhite == this.isWhite) {
                return false;
            }
        }


        return false;
    }
}
