package chessp;

public class Bishop extends ChessP {

    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return this.isWhite ? "wB" : "bB";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if the move is in the diagonal plane of the queen (reuse for bishop)
        if (Math.abs(row - this.row) == Math.abs(col - this.col)) {
            return canMoveDiagonally(col, row);
        }

        // if none of these moves work then return false
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        return kingInCheckDiagonally(OpponentKing);
    }
}
