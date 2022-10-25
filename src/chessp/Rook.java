package chessp;

import board.Board;

public class Rook extends ChessP {
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

        // check if the move is in the vertical plane
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

        // if none of these moves work then return false
        return false;
    }

    @Override
    public boolean isCheckingKing(ChessP OpponentKing) {
        /* Implement using for loops to check if opponentKing in movement plane */
        return false;
    }
}
