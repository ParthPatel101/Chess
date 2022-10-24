package chessp;

import board.Board;

public abstract class ChessP {
    protected boolean isWhite;
    protected int row;
    protected int col;
    public boolean in_game;

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
        if (Board.chessBoard[row][col] != null) {
            // we know we are capturing because we checked in is_legalMove that if there is a piece at row, col it's the enemy's piece
            Board.chessBoard[row][col].in_game = false;
        }
        // if a pawn made it's first move then change indicator variable
        if (Board.chessBoard[row][col] instanceof Pawn) {
            if (((Pawn) Board.chessBoard[row][col]).firstMove) {
                ((Pawn) Board.chessBoard[row][col]).firstMove = false;
            }
        }

        Board.chessBoard[row][col] = Board.chessBoard[this.row][this.col];
        Board.chessBoard[this.row][this.col] = null;
        this.row = row;
        this.col = col;

        return true;
    }
    abstract public String printName();
    abstract public boolean is_legalMove(int col, int row);
}


