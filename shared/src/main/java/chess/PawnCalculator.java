package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Check if square in front is null so we can move. Doesn't matter if we run off because we'll be promoted by the time we reach the end of the board
        //If we're white we're travelling up the board
        if(color == ChessGame.TeamColor.WHITE){
            //if we're on the starting square, AND the spot 2 above us is free also add that to the list
            if(myPosition.getRow() == 2){
                if(board.getPiece(new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn())) == null && board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn())) == null){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()), null));
                }
            }


            if (board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn())) == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            }

            //Capturing
            //Capture right
            if((board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1)) != null) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
            }

            //Capture left
            if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1)) != null && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
            }

        }




        //BLACK
        //else we're travelling down the board
        else{
            if(myPosition.getRow() == 7){
                if(board.getPiece(new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn())) == null && board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn())) == null){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()), null));
                }
            }
            if (board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn())) == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            }
        }
        
        return moves;
    }
}
