package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition){
        //return List.of(new ChessMove(myPosition, endPositions(myPosition), null));
        endPositions(myPosition);
        return moves; 
    }

    private ChessPosition endPositions(ChessPosition ChessPosition){
        //top right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() + 1 <= 8)){
            moves.add(new ChessMove(ChessPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
            return endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        ////top left
        if((ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() + 1 <= 8)){
            moves.add(new ChessMove(ChessPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null));
            return endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1));
        }
        ////bottom right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() - 1 >= 1)){
            moves.add(new ChessMove(ChessPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null));
            return endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        ////bottom left
        if((ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() - 1 >= 1)){
            moves.add(new ChessMove(ChessPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null));
            return endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        //itself
        return ChessPosition; 
    } 
}
