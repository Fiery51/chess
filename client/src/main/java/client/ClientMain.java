package client;

import chess.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import static ui.EscapeSequences.*;
import java.io.Console;


public class ClientMain {


    //DELETE THIS STUFF

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 3;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 3;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    // Padded characters.
    private static final String EMPTY = "   ";
    private static final String X = " X ";
    private static final String O = " O ";

    private static Random rand = new Random();

    static String command;




    //__________________________________________








    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);



        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        //drawHeaders(out);
        loggedOut(out);
    }


    private static void loggedOut(PrintStream out){
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

    private static void register(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Register");
        String username = console.readLine("Enter username: ");
        String password = console.readLine("Enter password: ");
        String email = console.readLine("Enter email: ");
        
        //make HTTP request to rester a user
        loggedIn(out);
    }

    private static void login(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Login");
        String username = console.readLine("Enter username: ");
        String password = console.readLine("Enter password: ");

        //send http request do all that jazz. For now just pretend we logged in to test functionality
        loggedIn(out);
    }

    private static void quit(PrintStream out){
        out.print(ERASE_SCREEN);
    }

    private static void helpLogout(PrintStream out){
        Console console = System.console();
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


    static void moveNext(String command, PrintStream out, int loggedIn){
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
    private static void loggedIn(PrintStream out){
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

    private static void helpLoggedIn(PrintStream out){
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
    
    private static void logout(PrintStream out){
        //send HTTP request to logout
        loggedOut(out);
    }

    private static void createGame(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Create Game");
        String name = console.readLine("Enter game name: ");
        //send HTTP request to create the game
        loggedIn(out);
    }

    private static void listGames(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Games Available:");
        //list all the games here. Make HTTP request to list all the games and well, list them all out
        console.readLine("Press enter to continue");
        loggedIn(out);
    }

    private static void playGame(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Play game:");
        String id = console.readLine("Game ID: ");
        String color = console.readLine("Color: ");
        //make HTTP request to join teh game
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

