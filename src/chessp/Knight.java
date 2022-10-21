package chessp;

public class Knight extends ChessP{

    public Knight(boolean isWhite, int row, int col) { super(isWhite, row, col); }

    @Override
    public String printName() {
        return this.isWhite ? "wN" : "bN";
    }


    @Override
    public boolean is_legalMove(int col, int row) {
        return false;
    }
}
