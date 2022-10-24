package chessp;

import board.Board;

public abstract class ChessP {
    public boolean isWhite;
    public int row;
    public int prevRow;
    public int col;
    public int prevCol;
    public boolean in_game;
    public ChessP capturedLast;
    public boolean undoCapturedLast;
    public boolean undoPawnFirstMove;

    public ChessP(boolean isWhite, int row, int col) {
        this.isWhite = isWhite;
        this.row = row;
        this.prevRow = this.row;
        this.col = col;
        this.prevCol = this.col;
        this.in_game = true;
        this.capturedLast = null;
        this.undoCapturedLast = false;
        this.undoPawnFirstMove = false;
    }

    public void move(int col, int row, boolean theoretical) throws Exception {
        if (!theoretical) {
            if (!preliminaryLegalMove(col, row)) throw new Exception("Move does not pass preliminary checks.");
            if (!willMoveBlockCheck(col, row)) throw new Exception("Move does not block check.");
            if (willMovePutKingInCheck(col, row)) throw new Exception("Move puts your king in check.");
        } else {
            if (!preliminaryLegalMove(col, row)) throw new Exception("Illegal Theoretical move.");
        }
        if (Board.chessBoard[row][col] != null) {
            // we know we are capturing because we checked that if there is a piece at row, col it's the enemy's piece
            if (theoretical) this.undoCapturedLast = true;
            Board.chessBoard[row][col].in_game = false;
            this.capturedLast = Board.chessBoard[row][col];
        }
        // if a pawn made it's first move then change indicator variable
        if (this instanceof Pawn pawn) {
            if (pawn.firstMove) {
                if (theoretical) this.undoPawnFirstMove = true;
                pawn.firstMove = false;
            }
        }
        Board.chessBoard[row][col] = this;
        Board.chessBoard[this.row][this.col] = null;
        this.prevRow = this.row;
        this.prevCol = this.col;
        this.row = row;
        this.col = col;
    }

    public void undoMove() {
        if (this.undoCapturedLast) {
            this.undoCapturedLast = false;
            this.capturedLast.in_game = true;
            Board.chessBoard[this.row][this.col] = this.capturedLast;
            this.capturedLast = null;
        }
        if (this.undoPawnFirstMove) {
            this.undoPawnFirstMove = false;
            Pawn pawn = (Pawn) this;
            pawn.firstMove = true;
        }
        if (Board.chessBoard[this.row][this.col].equals(this)) {
            Board.chessBoard[this.row][this.col] = null;
        }
        Board.chessBoard[this.prevRow][this.prevCol] = this;
        this.row = this.prevRow;
        this.col = this.prevCol;
    }

    public static boolean isValidLocationOnBoard(int col, int row) {
        // check if location is on the board
        return row <= 7 && row >= 0 && col <= 7 && col >= 0;
    }

    public boolean isCurrentLocation(int col, int row) {
        // check if location is current location (meaning no movement)
        return col == this.col && row == this.row;
    }

    public boolean isDestinationOccupiedByCompanion(int col, int row) {
        // check if position in use and is of same color
        return Board.chessBoard[row][col] != null && Board.chessBoard[row][col].isWhite == this.isWhite;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean preliminaryLegalMove(int col, int row) {
        if (!isValidLocationOnBoard(col, row)) return false;
        if (isCurrentLocation(col, row)) return false;
        if (!isFollowingPath(col, row)) return false;
        return !isDestinationOccupiedByCompanion(col, row);
    }

    public boolean willMoveBlockCheck(int col, int row) {
        // check if your king is in check and see if moving would block the check, return false if it doesn't block the check (reuse for all non-king pieces)
        if (Board.is_inCheck(this.isWhite)) {
            try {
                move(col, row, true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }

            if (Board.is_inCheck(this.isWhite)) {
                undoMove();
                return false;
            }
            undoMove();
        }
        return true;
    }

    public boolean willMovePutKingInCheck(int col, int row) {
        try {
            move(col, row, true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return true;
        }
        if (Board.is_inCheck(this.isWhite)) {
            undoMove();
            return true;
        }
        undoMove();
        return false;
    }

    abstract public String printName();
    abstract public boolean isFollowingPath(int col, int row);
}


