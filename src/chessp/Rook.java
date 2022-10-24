package chessp;

import board.Board;

public class Rook extends ChessP {
    public Rook(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String printName() {
        return this.isWhite ? "wR" : "bR";
    }

    @Override
    public boolean is_legalMove(int col, int row) {
        // check if location is on the board
        if (row > 7 || col > 7) {
            return false;
        }
        // check if location is current location (meaning no movement)
        if (col == this.col && row == this.row) {
            return false;
        }

        // check if position is use and is of same color
        if (Board.chessBoard[row][col] != null && Board.chessBoard[row][col].isWhite == this.isWhite)  {
            return false;
        }

        // check if your king is in check and see if moving would block the check, return false if it doesn't block the check (reuse for all non-king pieces)
        if (Board.is_inCheck(this.isWhite)) {
            throw new RuntimeException();
        }

        // check if the move will put king in check
        // if (willPutinCheck) {
        // return false;
        // }

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
}
