package chessp;

/**
 * queen chess piece
 * @author Parth Patel, Yash Patel
 */
public class Queen extends ChessP {
    /**
     * @param isWhite which team is this queen on?
     * @param row current row index on the board
     * @param col current column index on the board
     * initializes the queen information
     */
    public Queen(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    /**
     * @return name of queen based on color
     */
    @Override
    public String getName() {
        return this.isWhite ? "wQ" : "bQ";
    }

    /**
     * @param col destination column index on the board
     * @param row destination row index on the board
     * @return if the destination is possible based on the queen's path
     */
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
        return (kingInCheckHorizontally(OpponentKing) || kingInCheckVertically(OpponentKing) || kingInCheckDiagonally(OpponentKing));
    }
}
