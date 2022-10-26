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

    public boolean isValidLocation(int col, int row) {
        // check if location is on the board
        return (row <= 7 && row >= 0) && (col <= 7 && col >= 0) && (row != this.row || col != this.col);
    }

    public boolean isOccupiedBySameColor(int col, int row) {
        // check if position in use and is of same color
        return Board.chessBoard[row][col] != null && Board.chessBoard[row][col].isWhite == this.isWhite;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean preliminaryLegalMove(int col, int row) {
        if (!isValidLocation(col, row)) return false;
        if (isOccupiedBySameColor(col, row)) return false;
        return (isFollowingPath(col, row));
    }

    public boolean willMoveBlockCheck(int col, int row) {
        // check if your king is in check and see if moving would block the check, return false if it doesn't block the check
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

    public boolean canMoveHorizontally(int col, int row) {
        // moving right
        if (col > this.col) {
            for (int i = this.col + 1; i < col; i++) {
                if (Board.chessBoard[row][i] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
        }
        // moving left
        else {
            for (int i = this.col - 1; i > col; i--) {
                if (Board.chessBoard[row][i] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
        }
        // no obstruction
        return true;
    }

    public boolean canMoveVertically(int col, int row) {
        // moving up
        if (row > this.row) {
            for (int i = this.row + 1; i < row; i++) {
                if (Board.chessBoard[i][col] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
        }
        // moving down
        else {
            for (int i = this.row - 1; i > row; i--) {
                if (Board.chessBoard[i][col] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
        }
        // no obstruction
        return true;
    }

    public boolean canMoveDiagonally(int col, int row) {
        // diag up and right
        if (row > this.row && col > this.col) {
            for (int i = this.row + 1, j = this.col + 1; i < row && j < col; i++, j++) {
                if (Board.chessBoard[i][j] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
            return true;
        }
        // diag up and left
        else if (row > this.row && col < this.col) {
            for (int i = this.row + 1, j = this.col - 1; i < row && j > col; i++, j--) {
                if (Board.chessBoard[i][j] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
            return true;
        }
        // diag down and right
        else if (row < this.row && col > this.col) {
            for (int i = this.row - 1, j = this.col + 1; i > row && j < col; i--, j++) {
                if (Board.chessBoard[i][j] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
            return true;
        }
        // diag down and left
        else {
            for (int i = this.row - 1, j = this.col - 1; i > row && j > col; i--, j--) {
                if (Board.chessBoard[i][j] != null) {
                    // there is an obstruction on the way to the destination
                    return false;
                }
            }
            return true;
        }
    }

    public boolean kingInCheckHorizontally(King OpponentKing) {
        // check right
        for (int i = this.col + 1; i <= 7; i++) {
            if (Board.chessBoard[this.row][i] != null) {
                if (Board.chessBoard[this.row][i].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // check left
        for (int i = this.col - 1; i >= 0; i--) {
            if (Board.chessBoard[this.row][i] != null) {
                if (Board.chessBoard[this.row][i].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // no king in the way
        return false;
    }

    public boolean kingInCheckVertically(King OpponentKing) {
        // check up
        for (int i = this.row + 1; i <= 7; i++) {
            if (Board.chessBoard[i][this.col] != null) {
                if (Board.chessBoard[i][this.col].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // check down
        for (int i = this.row - 1; i >= 0; i--) {
            if (Board.chessBoard[i][this.col] != null) {
                if (Board.chessBoard[i][this.col].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // no king in the way
        return false;
    }
    public boolean kingInCheckDiagonally(King OpponentKing) {
        // check diag up and right
        for (int i = this.row + 1, j = this.col + 1; i <= 7 && j <= 7; i++, j++) {
            if (Board.chessBoard[i][j] != null) {
                if (Board.chessBoard[i][j].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // check diag up and left
        for (int i = this.row + 1, j = this.col - 1; i <= 7 && j >= 0; i++, j--) {
            if (Board.chessBoard[i][j] != null) {
                if (Board.chessBoard[i][j].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // check diag down and right
        for (int i = this.row - 1, j = this.col + 1; i >= 0 && j <= 7; i--, j++) {
            if (Board.chessBoard[i][j] != null) {
                if (Board.chessBoard[i][j].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // check diag down and left
        for (int i = this.row - 1, j = this.col - 1; i >= 0 && j >= 0; i--, j--) {
            if (Board.chessBoard[i][j] != null) {
                if (Board.chessBoard[i][j].getName().equals(OpponentKing.getName())) {
                    return true;
                }
                break;
            }
        }
        // no king in the way
        return false;
    }

        abstract public String getName();
    abstract public boolean isFollowingPath(int col, int row);
    abstract public boolean isCheckingKing(King OpponentKing);
}