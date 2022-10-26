package chessp;

import board.Board;

public class King extends ChessP {

    private final int[] possibleXMoves = { -1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] possibleYMoves = { -1, 0, 1, -1, 1, -1, 0, 1};
    public boolean canCastle = true;

    public King(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String getName() { return this.isWhite ? "wK" : "bK"; }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if king has not castled yet
        if (canCastle) {
            // check if move is a castling move
            if (Math.abs(this.col - col) == 2) {
                // castling left
                if (this.col > col) {
                    if (Board.chessBoard[this.row][0] != null && Board.chessBoard[this.row][0].isWhite == this.isWhite && Board.chessBoard[this.row][0] instanceof Rook rook && rook.hasNotMoved) {
                        for (int i = this.col - 1; i > 0; i--) {
                            // check if there is an obstruction between the king and the rook
                            if (Board.chessBoard[this.row][i] != null) {
                                return false;
                            } else if (this.willMovePutKingInCheck(i, this.row)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                // castling right
                else if (this.col < col) {
                    if (Board.chessBoard[this.row][7] != null && Board.chessBoard[this.row][7].isWhite == this.isWhite && Board.chessBoard[this.row][7] instanceof Rook rook && rook.hasNotMoved) {
                        for (int i = this.col + 1; i < 7; i++) {
                            // check if there is an obstruction between the king and the rook
                            if (Board.chessBoard[this.row][i] != null) {
                                return false;
                            } else if (this.willMovePutKingInCheck(i, this.row)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            if ((row == this.row + possibleXMoves[i]) && (col == this.col + possibleYMoves[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        for (int i = 0; i < 8; i++) {
            int checkRow = this.row + this.possibleXMoves[i];
            int checkCol = this.col + this.possibleYMoves[i];
            if (checkRow <= 7 && checkRow >= 0 && checkCol <= 7 && checkCol >= 0) {
                if (Board.chessBoard[checkRow][checkCol] != null && Board.chessBoard[checkRow][checkCol].getName().equals(OpponentKing.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}


