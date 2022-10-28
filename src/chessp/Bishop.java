package chessp;

/**
 * Bishop chess piece
 *
 * @author Parth Patel, Yash Patel
 */
public class Bishop extends ChessP {

    /**
     * @param isWhite which team is this bishop on?
     * @param row current row index on the board
     * @param col current column index on the board
     * initializes the bishop information
     */
    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    /**
     * @return name of bishop based on color
     */
    @Override
    public String getName() {
        return this.isWhite ? "wB" : "bB";
    }

    /**
     * @param col destination column index on the board
     * @param row destination row index on the board
     * @return if the destination is possible based on the bishop's path
     */
    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if the move is in the diagonal plane
        if (Math.abs(row - this.row) == Math.abs(col - this.col)) {
            return canMoveDiagonally(col, row);
        }

        // if none of these moves work then return false
        return false;
    }

    /**
     * @param OpponentKing reference to the opponent king
     * @return if the piece is checking the opponent king
     */
    @Override
    public boolean isCheckingKing(King OpponentKing) {
        return kingInCheckDiagonally(OpponentKing);
    }
}
