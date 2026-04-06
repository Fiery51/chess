package client;

import java.io.PrintStream;

import javax.print.PrintService;

import static ui.EscapeSequences.*;

public class GameUI {
    PrintStream out;

    public GameUI(PrintStream out){
        this.out = out;
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
