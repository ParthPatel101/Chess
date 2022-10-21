package chessp;

public class Rook extends ChessP {
    public Rook(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() { return this.isWhite ? "wR" : "bR"; }

    @Override
    public boolean is_legalMove(int col, int row) {
        return false;
    }
}
