package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Top right
        if((myPosition.getRow() >= 1) && (myPosition.getRow() + 1 <= 8) && (myPosition.getColumn() >= 1) && (myPosition.getColumn() + 1 <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
            }
        }
        
        //Up
        if((myPosition.getRow() >= 1) && (myPosition.getRow() + 1 <= 8) && (myPosition.getColumn() >= 1) && (myPosition.getColumn() <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
            }
        }
        //Top left
        if((myPosition.getRow() >= 1) && (myPosition.getRow() + 1 <= 8) && (myPosition.getColumn() - 1 >= 1) && (myPosition.getColumn() <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
            }
        }

        //Left
        if((myPosition.getRow() >= 1) && (myPosition.getRow() <= 8) && (myPosition.getColumn() - 1 >= 1) && (myPosition.getColumn() <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() - 1), null));
            }
        }

        //Bottom left
        if((myPosition.getRow() - 1 >= 1) && (myPosition.getRow() <= 8) && (myPosition.getColumn() - 1 >= 1) && (myPosition.getColumn() <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
            }
        }
        
        
        //Bottom middle
        if((myPosition.getRow() - 1 >= 1) && (myPosition.getRow() <= 8) && (myPosition.getColumn() >= 1) && (myPosition.getColumn() <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
            }
        }
        
        
        //Bottom right
        if((myPosition.getRow() - 1 >= 1) && (myPosition.getRow() <= 8) && (myPosition.getColumn() >= 1) && (myPosition.getColumn() + 1 <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
            }
        }

        //Right
        if((myPosition.getRow() >= 1) && (myPosition.getRow() <= 8) && (myPosition.getColumn() >= 1) && (myPosition.getColumn() + 1 <= 8)){
            if(new KnightCalculator().checkTeamate(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn() + 1), null));
            }
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
