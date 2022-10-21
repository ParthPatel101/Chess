package chessp;

public class King extends ChessP{
    public King(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() {
        return this.isWhite ? "wK" : "bK";
    }

    @Override
    public boolean is_legalMove(int col, int row) {
        return false;
    }
}
