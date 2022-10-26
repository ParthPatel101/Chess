package chessp;

import board.Board;

public class King extends ChessP {

    private final int[] possibleXMoves = { -1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] possibleYMoves = { -1, 0, 1, -1, 1, -1, 0, 1};
    public int castling = 0; // 0 means not yet castled, 1 means they have castled, -1 means they are not allowed to castle

    public King(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String getName() { return this.isWhite ? "wK" : "bK"; }

    @Override
    public boolean isFollowingPath(int col, int row) {
        // check if king has not castled yet
        if (castling == 0) {
            // castling left
            if (this.col > col) {
                if (Board.chessBoard[this.row][0] != null && Board.chessBoard[this.row][0] instanceof Rook && Board.chessBoard[this.row][0].isWhite == this.isWhite) {
                    for (int i = this.col; i > 0; i--) {
                        // check if there is an obstruction between the king and the rook
                    }
                }
            }
            else if (this.col < col) {

            }
            // if the move is a castling move && it works
            // castling = 1
            // return true;

            // else if the move is a not castling move && it works
            // castling = -1
            // return true;
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


