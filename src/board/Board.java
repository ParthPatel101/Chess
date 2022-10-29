package board;

import chessp.*;

import java.util.Scanner;

/**
 * This class acts as the supervisor of the game, keeping information about the state of the game
 * It also offers general methods that operate on the board
 * This class cannot be inherited or instantiated, meaning it is purely static
 *
 * @author Parth Patel, Yash Patel
 */
public final class Board {
    /**
     * This is the chessboard of size 8 by 8
     */
    public static ChessP[][] chessBoard;
    /**
     * This is an array that stores all the white pieces
     */
    public static ChessP[] whitePieces;
    /**
     * This is an array that stores all the black pieces
     */
    public static ChessP[] blackPieces;
    /**
     * This is a reference to the white king
     */
    public static King whiteKing;
    /**
     * This is a reference to the black king
     */
    public static King blackKing;
    /**
     * This keeps track of which color's turn it is
     */
    public static boolean whiteTurn = true;
    /**
     * This keeps track of whether the game is still running, or it ended
     */
    public static boolean gameRunning = false;
    /**
     * This keeps track of whether the game ends in a draw
     */
    public static boolean draw = false;

    /**
     * This makes sure that this class cannot be instantiated
     */
    private Board() {
    }

    /**
     * starts the game by printing the board and asking the user to make a move
     * Handles all input validation and manages the user interaction as well as game results
     */
    public static void start_game() {
        Scanner in = new Scanner(System.in);
        initialize_board();
        gameRunning = true;
        while (gameRunning) {
            if (!draw) {
                print_board();
            }
            System.out.print(whiteTurn ? "White's move: " : "Black's move: ");
            String input = in.nextLine();
            if (draw) {
                if (input.equals("draw")) {
                    gameRunning = false;
                    System.out.println("draw");
                    break;
                } else {
                    // if player does not confirm the draw it will not stop asking them until they input "draw"
                    continue;
                }
            }
            if (input.equals("resign")) {
                if (whiteTurn) {
                    System.out.println("Black wins");
                } else {
                    System.out.println("White wins");
                }
                gameRunning = false;
                break;
            } else if (input.contains("draw?")) {
                draw = true;

                print_board();
            } else {
                String[] arr = input.split(" ");

                int fromRow = Integer.parseInt(arr[0].substring(1, 2)) - 1;
                int fromCol = convertLetterToNumber(arr[0].substring(0, 1));

                int toRow = Integer.parseInt(arr[1].substring(1, 2)) - 1;
                int toCol = convertLetterToNumber(arr[1].substring(0, 1));

                if (chessBoard[fromRow][fromCol] == null) {
                    System.out.println("Illegal move, try again");
                    continue;
                }

                ChessP movingPiece = chessBoard[fromRow][fromCol];
                if (whiteTurn != movingPiece.isWhite) {
                    System.out.println("Illegal move, try again");
                    continue;
                }

                // if input has a promotion input without a promotion possible
                if (arr.length == 3 && (!(movingPiece instanceof Pawn) || !(toRow == 0 || toRow == 7))) {
                    System.out.println("Illegal move, try again");
                    continue;
                }

                try {
                    movingPiece.move(toCol, toRow, false);
                } catch (Exception e) {
                    System.out.println("Illegal move, try again");
                    continue;
                }

                // check if pawn is moving to opposite side then check if there is a rank indicated
                if (movingPiece instanceof Pawn && (toRow == 0 || toRow == 7)) {
                    if (arr.length == 3) {
                        if (movingPiece.isWhite) {
                            for (int i = 8; i < whitePieces.length; i++) {
                                if (whitePieces[i] == movingPiece) {
                                    switch (arr[2]) {
                                        case "B" -> whitePieces[i] = new Bishop(true, toRow, toCol);
                                        case "N" -> whitePieces[i] = new Knight(true, toRow, toCol);
                                        case "R" -> whitePieces[i] = new Rook(true, toRow, toCol);
                                        default -> whitePieces[i] = new Queen(true, toRow, toCol);
                                    }
                                    chessBoard[toRow][toCol] = whitePieces[i];
                                }
                            }
                        }
                        else {
                            for (int i = 8; i < blackPieces.length; i++) {
                                if (blackPieces[i] == movingPiece) {
                                    switch (arr[2]) {
                                        case "B" -> blackPieces[i] = new Bishop(false, toRow, toCol);
                                        case "N" -> blackPieces[i] = new Knight(false, toRow, toCol);
                                        case "R" -> blackPieces[i] = new Rook(false, toRow, toCol);
                                        default -> blackPieces[i] = new Queen(false, toRow, toCol);
                                    }
                                    chessBoard[toRow][toCol] = blackPieces[i];
                                }
                            }
                        }
                    } else {
                        if (movingPiece.isWhite) {
                            for (int i = 8; i < whitePieces.length; i++) {
                                if (whitePieces[i] == movingPiece) {
                                    whitePieces[i] = new Queen(true, toRow, toCol);
                                    chessBoard[toRow][toCol] = whitePieces[i];
                                }
                            }
                        }
                        else {
                            for (int i = 8; i < blackPieces.length; i++) {
                                if (blackPieces[i] == movingPiece) {
                                    blackPieces[i] = new Queen(false, toRow, toCol);
                                    chessBoard[toRow][toCol] = blackPieces[i];
                                }
                            }
                        }
                    }
                }
            }
            whiteTurn = !whiteTurn;
            if (whiteTurn && isCheckMated(true)) {
                System.out.println("Checkmate");
                System.out.println("Black wins");
                gameRunning = false;
            } else if (!whiteTurn && isCheckMated(false)) {
                System.out.println("Checkmate");
                System.out.println("White wins");
                gameRunning = false;
            }
        }
    }

    /**
     * @param letter a lowercase letter part of the alphabet a through h
     * @return column index
     * Converts a letter to an index number for the chessboard
     */
    public static int convertLetterToNumber(String letter) {
        return switch (letter) {
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            case "e" -> 4;
            case "f" -> 5;
            case "g" -> 6;
            case "h" -> 7;
            default -> 0;
        };
    }

    /**
     * initializes the board by creating the chess pieces and placing them onto the board
     * Stores the initial state of the game as well
     */
    public static void initialize_board() {
        chessBoard = new ChessP[8][8];
        // initialize the Whites and Blacks arrays with their respective Chess Piece Objects and stored pieces based on game order
        blackPieces = new ChessP[16];
        blackPieces[0] = new Rook(false, 7, 0);
        blackPieces[1] = new Knight(false, 7, 1);
        blackPieces[2] = new Bishop(false, 7, 2);
        blackPieces[3] = new Queen(false, 7, 3);
        blackPieces[4] = new King(false, 7, 4);
        blackKing = (King) blackPieces[4];
        blackPieces[5] = new Bishop(false, 7, 5);
        blackPieces[6] = new Knight(false, 7, 6);
        blackPieces[7] = new Rook(false, 7, 7);
        for (int i = 0; i < 8; i++) {
            blackPieces[i + 8] = new Pawn(false, 6, i);
        }

        whitePieces = new ChessP[16]; // order this based on the game order [rook, knight, bishop, queen, king ..., pawn, pawn, pawn ...]
        whitePieces[0] = new Rook(true, 0, 0);
        whitePieces[1] = new Knight(true, 0, 1);
        whitePieces[2] = new Bishop(true, 0, 2);
        whitePieces[3] = new Queen(true, 0, 3);
        whitePieces[4] = new King(true, 0, 4);
        whiteKing = (King) whitePieces[4];
        whitePieces[5] = new Bishop(true, 0, 5);
        whitePieces[6] = new Knight(true, 0, 6);
        whitePieces[7] = new Rook(true, 0, 7);
        for (int i = 0; i < 8; i++) {
            whitePieces[i + 8] = new Pawn(true, 1, i);
        }
        // store reference to the white and black kings in Board.WhiteKing and Board.BlackKing
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                // put pieces in their correct position based on row
                if (row == 7) {
                    chessBoard[row][col] = blackPieces[col];
                } else if (row == 6) {
                    chessBoard[row][col] = blackPieces[col + 8];
                } else if (row == 1) {
                    chessBoard[row][col] = whitePieces[col + 8];
                } else if (row == 0) {
                    chessBoard[row][col] = whitePieces[col];
                } else {
                    chessBoard[row][col] = null;
                }
            }
        }

    }

    /**
     * Prints the chess board onto the terminal
     */
    public static void print_board() {
        System.out.print("\n");
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if (Board.chessBoard[row][col] == null) {
                    if ((row + col) % 2 == 0) {
                        System.out.print("## ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print(Board.chessBoard[row][col].getName() + " ");
                }
                if (col == 7) {
                    System.out.print((row + 1) + "\n");
                }
            }
        }
        System.out.println(" a  b  c  d  e  f  g  h\n");
    }

    /**
     * @param isWhite Which team?
     * @return whether the team's king is in check
     * Checks whether the team's king is in check
     */
    public static boolean is_inCheck(boolean isWhite) {
        // check all black pieces that are alive to see if they are checking white king
        if (isWhite) {
            for (ChessP i : blackPieces) {
                if (i.in_game && i.isCheckingKing(whiteKing)) return true;
            }
        }
        // check all white pieces that are alive to see if they are checking black king
        else {
            for (ChessP i : whitePieces) {
                if (i.in_game && i.isCheckingKing(blackKing)) return true;
            }
        }
        return false;
    }

    /**
     * @param isWhite Which team?
     * @return whether the team's king is checkmated
     * Checks whether the team's king is checkmated by opponent team
     */
    public static boolean isCheckMated(boolean isWhite) {
        // check if king is even in check or not
        if (!is_inCheck(isWhite)) {
            return false;
        }

        // check if king can move anywhere to uncheck
        if (isWhite) {
            for (int i = 0; i < 8; i++) {
                if (!whiteKing.willMovePutKingInCheck(whiteKing.col + King.possibleYMoves[i], whiteKing.row + King.possibleXMoves[i])) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (!blackKing.willMovePutKingInCheck(blackKing.col + King.possibleYMoves[i], blackKing.row + King.possibleXMoves[i])) {
                    return false;
                }
            }
        }

        // check if any pieces can move in the way to block check
        if (isWhite) {
            for (ChessP piece : whitePieces) {
                if (piece.in_game) {
                    // check if Pawn can block check
                    if (piece instanceof Pawn pawn) {
                        // check 1 space above
                        if (pawn.willMoveBlockCheck(pawn.col, pawn.row + 1)) {
                            return false;
                        }
                        // check 2 space above (if pawn has not moved yet)
                        if (pawn.firstMove && pawn.willMoveBlockCheck(pawn.col, pawn.row + 2)) {
                            return false;
                        }
                        // check up right (capture move)
                        if (pawn.willMoveBlockCheck(pawn.col + 1, pawn.row + 1)) {
                            return false;
                        }
                        // check up left (capture move)
                        if (pawn.willMoveBlockCheck(pawn.col - 1, pawn.row + 1)) {
                            return false;
                        }
                    }
                    // check if Knight can block check
                    else if (piece instanceof Knight knight) {
                        for (int i = 0; i < 8; i++) {
                            if (knight.willMoveBlockCheck(knight.col + Knight.possibleYMoves[i], knight.row + Knight.possibleXMoves[i])) {
                                return false;
                            }
                        }
                    }
                    // check if Bishop can block check
                    else if (piece instanceof Bishop bishop) {
                        for (int i = 1; i < 8; i++) {
                            // diag up and right
                            if (bishop.willMoveBlockCheck(bishop.col + i, bishop.row + i)) {
                                return false;
                            }
                            // diag up and left
                            if (bishop.willMoveBlockCheck(bishop.col - i, bishop.row + i)) {
                                return false;
                            }
                            // diag down and right
                            if (bishop.willMoveBlockCheck(bishop.col + i, bishop.row - i)) {
                                return false;
                            }
                            // diag down and left
                            if (bishop.willMoveBlockCheck(bishop.col - i, bishop.row - i)) {
                                return false;
                            }
                        }
                    }
                    // check if Rook can block check
                    else if (piece instanceof Rook rook) {
                        for (int i = 1; i < 8; i++) {
                            // up
                            if (rook.willMoveBlockCheck(rook.col, rook.row + i)) {
                                return false;
                            }
                            // down
                            if (rook.willMoveBlockCheck(rook.col, rook.row - i)) {
                                return false;
                            }
                            // right
                            if (rook.willMoveBlockCheck(rook.col + i, rook.row)) {
                                return false;
                            }
                            // left
                            if (rook.willMoveBlockCheck(rook.col - i, rook.row)) {
                                return false;
                            }
                        }
                    }
                    // check if Queen can block check
                    else if (piece instanceof Queen queen) {
                        for (int i = 1; i < 8; i++) {
                            // up
                            if (queen.willMoveBlockCheck(queen.col, queen.row + i)) {
                                return false;
                            }
                            // down
                            if (queen.willMoveBlockCheck(queen.col, queen.row - i)) {
                                return false;
                            }
                            // right
                            if (queen.willMoveBlockCheck(queen.col + i, queen.row)) {
                                return false;
                            }
                            // left
                            if (queen.willMoveBlockCheck(queen.col - i, queen.row)) {
                                return false;
                            }
                            // diag up and right
                            if (queen.willMoveBlockCheck(queen.col + i, queen.row + i)) {
                                return false;
                            }
                            // diag up and left
                            if (queen.willMoveBlockCheck(queen.col - i, queen.row + i)) {
                                return false;
                            }
                            // diag down and right
                            if (queen.willMoveBlockCheck(queen.col + i, queen.row - i)) {
                                return false;
                            }
                            // diag down and left
                            if (queen.willMoveBlockCheck(queen.col - i, queen.row - i)) {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            for (ChessP piece : blackPieces) {
                if (piece.in_game) {
                    // check if Pawn can block check
                    if (piece instanceof Pawn pawn) {
                        // check 1 space below
                        if (pawn.willMoveBlockCheck(pawn.col, pawn.row - 1)) {
                            return false;
                        }
                        // check 2 space below (if pawn has not moved yet)
                        if (pawn.firstMove && pawn.willMoveBlockCheck(pawn.col, pawn.row - 2)) {
                            return false;
                        }
                        // check down right (capture move)
                        if (pawn.willMoveBlockCheck(pawn.col + 1, pawn.row - 1)) {
                            return false;
                        }
                        // check down left (capture move)
                        if (pawn.willMoveBlockCheck(pawn.col - 1, pawn.row - 1)) {
                            return false;
                        }
                    }
                    // check if Knight can block check
                    else if (piece instanceof Knight knight) {
                        for (int i = 0; i < 8; i++) {
                            if (knight.willMoveBlockCheck(knight.col + Knight.possibleYMoves[i], knight.row + Knight.possibleXMoves[i])) {
                                return false;
                            }
                        }
                    }
                    // check if Bishop can block check
                    else if (piece instanceof Bishop bishop) {
                        for (int i = 1; i < 8; i++) {
                            // diag up and right
                            if (bishop.willMoveBlockCheck(bishop.col + i, bishop.row + i)) {
                                return false;
                            }
                            // diag up and left
                            if (bishop.willMoveBlockCheck(bishop.col - i, bishop.row + i)) {
                                return false;
                            }
                            // diag down and right
                            if (bishop.willMoveBlockCheck(bishop.col + i, bishop.row - i)) {
                                return false;
                            }
                            // diag down and left
                            if (bishop.willMoveBlockCheck(bishop.col - i, bishop.row - i)) {
                                return false;
                            }
                        }
                    }
                    // check if Rook can block check
                    else if (piece instanceof Rook rook) {
                        for (int i = 1; i < 8; i++) {
                            // up
                            if (rook.willMoveBlockCheck(rook.col, rook.row + i)) {
                                return false;
                            }
                            // down
                            if (rook.willMoveBlockCheck(rook.col, rook.row - i)) {
                                return false;
                            }
                            // right
                            if (rook.willMoveBlockCheck(rook.col + i, rook.row)) {
                                return false;
                            }
                            // left
                            if (rook.willMoveBlockCheck(rook.col - i, rook.row)) {
                                return false;
                            }
                        }
                    }
                    // check if Queen can block check
                    else if (piece instanceof Queen queen) {
                        for (int i = 1; i < 8; i++) {
                            // up
                            if (queen.willMoveBlockCheck(queen.col, queen.row + i)) {
                                return false;
                            }
                            // down
                            if (queen.willMoveBlockCheck(queen.col, queen.row - i)) {
                                return false;
                            }
                            // right
                            if (queen.willMoveBlockCheck(queen.col + i, queen.row)) {
                                return false;
                            }
                            // left
                            if (queen.willMoveBlockCheck(queen.col - i, queen.row)) {
                                return false;
                            }
                            // diag up and right
                            if (queen.willMoveBlockCheck(queen.col + i, queen.row + i)) {
                                return false;
                            }
                            // diag up and left
                            if (queen.willMoveBlockCheck(queen.col - i, queen.row + i)) {
                                return false;
                            }
                            // diag down and right
                            if (queen.willMoveBlockCheck(queen.col + i, queen.row - i)) {
                                return false;
                            }
                            // diag down and left
                            if (queen.willMoveBlockCheck(queen.col - i, queen.row - i)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // if all efforts to uncheck king fail then checkmate
        return true;
    }
}


