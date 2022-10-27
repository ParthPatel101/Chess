package chessp;

import board.Board;

public class Pawn extends ChessP {

    public boolean firstMove = true;
    public boolean ableToEnPassant = false;
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
                return (row - this.row) == 1 && Board.chessBoard[row][col] == null;
            }
            else {
                if ((row - this.row) == 1 && Math.abs(col - this.col) == 1) {
                    // check for en passant (only can happen on row 5 for white)
                    if (row == 4) {
                        // piece next to pawn
                        if (Board.chessBoard[row - 1][col] != null && Board.chessBoard[row - 1][col] instanceof Pawn pawn && !pawn.isWhite && pawn.ableToEnPassant) {
                            return true;
                        }
                    }
                    // check if there is a black enemy
                    return Board.chessBoard[row][col] != null && !Board.chessBoard[row][col].isWhite;
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
                return (this.row - row) == 1 && Board.chessBoard[row][col] == null;
            }
            else {
                if ((this.row - row) == 1 && Math.abs(col - this.col) == 1) {
                    // check for en passant (only can happen on row 5 for white)
                    if (row == 3) {
                        // piece next to pawn
                        if (Board.chessBoard[row + 1][col] != null && Board.chessBoard[row + 1][col] instanceof Pawn pawn && !pawn.isWhite && pawn.ableToEnPassant) {
                            return true;
                        }
                    }
                    // check if there is a white enemy
                    return Board.chessBoard[row][col] != null && Board.chessBoard[row][col].isWhite;
                }
            }
        }

        // if none of these moves work then return false
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        if (this.isWhite) {
            int upRow = this.row + 1;
            // check up-right
            int upRightCol = this.col + 1;
            if (upRow <= 7 && upRow >= 0 && upRightCol <= 7 && upRightCol >= 0) {
                if (Board.chessBoard[upRow][upRightCol] != null && Board.chessBoard[upRow][upRightCol].getName().equals(OpponentKing.getName())) {
                    return true;
                }
            }
            // check up-left
            int upLeftCol = this.col - 1;
            if (upRow <= 7 && upRow >= 0 && upLeftCol <= 7 && upLeftCol >= 0) {
                return Board.chessBoard[upRow][upLeftCol] != null && Board.chessBoard[upRow][upLeftCol].getName().equals(OpponentKing.getName());
            }
        }
        else {
            int downRow = this.row - 1;
            // check down-right
            int downRightCol = this.col + 1;
            if (downRow <= 7 && downRow >= 0 && downRightCol <= 7 && downRightCol >= 0) {
                if (Board.chessBoard[downRow][downRightCol] != null && Board.chessBoard[downRow][downRightCol].getName().equals(OpponentKing.getName())) {
                    return true;
                }
            }
            // check down-left
            int downLeftCol = this.col - 1;
            if (downRow <= 7 && downRow >= 0 && downLeftCol <= 7 && downLeftCol >= 0) {
                return Board.chessBoard[downRow][downLeftCol] != null && Board.chessBoard[downRow][downLeftCol].getName().equals(OpponentKing.getName());
            }
        }
        return false;
    }
}
