package chessp;

public class Bishop extends ChessP{

    public Bishop(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() {
        return this.isWhite ? "wB" : "bB";
    }


    @Override
    public boolean is_legalMove(int col, int row) {
        return false;
    }
}
