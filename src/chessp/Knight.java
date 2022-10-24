package chessp;

import board.Board;

public class Knight extends ChessP{

    public Knight(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() {
        return this.isWhite ? "wN" : "bN";
    }


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

        // check if position is use and is of same color
        if (Board.chessBoard[row][col] != null && Board.chessBoard[row][col].isWhite == this.isWhite)  {
                return false;
        }

        // check if your king is in check and see if moving would block the check, return false if it doesn't block the check (reuse for all non-king pieces)
        if (Board.is_inCheck(this.isWhite)) {
            throw new RuntimeException();
        }

        // check if the move will put king in check
        // if (willPutinCheck) {
        // return false;
        // }

        int possibleXMoves[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int possibleYMoves[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for (int i = 0; i < 8; i++) {
            // check if position of knight after moving is possible
            if  ((row == this.row + possibleXMoves[i]) && (col == this.col + possibleYMoves[i])) {
                // no need to check if piece at destination is of opposite color (already did it above) or if there is an obstacle in the way since knight jump over other pieces
                return true;
            }
        }

        return false;
    }
}
