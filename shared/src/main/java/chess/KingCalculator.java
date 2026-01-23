package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Top right
        if(inBounds(board, myPosition, color, 1, 1) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
        }
        
        //Up
        if(inBounds(board, myPosition, color, 1, 0) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
        }
        //Top left
        if(inBounds(board, myPosition, color, 1, -1) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
        }

        //Left
        if(inBounds(board, myPosition, color, 0, 1) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), null));
        }

        //Bottom left
        if(inBounds(board, myPosition, color, -1, -1) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
        }
        
        
        //Bottom middle
        if(inBounds(board, myPosition, color, -1, 0) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
        }
        
        
        //Bottom right
        if(inBounds(board, myPosition, color, -1, 1) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
        }

        //Right
        if(inBounds(board, myPosition, color, 0, 1) && new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), board, color)){
            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), null));
        }

        
        return moves; 
    }
//ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() + 1 <= 8)
    boolean inBounds(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color, int x, int y){
        //if it is in bounds
        if(((myPosition.getRow() + y <= 8) && (myPosition.getColumn() + x <= 8)) && (myPosition.getRow() - y >= 1) && (myPosition.getColumn() + x >= 1)){
            return true;
        }
        else{
            return false;
        }
        
    }
}
