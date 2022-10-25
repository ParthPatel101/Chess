package chessp;

public class Queen extends ChessP {
    public Queen(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return this.isWhite ? "wQ" : "bQ";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if the move is in the horizontal plane of queen (reuse for rook)
        if (row == this.row) {
            return canMoveHorizontally(col, row);
        }
        // check if the move is in the vertical plane of queen (reuse for rook, pawn [with modification])
        if (col == this.col) {
            return canMoveVertically(col, row);
        }
        // check if the move is in the diagonal plane of the queen (reuse for bishop)
        if (Math.abs(row - this.row) == Math.abs(col - this.col)) {
            return canMoveDiagonally(col, row);
        }
        // if none of these moves work then return false
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        /* Implement using for loops to check if opponentKing in movement plane */
        return false;
    }
}


