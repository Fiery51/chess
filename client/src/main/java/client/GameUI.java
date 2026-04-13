package client;

import java.io.Console;
import java.io.PrintStream;
import java.net.StandardProtocolFamily;

import javax.print.PrintService;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.KingCalculator;

import static ui.EscapeSequences.*;

public class GameUI {
    static int gameID;
    static String authToken;
    static ChessBoard theBoard; 
    static WebsocketClient connection;
    public GameUI(int id, String authToken){
        //lets create the web socket connection here im thinking?
        gameID = id;
        this.authToken = authToken;
    }

    public void playGame(PrintStream out, String teamColor, String[][] board) throws Exception{
        //lets just redraw it here
        connection = new WebsocketClient(authToken, gameID);
        redrawChessBoard(out, teamColor, board);
    }

    public static void help(PrintStream out){
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

    public static void redrawChessBoard(PrintStream out, String teamColor, String[][] chessBoard){
        //when redrawing the chessboard lets use the connection, and grab the current state of the chessboard, and well, redraw it
        //Actually lets just make an http request to list games, lets just filter through and grab our current game we joined
        var result = new ServerFacade(8080).listGames(authToken);
        ChessBoard board = null; 
        //first clear the terminal
        out.print(ERASE_SCREEN);
        //out.print(result.statusCode());
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
            var games = result.games().entrySet();
            board = result.gameBoards().get(gameID).getBoard();
            theBoard = board;
        }
        String color;
        String piece = "   ";
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
                else{
                    piece = "   ";
                    color = "";
                }
                chessBoard[9 - i][9 - j] += color + piece;
            }
            
        }
        for(int i = 0; i<10; i++){
            out.println(chessBoard[i][0] + 
            chessBoard[i][1] + 
            chessBoard[i][2] + 
            chessBoard[i][3] + 
            chessBoard[i][4] +
            chessBoard[i][5] +
            chessBoard[i][6] +
            chessBoard[i][7] +
            chessBoard[i][8] +
            chessBoard[i][9]);
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

    private static void makeMove(PrintStream out, String teamColor){
        ChessGame.TeamColor color;
        if(teamColor == "WHITE"){
            color = ChessGame.TeamColor.WHITE;
        }
        else{
            color = ChessGame.TeamColor.BLACK;
        }
        Console console = System.console();
        int startRank = 0;
        int startFile = 0;
        int endRank;
        int endFile;
        boolean validInput = false;
        while(!validInput){
            try {
                startRank = Integer.parseInt(console.readLine("What row would you like to move? (input a num): "));
                startFile = Integer.parseInt(console.readLine("What col would you like to move? (input a num): "));
                if(theBoard.getPiece(new ChessPosition(startFile, startRank)) != null &&
                    theBoard.getPiece(new ChessPosition(startFile, startRank)).getTeamColor() == color){
                        validInput = true;
                    }
            } catch (Exception e) {
                System.out.println("Please input a valid start position");
                validInput = false;
            }
        }
        validInput = false;
        while(!validInput){
            try {
                endRank = Integer.parseInt(console.readLine("What row would you like to move to? (input a num): "));
                endFile = Integer.parseInt(console.readLine("What col would you like to move to? (input a num): "));
                ChessPiece piece = theBoard.getPiece(new ChessPosition(startFile, startRank));
                ChessPosition endPosition = new ChessPosition(endFile, endRank);
                //if its in the list of valid end posistions for that specific chess piece
                if(piece.getPieceType() == ChessPiece.PieceType.KING){
                    var calculator = new KingCalculator();
                    var validMoves = calculator.returnAllMoves(theBoard, new ChessPosition(startFile, startRank), color);
                    if(validMoves.contains(endPosition)){
                        validInput = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Please input a valid start position");
                validInput = false;
            }
        }

        

    }

    private static void resign(PrintStream out){

    }

    private static void highlightMoves(PrintStream out){
        
    }



    
}
