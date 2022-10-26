package chessp;

import board.Board;

public class Knight extends ChessP {
    private final int[] possibleXMoves = { 2, 1, -1, -2, -2, -1, 1, 2 };
    private final int[] possibleYMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };

    public Knight(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String getName() {
        return this.isWhite ? "wN" : "bN";
    }

    @Override
    public boolean isFollowingPath(int col, int row) {
        for (int i = 0; i < 8; i++) {
            // check if position of knight after moving is possible
            if ((row == this.row + possibleXMoves[i]) && (col == this.col + possibleYMoves[i])) {
                // no need to check if piece at destination is of opposite color (already did it above) or if there is an obstacle in the way since knight jump over other pieces
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


