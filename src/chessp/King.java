package chessp;

import board.Board;

/**
 * King chess piece
 */
public class King extends ChessP {

    /**
     * list of possible X moves of the king
     */
    public static final int[] possibleXMoves = {-1, -1, -1, 0, 0, 1, 1, 1};
    /**
     * list of possible Y moves of the king
     */
    public static final int[] possibleYMoves = {-1, 0, 1, -1, 1, -1, 0, 1};
    /**
     * whether this king can castle
     */
    public boolean canCastle = true;
    /**
     * whether this king is castling right now
     */
    public boolean isCastling = false;

    /**
     * @param isWhite which team is this king on?
     * @param row current row index on the board
     * @param col current column index on the board
     * initializes the king information
     */
    public King(boolean isWhite, int row, int col) {
        super(isWhite, row, col);
    }

    @Override
    public String getName() {
        return this.isWhite ? "wK" : "bK";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        if (isCastling) {
            return true;
        }
        // check if king has not castled yet
        if (canCastle) {
            // check if move is a castling move
            if (Math.abs(this.col - col) == 2) {
                // castling left
                if (this.col > col) {
                    if (Board.chessBoard[this.row][0] != null && Board.chessBoard[this.row][0].isWhite == this.isWhite && Board.chessBoard[this.row][0] instanceof Rook rook && rook.hasNotMoved) {
                        // check if first move won't work
                        if (Board.chessBoard[this.row][this.col - 1] != null) {
                            return false;
                        }
                        isCastling = true;
                        if (this.willMovePutKingInCheck(this.col - 1, this.row)) {
                            isCastling = false;
                            return false;
                        }
                        // check if second move won't work
                        if (Board.chessBoard[this.row][this.col - 2] != null) {
                            return false;
                        }
                        isCastling = true;
                        if (this.willMovePutKingInCheck(this.col - 2, this.row)) {
                            isCastling = false;
                            return false;
                        }
                        isCastling = false;
                        return true;
                    }
                }
                // castling right
                else if (this.col < col) {
                    if (Board.chessBoard[this.row][7] != null && Board.chessBoard[this.row][7].isWhite == this.isWhite && Board.chessBoard[this.row][7] instanceof Rook rook && rook.hasNotMoved) {
                        // check if first move won't work
                        if (Board.chessBoard[this.row][this.col + 1] != null) {
                            return false;
                        }
                        isCastling = true;
                        if (this.willMovePutKingInCheck(this.col + 1, this.row)) {
                            isCastling = false;
                            return false;
                        }
                        // check if second move won't work
                        if (Board.chessBoard[this.row][this.col + 2] != null) {
                            return false;
                        }
                        isCastling = true;
                        if (this.willMovePutKingInCheck(this.col + 2, this.row)) {
                            isCastling = false;
                            return false;
                        }
                        isCastling = false;
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
        return kingInCheckByPossibleMoves(OpponentKing, possibleXMoves, possibleYMoves);
    }
}


