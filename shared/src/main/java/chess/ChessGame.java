package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor currentTeamTurn;
    ChessBoard currentChessBoard; 
    ChessPosition theStartPosition; 
    //public ChessPosition kingPosition = new ChessPosition(0, 0);  

    public ChessGame() {
        //White always goes first, therefor when the custructor is called, that means its being created i assume? So like white goes first. 
        //CHECK THIS IF YOU GET ERRORS I GUESS????????????????????????????????????????????
        currentTeamTurn = TeamColor.WHITE;
        ChessBoard newBoard = new ChessBoard();
        newBoard.resetBoard();
        setBoard(newBoard);
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
        //Do we even have a piece on the position we're on?
        theStartPosition = startPosition; 
        if(currentChessBoard.getPiece(startPosition) == null) {
            return null;
        }
        //First lets get all the moves we can possibly make period so we have something to mess around with
        ArrayList<ChessMove> moves = new ArrayList<>();
        ArrayList<ChessMove> theValidMoves = new ArrayList<>();
        moves.addAll(getBoard().getPiece(startPosition).pieceMoves(currentChessBoard, startPosition));
        //Next loop through all the possible moves
        for(ChessMove movesVar : moves){
            //make a copy of the chessboard;
            ChessBoard copyBoard = new ChessBoard(currentChessBoard);
            //apply the move on the copy of the chessboard
            //first grab the chesspiece at the position
            ChessPiece currentPiece = copyBoard.getPiece(startPosition);
            //Next lets delete the piece
            copyBoard.addPiece(startPosition, null);
            //Then actually move the piece
            copyBoard.addPiece(movesVar.getEndPosition(), currentPiece);
            //then check if the king is in check
            if (isInCheck(currentChessBoard.getPiece(startPosition).getTeamColor(), copyBoard) == false) {
                //if we're not in check after that move add that move to the valid moves array we made
                theValidMoves.add(movesVar);
            }
        }

        return theValidMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ArrayList<ChessMove> moves = new ArrayList<>();
        moves.addAll(validMoves(theStartPosition));
        if(!moves.contains(move)){
            throw new InvalidMoveException("reason");
        }
        //make a copy of the piece
        ChessPiece copyOfPiece = currentChessBoard.getPiece(move.getStartPosition()); 
        //delete the piece
        currentChessBoard.addPiece(move.getStartPosition(), null);
        //add the new piece at the end
        currentChessBoard.addPiece(move.getEndPosition(), copyOfPiece);

        
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
    //For this one we should only care if the king is being hit or not
    public boolean isInCheck(TeamColor teamColor) {
        ArrayList<ChessMove> movesList = new ArrayList<>();
        ArrayList<ChessPosition> endPositions = new ArrayList<>(); 
        ChessPosition kingPosition = new ChessPosition(0, 0);  
        //First loop over every single position in the board to grab all the pieces of opposite color/king of teamColor
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                //If the current position isn't null
                if(currentChessBoard.getPiece(new ChessPosition(i, j)) != null){
                    //Check what color the piece is
                    //IF IT'S THE OPPOSITE COLOR RUN CHESS PIECE pieceMOVES and add it to movesList for later processing
                    if(currentChessBoard.getPiece(new ChessPosition(i, j)).getTeamColor() != teamColor){
                        movesList.addAll(currentChessBoard.getPiece(new ChessPosition(i, j)).pieceMoves(currentChessBoard, new ChessPosition(i, j)));
                    }
                    //IF IT'S THE SAME COLOR
                    else{
                        //IF ITS EQUAL TO A KING
                        if(currentChessBoard.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                            //then save it for later so we know where it is
                            kingPosition = new ChessPosition(i, j);
                        }
                    }
                }
            }
        }
            //Okay after we've looped through, in THEORY we should have where all the opposite pieces are aiming
            //that means we should just be able to call a simple contains method
            //First extract all the individual end positions to the array list 
            for (ChessMove move : movesList) {
                endPositions.add(move.getEndPosition());
            }
            return endPositions.contains(kingPosition);
    }

    public boolean isInCheck(TeamColor teamColor, ChessPosition newKingPosition) {
        ArrayList<ChessMove> movesList = new ArrayList<>();
        ArrayList<ChessPosition> endPositions = new ArrayList<>();
        ChessPosition kingPosition = newKingPosition;
        //First loop over every single position in the board to grab all the pieces of opposite color/king of teamColor
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                //If the current position isn't null
                if(currentChessBoard.getPiece(new ChessPosition(i, j)) != null){
                    //Check what color the piece is
                    //IF IT'S THE OPPOSITE COLOR RUN CHESS PIECE pieceMOVES and add it to movesList for later processing
                    if(currentChessBoard.getPiece(new ChessPosition(i, j)).getTeamColor() != teamColor){
                        movesList.addAll(currentChessBoard.getPiece(new ChessPosition(i, j)).pieceMoves(currentChessBoard, new ChessPosition(i, j)));
                    }
                }
            }
        }
            //Okay after we've looped through, in THEORY we should have where all the opposite pieces are aiming
            //that means we should just be able to call a simple contains method
            //First extract all the individual end positions to the array list 
            for (ChessMove move : movesList) {
                endPositions.add(move.getEndPosition());
            }
            return endPositions.contains(kingPosition);
    }

    public boolean isInCheck(TeamColor teamColor, ChessBoard board) {
        ArrayList<ChessMove> movesList = new ArrayList<>();
        ArrayList<ChessPosition> endPositions = new ArrayList<>(); 
        ChessPosition kingPosition = new ChessPosition(0, 0);  
        //First loop over every single position in the board to grab all the pieces of opposite color/king of teamColor
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition thePosition = new ChessPosition(i, j);
                ChessPiece atPos = board.getPiece(thePosition);
                //If the current position isn't null
                if(atPos == null){
                    continue;
                }
                if(atPos.getTeamColor() == teamColor) {
                    if(atPos.getPieceType() == ChessPiece.PieceType.KING){
                        //then save it for later so we know where it is
                        kingPosition = thePosition;
                    }
                    continue;
                }
                //Check what color the piece is
                //IF IT'S THE OPPOSITE COLOR RUN CHESS PIECE pieceMOVES and add it to movesList for later processing
                movesList.addAll(atPos.pieceMoves(board, thePosition));
            }
        }
            //Okay after we've looped through, in THEORY we should have where all the opposite pieces are aiming
            //that means we should just be able to call a simple contains method
            //First extract all the individual end positions to the array list 
            for (ChessMove move : movesList) {
                endPositions.add(move.getEndPosition());
            }
            return endPositions.contains(kingPosition);
    }







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




    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //Grab all the current team colors allowed moves! Same logic as isInCheck but for the SAME team
        ArrayList<ChessMove> AllTeamMoves = new ArrayList<>();
        ArrayList<ChessMove> KingMoves = new ArrayList<>();
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition thePosition = new ChessPosition(i, j);
                //If the current position isn't null
                if(currentChessBoard.getPiece(new ChessPosition(i, j)) != null){
                    //Check what color the piece is
                    //If its the SAME color, save it to AllTeamMoves, AND its not the King piece because we already did that above!!
                    if(currentChessBoard.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && currentChessBoard.getPiece(new ChessPosition(i, j)).getPieceType() != ChessPiece.PieceType.KING){
                        //AllTeamMoves.addAll(currentChessBoard.getPiece(new ChessPosition(i, j)).pieceMoves(currentChessBoard, new ChessPosition(i, j)));
                        AllTeamMoves.addAll(validMoves(thePosition));
                    }
                    //If the position is the SAME color and it IS our king
                    //Grab the moves the king can make
                    if(currentChessBoard.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && currentChessBoard.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                        //KingMoves.addAll(currentChessBoard.getPiece(new ChessPosition(i, j)).pieceMoves(currentChessBoard, new ChessPosition(i, j))); 
                        KingMoves.addAll(validMoves(thePosition));
                    }
                }
            }
        }


        //First ensure we're in check (use the current king position)
        if(isInCheck(teamColor)){
            //Now we know that we're being hit, can the king move out of the way?
            //We know we're already in check, so first run isInCheck on all of the king's moves to see if we can move out of the way (overloaded method)
            for (ChessMove move : KingMoves) {
                //pass in all the end position (starting position is always that middle square)
                ArrayList<Boolean> canMoveOutOfWay = new ArrayList<>();
                canMoveOutOfWay.add(isInCheck(teamColor, move.getEndPosition()));
                //If theres a single false that means the king CAN move out of the way. Return false
                if(canMoveOutOfWay.contains(false)) return false;
            }
            //Now that we know the king can't move out of the way:
            //Next check if validmoves for every piece on same team is empty assume checkmate (king can't move, no pieces can move)
            //if you have no legal moves, that means we're in check, the king can't move out of the way, and you can't block it/take it
            //if you have legal moves means its not checkmate
            return AllTeamMoves.isEmpty();
        }




        //If your not even in check, that means your safe, return false. Your not even under attack
        else{
            return false; 
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //Grab all the current team colors allowed moves! Same logic as isInCheck but for the SAME team
        ArrayList<ChessMove> AllTeamMoves = new ArrayList<>();
        ArrayList<ChessMove> KingMoves = new ArrayList<>();
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                ChessPosition thePosition = new ChessPosition(i, j);
                //If the current position isn't null
                if(currentChessBoard.getPiece(new ChessPosition(i, j)) != null){
                    //Check what color the piece is
                    //If its the SAME color, save it to AllTeamMoves, AND its not the King piece because we already did that above!!
                    if(currentChessBoard.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && currentChessBoard.getPiece(new ChessPosition(i, j)).getPieceType() != ChessPiece.PieceType.KING){
                        //AllTeamMoves.addAll(currentChessBoard.getPiece(new ChessPosition(i, j)).pieceMoves(currentChessBoard, new ChessPosition(i, j)));
                        AllTeamMoves.addAll(validMoves(thePosition));
                    }
                    //If the position is the SAME color and it IS our king
                    //Grab the moves the king can make
                    if(currentChessBoard.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && currentChessBoard.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                        //KingMoves.addAll(currentChessBoard.getPiece(new ChessPosition(i, j)).pieceMoves(currentChessBoard, new ChessPosition(i, j))); 
                        KingMoves.addAll(validMoves(thePosition));
                    }
                }
            }
        }


        //First ensure we're in check (use the current king position)
        if(!isInCheck(teamColor)){
            //Now we know that we're being hit, can the king move out of the way?
            //We know we're already in check, so first run isInCheck on all of the king's moves to see if we can move out of the way (overloaded method)
            for (ChessMove move : KingMoves) {
                //pass in all the end position (starting position is always that middle square)
                ArrayList<Boolean> canMoveOutOfWay = new ArrayList<>();
                canMoveOutOfWay.add(isInCheck(teamColor, move.getEndPosition()));
                //If theres a single false that means the king CAN move out of the way. Return false
                if(canMoveOutOfWay.contains(false)) return false;
            }
            //Now that we know the king can't move out of the way:
            //Next check if validmoves for every piece on same team is empty assume checkmate (king can't move, no pieces can move)
            //if you have no legal moves, that means we're in check, the king can't move out of the way, and you can't block it/take it
            //if you have legal moves means its not checkmate
            return AllTeamMoves.isEmpty();
        }




        //If your not even in check, that means your safe, return false. Your not even under attack
        else{
            return false; 
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentChessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentChessBoard; 
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "currentTeamTurn=" + currentTeamTurn +
                ", currentChessBoard=" + currentChessBoard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return currentTeamTurn == chessGame.currentTeamTurn && Objects.equals(currentChessBoard, chessGame.currentChessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTeamTurn, currentChessBoard);
    }


}