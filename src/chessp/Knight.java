package chessp;

public class Knight extends ChessP {
    private final int[] possibleXMoves = { 2, 1, -1, -2, -2, -1, 1, 2 };
    private final int[] possibleYMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };

    public Knight(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String getName() {
        return this.isWhite ? "wN" : "bN";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        for (int i = 0; i < 8; i++) {
            // check if position of knight after moving is possible
            if ((row == this.row + possibleXMoves[i]) && (col == this.col + possibleYMoves[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        return kingInCheckByPossibleMoves(OpponentKing, this.possibleXMoves, this.possibleYMoves);
    }
}


