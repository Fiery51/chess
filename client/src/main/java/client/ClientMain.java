package client;

import chess.*;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

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
        moveNext(command, out, 0);
    }

    private static void register(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Register");
        String username = console.readLine("Enter username: ");
        String password = console.readLine("Enter password: ");
        String email = console.readLine("Enter email: ");
        var result = new ServerFacade().registerRequest(username, password, email);

        int statusCode = Integer.parseInt(result[1]);

        codesLoggedOut(out, statusCode, result[0]);
    }

    private static void login(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Login");
        String username = console.readLine("Enter username: ");
        String password = console.readLine("Enter password: ");
        var result = new ServerFacade().loginRequest(username, password);
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


    static void moveNext(String command, PrintStream out, int loggedIn) throws IOException, InterruptedException{
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
        moveNext(command, out, 1);
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
        var result = new ServerFacade().logoutRequest(authToken);
        if(result == 200){
            //custom 200 one for this
            loggedOut(out);;
            return;
        }
        codesLoggedIn(out, result);
    }

    private static void createGame(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Create Game");
        String name = console.readLine("Enter game name: ");
        var result = new ServerFacade().createGame(authToken, name);
        if(result[0] == 200){
            out.println("Game: " + name + " created");
            loggedIn(out);
            return;
        }
        codesLoggedIn(out, result[0]);
    }

    private static void listGames(PrintStream out) throws IOException, InterruptedException{
        Console console = System.console();
        var result = new ServerFacade().listGames(authToken);

        if(result.statusCode() == 200){
            out.print(ERASE_SCREEN);
            out.println("Games Available:");
            for (var element : result.games().entrySet()) {
                out.println(element.getKey() + " " + element.getValue());
            }
            console.readLine("Press enter to continue");
            loggedIn(out);
            return;
        }
        codesLoggedIn(out, result.statusCode());

    }

    private static void playGame(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Play game:");
        String id = console.readLine("Game ID: ");
        String color = console.readLine("Color: ");
        //make HTTP request to join teh game

        drawChessBoard(out);
    }

    private static void observeGame(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Observe game:");
        String id = console.readLine("Game ID: ");
        //make http request to view teh game
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
                out.println("Server problem (nah doesn't happen, trust trust im a perfect software dev you should never see thi- why are you seeing this)");
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
                out.println("Server problem (nah doesn't happen, trust trust im a perfect software dev you should never see thi- why are you seeing this)");
                loggedIn(out);
                break;

            default:
                out.print(ERASE_SCREEN);
                out.println("Unexpected server problem, the heck happened here man, the frick you do");
                loggedIn(out);
                break;
        }
    }



    public static void drawChessBoard(PrintStream out){
        out.print(ERASE_SCREEN);


        for(int i=0; i<8; i++){
            for(int j=0; j < 8; j++){
                if(i + j % 2 == 0){
                    out.print(SET_BG_COLOR_LIGHT_GREY);
                }
                else{
                    out.print(SET_BG_COLOR_DARK_GREEN);
                }
            }
            out.println(EMPTY.repeat(3));
        }
    }
}







//HEY THIS ISN"T NEARLY AS COMPLICATED AS YOU THINK IT IS!!!

//WHEN YOU PRESS PLAY
//SHOW A MENU
//menu should have options to: join game, create game, etc.

//ONLY THEN when they press on an option
//send an http request
//once we get a response back then update the UI

//make sure that each request has a time out response 
//(i don't think you need to connect to the server at first, just timeout each request after x seconds)

