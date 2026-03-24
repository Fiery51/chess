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



        //DELETE THIS
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        //drawHeaders(out);
        loggedOut(out);
        //_______________
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
        moveNext(command, out);
    }

    private static void register(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Register");
        String username = console.readLine("Enter username: ");
        String password = console.readLine("Enter password: ");
        String email = console.readLine("Enter email: ");
        
    }

    private static void login(PrintStream out){
        Console console = System.console();
        out.print(ERASE_SCREEN);
        out.println("Login");
        String username = console.readLine("Enter username: ");
        String password = console.readLine("Enter password: ");

    }

    private static void quit(PrintStream out){
        out.print(ERASE_SCREEN);
    }

    private static void help(PrintStream out){
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

        command = console.readLine("[LOGGED_OUT] >>> ");
        ArrayList<String> s = new ArrayList<>();
        s.add("register");
        s.add("login");
        s.add("quit");
        s.add("help");
        while(!validInput(command, s)){
            command = console.readLine("[LOGGED_OUT] >>> ");
        }
        moveNext(command, out);
    }


    static void moveNext(String command, PrintStream out){
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
                help(out);
                break;
        }
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

