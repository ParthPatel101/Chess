package chessp;

import board.Board;

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
            // moving right
            if (col > this.col) {
                for (int i = this.col + 1; i <= col; i++) {
                    if (Board.chessBoard[row][i] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
            }
            // moving left
            else {
                for (int i = this.col - 1; i >= col; i--) {
                    if (Board.chessBoard[row][i] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
            }
            // no obstruction
            return true;
        }

        // check if the move is in the vertical plane of queen (reuse for rook, pawn [with modification])
        if (col == this.col) {
            // moving up
            if (row > this.row) {
                for (int i = this.row + 1; i <= row; i++) {
                    if (Board.chessBoard[i][col] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
            }
            // moving down
            else {
                for (int i = this.row - 1; i >= row; i--) {
                    if (Board.chessBoard[i][col] != null) {
                        // there is an obstruction
                        return false;
                    }
                }
            }
            // no obstruction
            return true;
        }

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


