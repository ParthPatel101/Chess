package chessp;

/**
 * knight chess piece
 *
 * @author Parth Patel, Yash Patel
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
     * @param isWhite which team is this knight on?
     * @param row current row index on the board
     * @param col current column index on the board
     * initializes the knight information
     */
    public Knight(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    /**
     * @return name of knight based on color
     */
    @Override
    public String getName() {
        return this.isWhite ? "wN" : "bN";
    }

    /**
     * @param col destination column index on the board
     * @param row destination row index on the board
     * @return if the destination is possible based on the knight's path
     */
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

    /**
     * @param OpponentKing reference to the opponent king
     * @return if the piece is checking the opponent king
     */
    @Override
    public boolean isCheckingKing(King OpponentKing) {
        return kingInCheckByPossibleMoves(OpponentKing, possibleXMoves, possibleYMoves);
    }
}


