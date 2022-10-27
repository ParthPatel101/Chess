package board;

import chessp.*;

import java.util.Scanner;

public abstract class Board {
    public static ChessP[][] chessBoard;
    public static ChessP[] whitePieces;
    public static ChessP[] blackPieces;
    public static King whiteKing;
    public static King blackKing;
    public static boolean whiteTurn = true;
    public static boolean gameRunning = false;
    public static boolean draw = false;

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
                    System.out.println(e.getMessage());
                    continue;
                }

                // check if pawn is moving to opposite side then check if there is a rank indicated
                if (movingPiece instanceof Pawn && (toRow == 0 || toRow == 7)) {
                    if (arr.length == 3) {
                        switch (arr[2]) {
                            case "B" -> chessBoard[toRow][toCol] = new Bishop(movingPiece.isWhite, toRow, toCol);
                            case "N" -> chessBoard[toRow][toCol] = new Knight(movingPiece.isWhite, toRow, toCol);
                            case "R" -> chessBoard[toRow][toCol] = new Rook(movingPiece.isWhite, toRow, toCol);
                            default -> chessBoard[toRow][toCol] = new Queen(movingPiece.isWhite, toRow, toCol);
                        }
                    } else {
                        chessBoard[toRow][toCol] = new Queen(movingPiece.isWhite, toRow, toCol);
                    }
                }
            }
            whiteTurn = !whiteTurn;
//        return !whiteKing.checkMate && !blackKing.checkMate;

        }
    }

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

    public static void print_board() {
        System.out.print("\n");
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if (Board.chessBoard[row][col] == null) {
                    if ((row + col) % 2 == 0) {
                        System.out.print("## ");
                    } else {
                        // this is just for looking at index while we develop the moves will remove after
                        System.out.print(row + "" + col + " ");
//                        System.out.print("   ");
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
}


