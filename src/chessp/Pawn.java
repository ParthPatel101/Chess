package chessp;

import board.Board;

public class Pawn extends ChessP {

    public boolean firstMove = true;
    public Pawn(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return this.isWhite ? "wp" : "bp";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // since pawns cannot move both forward and backward, check color
        if (this.isWhite) {
            // check if first move
            if (this.firstMove) {
                // check if we are moving vertically
                if (this.col == col) {
                    // check if we are moving up by at most 2 spaces
                    if (row > this.row && (row - this.row) <= 2) {
                        for (int i = this.row + 1; i <= row; i++) {
                            if (Board.chessBoard[i][col] != null) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
            }
            // check if we are moving vertically
            if (this.col == col) {
                if ((row - this.row) == 1 && Board.chessBoard[row][col] == null) {
                    return true;
                }
            }
            else {
                if ((row - this.row) == 1 && Math.abs(col - this.col) == 1) {
                    // check if there is a black enemy
                    if (Board.chessBoard[row][col] != null && !Board.chessBoard[row][col].isWhite) {
                        return true;
                    }
                    return false;
                }
            }
        }
        else {
            // check if first move
            if (this.firstMove) {
                // check if we are moving vertically
                if (this.col == col) {
                    // check if we are moving up by at most 2 spaces
                    if (row < this.row && (this.row - row) <= 2) {
                        for (int i = this.row - 1; i >= row; i--) {
                            if (Board.chessBoard[i][col] != null) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
            }
            // check if we are moving vertically
            if (this.col == col) {
                if ((this.row - row) == 1 && Board.chessBoard[row][col] == null) {
                    return true;
                }
            }
            else {
                if ((this.row - row) == 1 && Math.abs(col - this.col) == 1) {
                    // check if there is a black enemy
                    if (Board.chessBoard[row][col] != null && !Board.chessBoard[row][col].isWhite) {
                        return true;
                    }
                    return false;
                }
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
