package chessp;

import board.Board;

public class King extends ChessP {
    public int castling = 0; // 0 means not yet castled, 1 means they have castled, -1 means they are not allowed to castle

    public King(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String getName() { return this.isWhite ? "wK" : "bK"; }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if king has not castled yet
        if (castling == 0) {
            // if the move is a castling move && it works
            // castling = 1
            // return true;

            // else if the move is a not castling move && it works
            // castling = -1
            // return true;
        }
        return false;
    }
}


