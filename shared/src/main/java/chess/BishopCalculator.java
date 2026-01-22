package chess;
import java.util.Collection;
import java.util.List;

public class BishopCalculator {
    //static return all moves possible by bishop
    public static Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition){
        return List.of(new ChessMove(myPosition, endPositions(myPosition), null));
    }

    private static ChessPosition endPositions(ChessPosition ChessPosition){
        //top right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() + 1 <= 8)){
            return endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        //top left
        if((ChessPosition.getRow() - 1 <= 1) && (ChessPosition.getColumn() + 1 >= 8)){
            return endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1));
        }
        //bottom right
        if((ChessPosition.getRow() + 1 >= 8) && (ChessPosition.getColumn() - 1 <= 8)){
            return endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        //bottom left
        if((ChessPosition.getRow() - 1 >= 8) && (ChessPosition.getColumn() - 1 >= 8)){
            return endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        //itself
        return ChessPosition; 
    } 
}
