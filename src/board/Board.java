package board;

import chessp.*;

import java.util.Scanner;

public abstract class Board {
    public static ChessP[][] chessBoard;
    public static ChessP[] whitePieces;
    public static ChessP[] blackPieces;
    public static ChessP whiteKing;
    public static ChessP blackKing;
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
                }
                else {
                    // if player does not confirm the draw it will not stop asking them until they input "draw"
                    continue;
                }
            }
            if (input.equals("resign")) {
                if (whiteTurn) {
                    System.out.println("Black wins");
                }
                else {
                    System.out.println("White wins");
                }
                gameRunning = false;
                break;
            }
            else if (input.contains("draw?")) {
                draw = true;

                print_board();
            }
            else {
                String[] arr = input.split(" ");

                int fromRow = Integer.parseInt(arr[0].substring(1,2)) - 1;
                int fromCol = convertLetterToNumber(arr[0].substring(0,1));

                int toRow = Integer.parseInt(arr[1].substring(1,2)) - 1;
                int toCol = convertLetterToNumber(arr[1].substring(0,1));

                if (chessBoard[fromRow][fromCol] == null) {
                    System.out.println("Illegal move, try again");
                    continue;
                }

                ChessP movingPiece = chessBoard[fromRow][fromCol];
                System.out.println(fromRow + "" + fromCol);
                System.out.println(toRow + "" + toCol);
                System.out.println(chessBoard[fromRow][fromCol].getName());
                System.out.println(chessBoard[toRow][toCol].getName());

                // check if pawn is moving to opposite side then check if there is a rank indicated
                if (movingPiece instanceof Pawn && (toRow == 0 || toRow == 7)) {
                    if (arr.length == 3) {
                        switch (arr[2]) {
                            case "B":
                                System.out.println("Bishop");
                                break;
                            case "N":
                                System.out.println("Knight");
                                break;
                            case "R":
                                System.out.println("Rook");
                                break;
                            default:
                                System.out.println("Queen");
                                break;
                        }
                    } else {
                        System.out.println("Queen");
                    }
                }
            }
            whiteTurn = !whiteTurn;
//        return !whiteKing.checkMate && !blackKing.checkMate;

        }
    }

    public static int convertLetterToNumber(String letter) {
        switch (letter) {
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            case "e":
                return 4;
            case "f":
                return 5;
            case "g":
                return 6;
            case "h":
                return 7;
            default:
                return 0;
        }
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
         blackKing = blackPieces[4];
         blackPieces[5] = new Bishop(false, 7, 5);
         blackPieces[6] = new Knight(false, 7, 6);
         blackPieces[7] = new Rook(false, 7, 7);
         for (int i = 0; i < 8; i++) {
             blackPieces[i+8] = new Pawn(false, 6, i);
         }

         whitePieces = new ChessP[16]; // order this based on the game order [rook, knight, bishop, queen, king ..., pawn, pawn, pawn ...]
         whitePieces[0] = new Rook(true, 0, 0);
         whitePieces[1] = new Knight(true, 0, 1);
         whitePieces[2] = new Bishop(true, 0, 2);
         whitePieces[3] = new Queen(true, 0, 3);
         whitePieces[4] = new King(true, 0, 4);
         whiteKing = whitePieces[4];
         whitePieces[5] = new Bishop(true, 0, 5);
         whitePieces[6] = new Knight(true, 0, 6);
         whitePieces[7] = new Rook(true, 0, 7);
         for (int i = 0; i < 8; i++) {
             whitePieces[i+8] = new Pawn(true, 1, i);
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
                if (i.in_game) {
                    if (i instanceof Bishop) {
                        if (BishopCheck((King) whiteKing, (Bishop) i)) {
                            return true;
                        }
                    } else if (i instanceof King) {
                        if (KingCheck((King) whiteKing, (King) i)) {
                            return true;
                        }
                    } else if (i instanceof Knight) {
                        if (KnightCheck((King) whiteKing, (Knight) i)) {
                            return true;
                        }
                    } else if (i instanceof Pawn) {
                        if (PawnCheck((King) whiteKing, (Pawn) i)) {
                            return true;
                        }
                    } else if (i instanceof Queen) {
                        if (QueenCheck((King) whiteKing, (Queen) i)) {
                            return true;
                        }
                    } else {
                        if (RookCheck((King) whiteKing, (Rook) i)) {
                            return true;
                        }
                    }
                }
            }
        }
        // check all white pieces that are alive to see if they are checking white king
        else {
            for (ChessP i : whitePieces) {
                if (i.in_game) {
                    if (i instanceof Bishop) {
                        if (BishopCheck((King) blackKing, (Bishop) i)) {
                            return true;
                        }
                    } else if (i instanceof King) {
                        if (KingCheck((King) blackKing, (King) i)) {
                            return true;
                        }
                    } else if (i instanceof Knight) {
                        if (KnightCheck((King) blackKing, (Knight) i)) {
                            return true;
                        }
                    } else if (i instanceof Pawn) {
                        if (PawnCheck((King) blackKing, (Pawn) i)) {
                            return true;
                        }
                    } else if (i instanceof Queen) {
                        if (QueenCheck((King) blackKing, (Queen) i)) {
                            return true;
                        }
                    } else {
                        if (RookCheck((King) blackKing, (Rook) i)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean BishopCheck(King yourKing, Bishop opponentBishop) {
        /* Implement checker code here */
        return false;
    }
    public static boolean KingCheck(King yourKing, King opponentKing) {
        /* Implement checker code here */
        return false;
    }
    public static boolean KnightCheck(King yourKing, Knight opponentKnight) {
        /* Implement checker code here */
        return false;
    }
    public static boolean PawnCheck(King yourKing, Pawn opponentPawn) {
        /* Implement checker code here */
        return false;
    }
    public static boolean QueenCheck(King yourKing, Queen opponentQueen) {
        /* Implement checker code here */
        return false;
    }
    public static boolean RookCheck(King yourKing, Rook opponentRook) {
        /* Implement checker code here */
        return false;
    }
}


