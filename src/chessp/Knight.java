package chessp;

/**
 * knight chess piece
 */
public class Knight extends ChessP {
    /**
     * list of possible X moves that knight can take
     */
    public static final int[] possibleXMoves = {2, 1, -1, -2, -2, -1, 1, 2};
    /**
     * list of possible Y moves that knight can take
     */
    public static final int[] possibleYMoves = {1, 2, 2, 1, -1, -2, -2, -1};

    /**
     * @param isWhite which team is this king on?
     * @param row current row index on the board
     * @param col current column index on the board
     * initializes the knight information
     */
    public Knight(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

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
        return kingInCheckByPossibleMoves(OpponentKing, possibleXMoves, possibleYMoves);
    }
}


