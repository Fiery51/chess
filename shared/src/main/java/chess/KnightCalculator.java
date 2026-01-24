package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){

        //top right
        addTopRight(board, myPosition, color);

        //top left
        addTopLeft(board, myPosition, color);
        


        //bottom right
        addBottomRight(board, myPosition, color);



        //bottom left
        addBottomLeft(board, myPosition, color);


        return moves; 
    }

    void addTopRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //Check if the top right square is in bounds
        if((myPosition.getRow() + 2 <= 8) && (myPosition.getColumn() + 1) <= 8){
            if(checkTeamate(new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() + 1), null));
            }
        }
        //lower right square in top right quadrant
        if((myPosition.getRow() + 1 <= 8) && (myPosition.getColumn() + 2) <= 8){
            if(checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 2), null));
            }
        }
    }

    void addTopLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        
        if((myPosition.getRow() + 2 <= 8) && (myPosition.getColumn() - 1) >= 1){
            if(checkTeamate(new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn() - 1), null));
            }
        }

        if((myPosition.getRow() + 1 <= 8) && (myPosition.getColumn() - 2) >= 1){
            if(checkTeamate(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 2), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 2), null));
            }
        }
    }

    void addBottomRight(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        
        if((myPosition.getRow() - 2 >= 1) && (myPosition.getColumn() + 1) <= 8){
            if(checkTeamate(new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() + 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() + 1), null));
            }
        }
        
        if((myPosition.getRow() - 1 >= 1) && (myPosition.getColumn() + 2) <= 8){
            if(checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 2), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 2), null));
            }
        }
    }

    void addBottomLeft(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        
        if((myPosition.getRow() - 2 >= 1) && (myPosition.getColumn() - 1) >= 1){
            if(checkTeamate(new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() - 1), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn() - 1), null));
            }
        }
        
        if((myPosition.getRow() - 1 >= 1) && (myPosition.getColumn() - 2) >= 1){
            if(checkTeamate(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 2), board, color)){
                moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 2), null));
            }
        }
    }

    boolean checkTeamate(ChessPosition position, ChessBoard board, ChessGame.TeamColor color){
        //If it equals null, theres no piece, your good to move there!
        if(board.getPiece(position) == null){
            return true; 
        }
        else{
            //if it equals our color we cannot move there
            if(board.getPiece(position).getTeamColor() == color){
                return false; 
            }
            //if it doesn't equal our color, take da piece
            else{
                return true;
            }
        }
    }
}