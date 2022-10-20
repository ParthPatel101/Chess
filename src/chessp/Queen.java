package chessp;

import chessp.ChessP;

public class Queen extends ChessP {
    public Queen(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    /**
     * @param col - column index
     * @param row - row index
     * @return boolean - whether the piece is allowed to move to the destination from its current location
     */
    public boolean is_legalMove(int col, int row) {
        // check if your king is in check and see if moving would block the check, return false if it doesn't block the check (reuse for all non-king pieces)

        // check if the move is in the horizontal or vertical plane of the queen (reuse for rook)

        // check if the move is in the diagonal plane of the queen (reuse for bishop)

        // if passes all these conditions return true
        return false;
    }
}


