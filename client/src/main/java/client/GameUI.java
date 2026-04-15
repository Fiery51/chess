package client;

import java.io.Console;
import java.io.PrintStream;

import java.util.ArrayList;


import com.google.gson.Gson;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import static ui.EscapeSequences.*;

public class GameUI {
    static int gameID;
    static String authToken;
    static ChessBoard theBoard; 
    static WebsocketClient connection;
    static PrintStream out;
    static String teamColor;
    static ChessGame game;
    static Boolean leaveGame;
    public GameUI(int id, String authToken){
        //lets create the web socket connection here im thinking?
        gameID = id;
        this.authToken = authToken;
    }

    public void playGame(PrintStream out, String teamColor) throws Exception{
        //lets just redraw it here
        this.teamColor = teamColor;
        this.out = out;
        this.game = game;
        leaveGame = false;
        connection = new WebsocketClient(authToken, gameID);
        runGame();
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

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("help");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to explain all the commands");

    }

    public static void redrawChessBoard(ChessBoard theBoard){
        GameUI.theBoard = theBoard;
        String[][] chessBoard;
        if(teamColor.equals("WHITE")){
            chessBoard = drawBackgroundWhite();
        }
        else{
            chessBoard = drawBackgroundBlack();
        }
        //when redrawing the chessboard lets use the connection, and grab the current state of the chessboard, and well, redraw it
        //Actually lets just make an http request to list games, lets just filter through and grab our current game we joined

        ChessBoard board = theBoard; 
        //first clear the terminal
        out.print(ERASE_SCREEN);
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
                        piece = " N ";
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
                if(teamColor.equals("WHITE")){
                    chessBoard[9 - i][j] += color + piece;
                }
                else{
                    chessBoard[i][9 - j] += color + piece;
                }
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

    public static void redrawChessBoard(ChessBoard theBoard, ArrayList<ChessPosition> highlight){
        GameUI.theBoard = theBoard;
        String[][] chessBoard;
        if(teamColor.equals("WHITE")){
            chessBoard = drawBackgroundWhite();
        }
        else{
            chessBoard = drawBackgroundBlack();
        }
        //when redrawing the chessboard lets use the connection, and grab the current state of the chessboard, and well, redraw it
        //Actually lets just make an http request to list games, lets just filter through and grab our current game we joined

        ChessBoard board = theBoard; 
        //first clear the terminal
        out.print(ERASE_SCREEN);
        String color;
        String piece = "   ";

        for (var square : highlight) {
            var row = square.getRow();
            var col = square.getColumn();
            if(teamColor.equals("WHITE")){
                chessBoard[9 - row][col] = SET_BG_COLOR_GREEN;
            }
            if(teamColor.equals("BLACK")){
                chessBoard[row][9 - col] = SET_BG_COLOR_GREEN;
            }
        }

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
                        piece = " N ";
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
                if(teamColor.equals("WHITE")){
                    chessBoard[9 - i][j] += color + piece;
                }
                else{
                    chessBoard[i][9 - j] += color + piece;
                }
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
    }

    private static void leave(PrintStream out){
        var serializer = new Gson();
        UserGameCommand data = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID);
        var json = serializer.toJson(data);
        if(connection == null || connection.session == null || !connection.session.isOpen()){
            System.out.println("Discconected, restart the client again, spent 2 hours trying to debug this. Keep the client open smh");
            return;
        }
        connection.session.getAsyncRemote().sendText(json);
        leaveGame = true;
    }

    private static void makeMove(PrintStream out, String teamColor, ChessGame game){
        ChessGame.TeamColor color;
        if(teamColor.equals("WHITE")){
            color = ChessGame.TeamColor.WHITE;
        }
        else{
            color = ChessGame.TeamColor.BLACK;
        }
        Console console = System.console();
        int startRank = 0;
        int startFile = 0;
        int endRank = 0;
        int endFile = 0;
        boolean validInput = false;
        ChessPosition startPosition = null;
        ChessPosition endPosition;
        ChessMove move = null;
        

        while(!validInput){
            try {
                startRank = Integer.parseInt(console.readLine("What row would you like to move? (input a num): "));
                startFile = Integer.parseInt(console.readLine("What col would you like to move? (input a num): "));
                validInput = true;
            } catch (Exception e) {
                System.out.println("Please input a valid input");
            }
        }
        
        if(theBoard.getPiece(new ChessPosition(startFile, startRank)) != null &&
            theBoard.getPiece(new ChessPosition(startFile, startRank)).getTeamColor() == color){
                validInput = true;
                startPosition = new ChessPosition(startFile, startRank);
            }
        else{
            System.out.println("Invalid start position");
            return;
        }
        validInput = false;

        while (!validInput) {
            try {
                endRank = Integer.parseInt(console.readLine("What row would you like to move to? (input a num): "));
                endFile = Integer.parseInt(console.readLine("What col would you like to move to? (input a num): "));
                validInput = true;
            } catch (Exception e) {
                System.out.println("Please input a valid input");
            }
        }
        endPosition = new ChessPosition(endFile, endRank);
        ChessPiece.PieceType promotionPieceType = null;
        if(theBoard.getPiece(startPosition).getPieceType() == ChessPiece.PieceType.PAWN
            && (endPosition.getRow() == 8 || endPosition.getRow() == 1)){
            Boolean valid = false;
            while(!valid){
                String promotionPiece = console.readLine("What would you like to promote your piece to (B) (N) (R) (Q): ");
                switch (promotionPiece) {
                    case "B":
                        promotionPieceType = ChessPiece.PieceType.BISHOP;
                        valid = true;
                        break;
                    case "N":
                        promotionPieceType = ChessPiece.PieceType.KNIGHT;
                        valid = true;
                        break;
                    case "R":
                        promotionPieceType = ChessPiece.PieceType.ROOK;
                        valid = true;
                        break;
                    case "Q":
                        promotionPieceType = ChessPiece.PieceType.QUEEN;
                        valid = true;
                        break;
                    default:
                        System.out.println("Please input a valid promotion piece");
                        break;
                }
            }
        }
        move = new ChessMove(startPosition, endPosition, promotionPieceType);


        
        var serializer = new Gson();
        MakeMoveCommand data = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID, move);
        var json = serializer.toJson(data);
        if(connection == null || connection.session == null || !connection.session.isOpen()){
            System.out.println("Discconected, restart the client again, spent 2 hours trying to debug this. Keep the client open smh");
            return;
        }
        connection.session.getAsyncRemote().sendText(json);

        

    }

    private static void resign(PrintStream out){
        var serializer = new Gson();
        UserGameCommand data = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID);
        var json = serializer.toJson(data);
        if(connection == null || connection.session == null || !connection.session.isOpen()){
            System.out.println("Discconected, restart the client again, spent 2 hours trying to debug this. Keep the client open smh");
            return;
        }
        connection.session.getAsyncRemote().sendText(json);
    }

    private static void highlightMoves(PrintStream out){
        int startRank = 0;
        int startFile = 0;
        boolean validInput = false;
        ChessPosition startPosition = null;
        Console console = System.console();
        
        while(!validInput){
            try {
                startRank = Integer.parseInt(console.readLine("What row would you like to view? (input a num): "));
                startFile = Integer.parseInt(console.readLine("What col would you like to view? (input a num): "));
                validInput = true;
            } catch (Exception e) {
                System.out.println("Please input a valid input");
            }
        }
        
        if(theBoard.getPiece(new ChessPosition(startFile, startRank)) != null){
                validInput = true;
                startPosition = new ChessPosition(startFile, startRank);
            }
        else{
            System.out.println("Invalid position");
            return;
        }

        ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) game.validMoves(startPosition);
        ArrayList<ChessPosition> validPositions = new ArrayList<>();
        for (var moves : validMoves) {
            validPositions.add(moves.getEndPosition());
        }
        redrawChessBoard(theBoard, validPositions);


    }

    private static void runGame(){
        Console console = System.console();
        ArrayList<String> s = new ArrayList<>();
        s.add("help");
        s.add("leave");
        s.add("make move");
        s.add("resign");
        s.add("highlight");
        s.add("redraw");
        while(!leaveGame){
            String command = console.readLine("[GAME] >>> ");
            while(!validInput(command, s)){
                command = console.readLine("[Game] >>> ");
            }
            try {
                moveNext(command, out, 0);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    static boolean validInput(String input, ArrayList<String> s){
        return s.contains(input);
    }

    static void moveNext(String command, PrintStream out, int loggedIn) throws Exception{
        switch (command) {
            case "help":
                help(out);
                break;
            case "leave":
                leave(out);
                break;
            case "make move":
                makeMove(out, teamColor, game);
                break;
            case "resign":
                resign(out);
                break;
            case "highlight":
                highlightMoves(out);
                break;
            case "redraw":
                redrawChessBoard(theBoard);
                break;
            
        }
    }

    public static void displayNotification(String msg){
        System.out.println();
        System.out.println("Notification: " + msg);
    }

    public static void updateGame(ChessGame theGame){
        game = theGame;
        theBoard = game.getBoard();
        redrawChessBoard(theBoard);
    }

    private static String[][] drawBackgroundWhite(){
        String[][] chessBoardTerminal = new String[10][10];

        chessBoardTerminal[0][0] = SET_BG_COLOR_DARK_GREY + "   ";
        chessBoardTerminal[1][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ";
        chessBoardTerminal[2][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ";
        chessBoardTerminal[3][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ";
        chessBoardTerminal[4][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ";
        chessBoardTerminal[5][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ";
        chessBoardTerminal[6][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ";
        chessBoardTerminal[7][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ";
        chessBoardTerminal[8][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ";
        chessBoardTerminal[9][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[0][9] = SET_BG_COLOR_DARK_GREY + EMPTY.repeat(1);
        chessBoardTerminal[1][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ";
        chessBoardTerminal[2][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ";
        chessBoardTerminal[3][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ";
        chessBoardTerminal[4][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ";
        chessBoardTerminal[5][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ";
        chessBoardTerminal[6][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ";
        chessBoardTerminal[7][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ";
        chessBoardTerminal[8][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ";
        chessBoardTerminal[9][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[0][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";
        chessBoardTerminal[0][1] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " a ";
        chessBoardTerminal[0][2] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " b ";
        chessBoardTerminal[0][3] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " c ";
        chessBoardTerminal[0][4] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " d ";
        chessBoardTerminal[0][5] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " e ";
        chessBoardTerminal[0][6] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " f ";
        chessBoardTerminal[0][7] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " g ";
        chessBoardTerminal[0][8] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " h ";
        chessBoardTerminal[0][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[9][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";
        chessBoardTerminal[9][1] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " a ";
        chessBoardTerminal[9][2] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " b ";
        chessBoardTerminal[9][3] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " c ";
        chessBoardTerminal[9][4] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " d ";
        chessBoardTerminal[9][5] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " e ";
        chessBoardTerminal[9][6] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " f ";
        chessBoardTerminal[9][7] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " g ";
        chessBoardTerminal[9][8] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " h ";
        chessBoardTerminal[9][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[1][1] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[1][2] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[1][3] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[1][4] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[1][5] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[1][6] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[1][7] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[1][8] = SET_BG_COLOR_BLACK;

        chessBoardTerminal[2][1] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[2][2] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[2][3] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[2][4] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[2][5] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[2][6] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[2][7] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[2][8] = SET_BG_COLOR_WHITE;


        //Red
        chessBoardTerminal[7][1] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[7][2] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[7][3] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[7][4] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[7][5] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[7][6] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[7][7] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[7][8] = SET_BG_COLOR_BLACK;


        chessBoardTerminal[8][1] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[8][2] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[8][3] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[8][4] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[8][5] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[8][6] = SET_BG_COLOR_WHITE;
        chessBoardTerminal[8][7] = SET_BG_COLOR_BLACK;
        chessBoardTerminal[8][8] = SET_BG_COLOR_WHITE;


        for(int i = 1; i<=8; i++){
            if(i % 2 == 1){
                chessBoardTerminal[4][i] = SET_BG_COLOR_BLACK;
                chessBoardTerminal[6][i] = SET_BG_COLOR_BLACK;
            }
            else{
                chessBoardTerminal[4][i] = SET_BG_COLOR_WHITE;
                chessBoardTerminal[6][i] = SET_BG_COLOR_WHITE;
            }
        }
        for(int i = 1; i<=8; i++){
            if(i % 2 == 1){
                chessBoardTerminal[5][i] = SET_BG_COLOR_WHITE;
                chessBoardTerminal[3][i] = SET_BG_COLOR_WHITE;
            }
            else{
                chessBoardTerminal[5][i] = SET_BG_COLOR_BLACK;
                chessBoardTerminal[3][i] = SET_BG_COLOR_BLACK;
            }
        }

        return chessBoardTerminal; 
    }

    private static String[][] drawBackgroundBlack(){
        String[][] chessBoardTerminal = new String[10][10];
        
        chessBoardTerminal[0][0] = SET_BG_COLOR_DARK_GREY + "   ";
        chessBoardTerminal[1][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ";
        chessBoardTerminal[2][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ";
        chessBoardTerminal[3][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ";
        chessBoardTerminal[4][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ";
        chessBoardTerminal[5][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ";
        chessBoardTerminal[6][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ";
        chessBoardTerminal[7][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ";
        chessBoardTerminal[8][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ";
        chessBoardTerminal[9][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[0][9] = SET_BG_COLOR_DARK_GREY + EMPTY.repeat(1);
        chessBoardTerminal[1][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 1 ";
        chessBoardTerminal[2][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 2 ";
        chessBoardTerminal[3][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 3 ";
        chessBoardTerminal[4][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 4 ";
        chessBoardTerminal[5][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 5 ";
        chessBoardTerminal[6][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 6 ";
        chessBoardTerminal[7][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 7 ";
        chessBoardTerminal[8][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " 8 ";
        chessBoardTerminal[9][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[0][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";
        chessBoardTerminal[0][1] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " h ";
        chessBoardTerminal[0][2] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " g ";
        chessBoardTerminal[0][3] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " f ";
        chessBoardTerminal[0][4] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " e ";
        chessBoardTerminal[0][5] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " d ";
        chessBoardTerminal[0][6] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " c ";
        chessBoardTerminal[0][7] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " b ";
        chessBoardTerminal[0][8] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " a ";
        chessBoardTerminal[0][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[9][0] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";
        chessBoardTerminal[9][1] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " h ";
        chessBoardTerminal[9][2] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " g ";
        chessBoardTerminal[9][3] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " f ";
        chessBoardTerminal[9][4] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " e ";
        chessBoardTerminal[9][5] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " d ";
        chessBoardTerminal[9][6] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " c ";
        chessBoardTerminal[9][7] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " b ";
        chessBoardTerminal[9][8] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + " a ";
        chessBoardTerminal[9][9] = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + "   ";

        chessBoardTerminal[8][1] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][2] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][3] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][4] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][5] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][6] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][7] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[8][8] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;

        chessBoardTerminal[7][1] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][2] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][3] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][4] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][5] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][6] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][7] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE;
        chessBoardTerminal[7][8] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE;


        //Red
        chessBoardTerminal[2][1] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][2] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][3] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][4] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][5] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][6] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][7] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[2][8] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;


        chessBoardTerminal[1][1] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][2] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][3] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][4] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][5] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][6] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][7] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED;
        chessBoardTerminal[1][8] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED;


        for(int i = 1; i<=8; i++){
            if(i % 2 == 1){
                chessBoardTerminal[4][i] = SET_BG_COLOR_BLACK;
                chessBoardTerminal[6][i] = SET_BG_COLOR_BLACK;
            }
            else{
                chessBoardTerminal[4][i] = SET_BG_COLOR_WHITE;
                chessBoardTerminal[6][i] = SET_BG_COLOR_WHITE;
            }
        }
        for(int i = 1; i<=8; i++){
            if(i % 2 == 1){
                chessBoardTerminal[5][i] = SET_BG_COLOR_WHITE;
                chessBoardTerminal[3][i] = SET_BG_COLOR_WHITE;
            }
            else{
                chessBoardTerminal[5][i] = SET_BG_COLOR_BLACK;
                chessBoardTerminal[3][i] = SET_BG_COLOR_BLACK;
            }
        }

        return chessBoardTerminal;
    }
}
