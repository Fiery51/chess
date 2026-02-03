package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor currentTeamTurn;
    ChessBoard currentChessBoard; 

    public ChessGame() {
        //White always goes first, therefor when the custructor is called, that means its being created i assume? So like white goes first. 
        //CHECK THIS IF YOU GET ERRORS I GUESS????????????????????????????????????????????
        currentTeamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeamTurn; 
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTeamTurn = team; 
        setBoard(currentChessBoard);
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //Grab the chess piece currently at whatever spot is at startPosition

        //from the current board, we want to get the piece at the start position, and return all valid moves that piece can make just for right now 
        //if(currentChessBoard.getPiece(startPosition) != null){
            return getBoard().getPiece(startPosition).pieceMoves(currentChessBoard, startPosition);
        //}
        //else{
        //    return null;
        //}
        //return new ArrayList<ChessMove>();
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        


        
        //If white just moved, set it to black, if black just moved set it to white
        if(currentTeamTurn == TeamColor.WHITE) setTeamTurn(TeamColor.BLACK);
        else setTeamTurn(TeamColor.WHITE);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //Overall layout of the function
        //First check if the position is empty, if so skip it

        //Second if it isn't lets grab that piece's team color. If it is NOT EQUAL to teamColor, and it is NOT a king, then run its pieceMoves function and add that to an arraylist we're making

        //Third keep doing above (somehow work out the pawns diagonal, might just have to manually code that in? *sigh*)

        //Fourth, now grab our current teamColors kings position (IF WE FOUND IT KEEP TRACK OF IT), and see if that position is IN that arraylist that we we made of all those moves of the opposite team
        //IF YES, return true. If NO return false






        //Idea:
        //Lets just loop through every single piece on the board. Top to bottom, left to right
        //Then i think we use the chess piece java file, and we can grab every single valid move that those pieces can make
        //and create one GIANT list of those. Then we can see if the kings position is in that list? If so return true, else return false




//________________________________________________
        //OHHH wait, probably make ALLL of that portion, and that overall layout a function.
        //We'll need to do the same thing for isInCheckmate. But for isInCheck that means they can move out of check,
        //so we need to also run this "is this square safe" check on the surrounding squares of the king, so see if the king
        //can move to any of the surrounding squares. If not we know "hey the king can't move to any of the surrounding squares"
        //Then we should probably see "hey can any of the teamate pieces block the attacking piece(s)." Gah holy smokes.
        //Hmmm this isn't all done in "isInCheck", this is like the entire validMoves logic lmao. But this is why cloning boards is important
        


        //OKAY so seperation of duties, look into this!!



        return true; 
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        
        return true; 
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return true; 
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentChessBoard = new ChessBoard(); 
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentChessBoard; 
    }
}
