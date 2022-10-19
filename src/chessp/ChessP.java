package chessp;

import board.Board;

public abstract class ChessP {
    protected boolean isWhite;
    protected int row;
    protected int col;
    protected boolean in_game;

    public ChessP(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.col = col;
        this.in_game = true;
    }

    public boolean move(int col, int row) {
        if (!is_legalMove(col, row)) {
            return false;
        }
        Board.board[row][col] = Board.board[this.row][this.col];
        Board.board[this.row][this.col] = null;
        this.row = row;
        this.col = col;
        return true;
    }
    abstract public boolean is_legalMove(int col, int row);
}


