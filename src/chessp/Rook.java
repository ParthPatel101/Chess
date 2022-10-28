package chessp;

/**
 * rook chess piece
 */
public class Rook extends ChessP {

    /**
     * whether this rook has not moved
     */
    public boolean hasNotMoved = true;

    /**
     * @param isWhite which team is this king on?
     * @param row current row index on the board
     * @param col current column index on the board
     * initializes the rook information
     */
    public Rook(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return this.isWhite ? "wR" : "bR";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if the move is in the horizontal plane
        if (row == this.row) {
            return canMoveHorizontally(col, row);
        }
        // check if the move is in the vertical plane
        if (col == this.col) {
            return canMoveVertically(col, row);
        }
        // if none of these moves work then return false
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        return (kingInCheckHorizontally(OpponentKing) || kingInCheckVertically(OpponentKing));
    }
}
