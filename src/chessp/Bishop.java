package chessp;

import board.Board;

public class Bishop extends ChessP {

    public Bishop(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String printName() {
        return this.isWhite ? "wB" : "bB";
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
}
