package chessp;

import board.Board;

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
            // diag up and right
            if (row > this.row && col > this.col) {
                for (int i = this.row + 1, j = this.col + 1; i <= row && j <= col; i++, j++) {
                    if (Board.chessBoard[i][j] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
                return true;
            }
            // diag up and left
            else if (row > this.row && col < this.col) {
                for (int i = this.row + 1, j = this.col - 1; i <= row && j >= col; i++, j--) {
                    if (Board.chessBoard[i][j] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
                return true;
            }
            // diag down and right
            else if (row < this.row && col > this.col) {
                for (int i = this.row - 1, j = this.col + 1; i >= row && j <= col; i--, j++) {
                    if (Board.chessBoard[i][j] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
                return true;
            }
            // diag down and left
            else {
                for (int i = this.row - 1, j = this.col - 1; i >= row && j >= col; i--, j--) {
                    if (Board.chessBoard[i][j] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
                return true;
            }
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
