package client;

import chess.*;
import java.io.PrintStream;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


import static ui.EscapeSequences.*;
import java.io.Console;
import java.io.IOException;


public class ClientMain {


    static String command;
    private static String authToken;

    public static void main(String[] args) throws IOException, InterruptedException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);


        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        //drawHeaders(out);
        loggedOut(out);
    }


    private static void loggedOut(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();

        command = console.readLine("[LOGGED_OUT] >>> ");
        ArrayList<String> s = new ArrayList<>();
        s.add("register");
        s.add("login");
        s.add("quit");
        s.add("help");
        
        while(!validInput(command, s)){
            command = console.readLine("[LOGGED_OUT] >>> ");
        }
        try {
            moveNext(command, out, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void register(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Register");
        String username = console.readLine("Enter username: ");
        while(username.equals("")){
            username = console.readLine("Please enter a username: ");
        }
        String password = console.readLine("Enter password: ");
        while(password.equals("")){
            password = console.readLine("Please enter a password: ");
        }
        String email = console.readLine("Enter email: ");
        while(email.equals("")){
            email = console.readLine("Please enter an email: ");
        }
        var result = new ServerFacade(8080).registerRequest(username, password, email);

        int statusCode = Integer.parseInt(result[1 ]);

        codesLoggedOut(out, statusCode, result[0]);
    }

    private static void login(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Login");
        String username = console.readLine("Enter username: ");
        while(username.equals("")){
            username = console.readLine("Please enter a username: ");
        }
        String password = console.readLine("Enter password: ");
        while(password.equals("")){
            password = console.readLine("Please enter a password: ");
        }
        var result = new ServerFacade(8080).loginRequest(username, password);
        int statusCode = Integer.parseInt(result[1]);
        codesLoggedOut(out, statusCode, result[0]);
    }

    private static void quit(PrintStream out){
        out.print(ERASE_SCREEN);
    }

    private static void helpLogout(PrintStream out) throws IOException, InterruptedException{

        out.print(ERASE_SCREEN);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("register");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to create an account");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("login");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to play chess");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("quit");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - playing chess");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("help");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - with possible commands");

        loggedOut(out);
    }


    static void moveNext(String command, PrintStream out, int loggedIn) throws Exception{
        switch (command) {
            case "register":
                register(out);
                break;
            case "login":
                login(out);
                break;
            case "quit":
                quit(out);
                break;
            case "help":
                if(loggedIn == 0){
                    helpLogout(out);
                }
                else{
                    helpLoggedIn(out);
                }
                break;

            case "logout":
                logout(out);
                break;
            case "create game":
                createGame(out);
                break;
            case "list games":
                listGames(out);
                break;
            case "play game":
                playGame(out);
                break;
            case "observe game":
                observeGame(out);
                break;
        }
    }

    
    //logged in
    private static void loggedIn(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();

        command = console.readLine("[LOGGED_IN] >>> ");
        ArrayList<String> s = new ArrayList<>();
        s.add("help");
        s.add("logout");
        s.add("create game");
        s.add("list games");
        s.add("play game");
        s.add("observe game");
        
        while(!validInput(command, s)){
            command = console.readLine("[LOGGED_IN] >>> ");
        }
        try {
            moveNext(command, out, 1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void helpLoggedIn(PrintStream out) throws IOException, InterruptedException{
        out.print(ERASE_SCREEN);
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLUE);
        out.print("create game");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to create a game");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("list games");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - list all games");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("play game");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to join a game");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("observe game");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to view a game");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("logout");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to logout");

        out.print(SET_TEXT_COLOR_BLUE);
        out.print("help");
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
        out.println(" - to view commands");

        loggedIn(out);
    }
    
    private static void logout(PrintStream out) throws IOException, InterruptedException{
        var result = new ServerFacade(8080).logoutRequest(authToken);
        if(result == 200){
            //custom 200 one for this
            loggedOut(out);
            return;
        }
        codesLoggedIn(out, result);
    }

    private static void createGame(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Create Game");
        String name = console.readLine("Enter game name: ");
        while(name.equals("")){
            name = console.readLine("Please enter a game name: ");
        }
        var result = new ServerFacade(8080).createGame(authToken, name);
        if(result[0] == 200){
            out.println("Game: " + name + " created");
            loggedIn(out);
            return;
        }
        codesLoggedIn(out, result[0]);
    }

    private static void listGames(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        var result = new ServerFacade(8080).listGames(authToken);

        if(result.statusCode() == 200){
            out.print(ERASE_SCREEN);
            out.println("Games Available:");
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
            console.readLine("Press enter to continue");
            loggedIn(out);
            return;
        }
        codesLoggedIn(out, result.statusCode());

    }

    private static void playGame(PrintStream out) throws Exception{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Play game:");
        String id = console.readLine("Game ID: ");
        while(true){
            try {
                Integer.parseInt(id);
                break;
            } catch (Exception e) {
                id = console.readLine("Please input a numeric game ID: ");
            }
        }
        String color = console.readLine("Color (WHITE) or (BLACK): ");
        if(!color.equals("WHITE") && !color.equals("BLACK")){
            color = console.readLine("Please select a color (WHITE) or (BLACK): ");
        }
        //make HTTP request to join teh game
        var result = new ServerFacade(8080).playGame(authToken, color, id); //hey ID might be null check on this 
        if(result == 200){
            if(color.equals("WHITE")){
                GameUI gameUI = new GameUI(Integer.parseInt(id), authToken);
                gameUI.playGame(out, "WHITE");
                loggedIn(out);
            }
            else{
                //drawChessBoardBlack(out);
                GameUI gameUI = new GameUI(Integer.parseInt(id), authToken);
                gameUI.playGame(out, "BLACK");
                loggedIn(out);
            }
        }
        //codesLoggedIn(out, result);
        //replace this with a websocket connection call
        //then transfer us from here over to the GameUI script's logic
    }

    private static void observeGame(PrintStream out) throws Exception{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Observe game:");
        String id = console.readLine("Game ID: ");
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            id = console.readLine("Please input a numeric game ID: ");
        }
        //make http request to view teh game
        var result = new ServerFacade(8080).observeGame(authToken, id);
        //System.out.println(result);
        //System.out.println(id);
        if(result == 200){
            //drawChessBoardWhite();
            GameUI gameUI = new GameUI(Integer.parseInt(id), authToken);
            gameUI.playGame(out, "WHITE");
        }
        codesLoggedIn(out, result);
    }

    
    static boolean validInput(String input, ArrayList<String> s){
        return s.contains(input);
    }



    public static void codesLoggedOut(PrintStream out, int code, String authToken) throws IOException, InterruptedException{
        switch (code) {
            case 200:
                ClientMain.authToken = authToken;
                loggedIn(out);
                break;
            case 400:
                out.print(ERASE_SCREEN);
                out.println("Bad request");
                loggedOut(out);
                break;

            case 401:
                out.print(ERASE_SCREEN);
                out.println("Invalid Credentials");
                loggedOut(out);
                break;

            case 403:
                out.print(ERASE_SCREEN);
                out.println("Username already taken :( ");
                loggedOut(out);
                break;

            case 500:
                out.print(ERASE_SCREEN);
                out.println("Server problem (nah doesn't happen");
                loggedOut(out);
                break;

            default:
                out.print(ERASE_SCREEN);
                out.println("Unexpected server problem, the heck happened here man, the frick you do");
                loggedOut(out);
                break;
        }
    }

    public static void codesLoggedIn(PrintStream out, int code) throws IOException, InterruptedException{
        switch (code) {
            case 200:
                loggedIn(out);
                break; 
            case 400:
                out.print(ERASE_SCREEN);
                out.println("Bad request");
                loggedIn(out);
                break;

            case 401:
                out.print(ERASE_SCREEN);
                out.println("Invalid Credentials");
                loggedIn(out);
                break;

            case 403:
                out.print(ERASE_SCREEN);
                out.println("Color already taken");
                loggedIn(out);
                break;

            case 500:
                out.print(ERASE_SCREEN);
                out.println("Server problem (nah doesn't happen)");
                loggedIn(out);
                break;

            default:
                out.print(ERASE_SCREEN);
                out.println("Unexpected server problem, the heck happened here man, the frick you do");
                loggedIn(out);
                break;
        }
    }



    public static String[][] drawChessBoardWhite(){
        //out.print(ERASE_SCREEN);

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

        //for(int i = 0; i<10; i++){
        //    out.println(chessBoardTerminal[i][0] + 
        //        chessBoardTerminal[i][1] + 
        //        chessBoardTerminal[i][2] + 
        //        chessBoardTerminal[i][3] + 
        //        chessBoardTerminal[i][4] +
        //        chessBoardTerminal[i][5] +
        //        chessBoardTerminal[i][6] +
        //        chessBoardTerminal[i][7] +
        //        chessBoardTerminal[i][8] +
        //        chessBoardTerminal[i][9]);
        //}
    }


    public static void drawChessBoardBlack(PrintStream out){
        out.print(ERASE_SCREEN);

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

        chessBoardTerminal[8][1] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " R ";
        chessBoardTerminal[8][2] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " N ";
        chessBoardTerminal[8][3] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " B ";
        chessBoardTerminal[8][4] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " K ";
        chessBoardTerminal[8][5] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " Q ";
        chessBoardTerminal[8][6] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " B ";
        chessBoardTerminal[8][7] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " N ";
        chessBoardTerminal[8][8] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " R ";

        chessBoardTerminal[7][1] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][2] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][3] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][4] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][5] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][6] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][7] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLUE + " P ";
        chessBoardTerminal[7][8] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_BLUE + " P ";


        //Red
        chessBoardTerminal[2][1] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][2] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][3] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][4] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][5] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][6] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][7] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " P ";
        chessBoardTerminal[2][8] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " P ";


        chessBoardTerminal[1][1] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " R ";
        chessBoardTerminal[1][2] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " N ";
        chessBoardTerminal[1][3] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " B ";
        chessBoardTerminal[1][4] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " K ";
        chessBoardTerminal[1][5] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " Q ";
        chessBoardTerminal[1][6] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " B ";
        chessBoardTerminal[1][7] = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_RED + " N ";
        chessBoardTerminal[1][8] = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_RED + " R ";


        for(int i = 1; i<=8; i++){
            if(i % 2 == 1){
                chessBoardTerminal[4][i] = SET_BG_COLOR_BLACK + "   ";
                chessBoardTerminal[6][i] = SET_BG_COLOR_BLACK + "   ";
            }
            else{
                chessBoardTerminal[4][i] = SET_BG_COLOR_WHITE + "   ";
                chessBoardTerminal[6][i] = SET_BG_COLOR_WHITE + "   ";
            }
        }
        for(int i = 1; i<=8; i++){
            if(i % 2 == 1){
                chessBoardTerminal[5][i] = SET_BG_COLOR_WHITE + "   ";
                chessBoardTerminal[3][i] = SET_BG_COLOR_WHITE + "   ";
            }
            else{
                chessBoardTerminal[5][i] = SET_BG_COLOR_BLACK + "   ";
                chessBoardTerminal[3][i] = SET_BG_COLOR_BLACK + "   ";
            }
        }

        for(int i = 0; i<10; i++){
            out.println(chessBoardTerminal[i][0] + 
                chessBoardTerminal[i][1] + 
                chessBoardTerminal[i][2] + 
                chessBoardTerminal[i][3] + 
                chessBoardTerminal[i][4] +
                chessBoardTerminal[i][5] +
                chessBoardTerminal[i][6] +
                chessBoardTerminal[i][7] +
                chessBoardTerminal[i][8] +
                chessBoardTerminal[i][9]);
        }
    }
}