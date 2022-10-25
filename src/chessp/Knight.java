package chessp;

import board.Board;

public class Knight extends ChessP {

    public Knight(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String getName() {
        return this.isWhite ? "wN" : "bN";
    }


    @Override
    public boolean isFollowingPath(int col, int row) {
        int[] possibleXMoves = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] possibleYMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for (int i = 0; i < 8; i++) {
            // check if position of knight after moving is possible
            if  ((row == this.row + possibleXMoves[i]) && (col == this.col + possibleYMoves[i])) {
                // no need to check if piece at destination is of opposite color (already did it above) or if there is an obstacle in the way since knight jump over other pieces
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCheckingKing(King OpponentKing) {
        /* Implement using for loops to check if opponentKing in movement plane */
        return false;
    }

}


