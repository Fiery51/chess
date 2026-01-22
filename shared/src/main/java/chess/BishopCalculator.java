package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    ChessPosition initialPosition; 

    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition){
        //return List.of(new ChessMove(myPosition, endPositions(myPosition), null));
        initialPosition = myPosition; 
        endPositions(myPosition);
        return moves; 
    }

    private ChessPosition endPositions(ChessPosition ChessPosition){
        if( !((ChessPosition.getRow() + 1 <= 8)  && (ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() <= 8) && (ChessPosition.getColumn() >= 1))){
            return ChessPosition; 
        }


        //top right
        if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null))){
            return ChessPosition; 
        }
        //top left
        if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null))){
            return ChessPosition; 
        }
        //bottom right 
        if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null))){
            return ChessPosition; 
        }
        //bottom left
        if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null))){
            return ChessPosition; 
        }



        //top right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() + 1 <= 8)){
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        //top left
        if((ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() + 1 <= 8)){
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1));
        }
        //bottom right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() - 1 >= 1)){
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1));
        }
        //bottom left
        if((ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() - 1 >= 1)){
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1));
        }
        //itself
        return ChessPosition; 
    } 
}
