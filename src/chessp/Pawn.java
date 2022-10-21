package chessp;

public class Pawn extends ChessP {
    public Pawn(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() {
        return this.isWhite ? "wp" : "bp";
    }

    @Override
    public boolean is_legalMove(int col, int row) {
        return false;
    }
}
