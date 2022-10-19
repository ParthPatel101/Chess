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
        return false;
    }
}


