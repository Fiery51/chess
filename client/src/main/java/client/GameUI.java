package client;

import java.io.PrintStream;

import javax.print.PrintService;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class GameUI {
    static int gameID;
    static String authToken;

    public GameUI(int id, String authToken){
        //lets create the web socket connection here im thinking?
        gameID = id;
        this.authToken = authToken;
    }

    public void playGame(PrintStream out){
        //lets just redraw it here
        redrawChessBoard(out);
    }

    private static void help(PrintStream out){
        out.print(ERASE_SCREEN);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("redraw");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to redraw the chess board");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("leave");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to leave the chess game");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("make move");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to make a move");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("resign");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to resign the game");
        
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("highlight");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to highlight all legal moves on the board");
    }

    private static void redrawChessBoard(PrintStream out){
        //when redrawing the chessboard lets use the connection, and grab the current state of the chessboard, and well, redraw it
        //Actually lets just make an http request to list games, lets just filter through and grab our current game we joined
        var result = new ServerFacade(8080).listGames(authToken);
        ChessBoard board = null; 
        //first clear the terminal
        out.print(ERASE_SCREEN);
        out.print(result.statusCode());
        //next lets grab the current chessboard
        if(result.statusCode() == 400){
            out.print(ERASE_SCREEN);
            out.println("Bad request");
            //loggedIn(out);
            
        }
        if(result.statusCode() == 401){
            out.print(ERASE_SCREEN);
            out.println("Invalid Credentials");
        }
        if(result.statusCode() == 500){
            out.print(ERASE_SCREEN);
            out.println("Server Problem");
        }
        if(result.statusCode() == 200){
            int i = 0;
            for (var element : result.games().entrySet()) {
                out.println(element.getKey() + " " + 
                element.getValue() +
                 "   White: " +
                  result.whiteUsernames().get(i) +
                   "  Black " +
                    result.blackUsernames().get(i));
                i++; 
            }
            //return;

            var games = result.games().entrySet();
            board = result.gameBoards().get(gameID - 1).getBoard();
        }
        String color;
        String piece;
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                if(board.getPiece(new ChessPosition(i, j)) != null){
                    if(board.getPiece(new ChessPosition(i, j)).getTeamColor() == ChessGame.TeamColor.WHITE){
                        color = SET_TEXT_COLOR_RED;
                    }
                    else{
                        color = SET_TEXT_COLOR_BLUE;
                    }

                    if(board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                        piece = " K ";
                    }
                    if(board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.QUEEN){
                        piece = " Q ";
                    }
                    if(board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.BISHOP){
                        piece = " B ";
                    }
                    if(board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KNIGHT){
                        piece = " K ";
                    }
                    if(board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.ROOK){
                        piece = " R ";
                    }
                    if(board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.PAWN){
                        piece = " P ";
                    }
                }
                
            }
            
        }
        //then loop through and assign the chessboard pieces to each matrix slot
        //Looking at it we want to do probably a pretty big if/else loop or switch statement
        //if pieceColor == white set it to red or blue whatever i did
        //then if type == rook set it equal to R or if type == queen set it equal to Q
        //just do a matrix += i believe should work
        //display the correct matrix

    }

    private static void leave(PrintStream out){

    }

    private static void makeMove(PrintStream out){

    }

    private static void resign(PrintStream out){

    }

    private static void highlightMoves(PrintStream out){
        
    }


    
}
