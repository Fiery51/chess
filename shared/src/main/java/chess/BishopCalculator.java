package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    ChessPosition initialPosition; 
    ChessGame.TeamColor pieceColor;
    ChessBoard theBoard; 
    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //return List.of(new ChessMove(myPosition, endPositions(myPosition), null));
        initialPosition = myPosition; 
        pieceColor = color; 
        theBoard = board;
        endPositions(myPosition,"NE");
        endPositions(myPosition,"SE");
        endPositions(myPosition,"NW");
        endPositions(myPosition,"SW");
        return moves; 
    }



    
    
    private ChessPosition endPositions(ChessPosition ChessPosition, String direction){
        //if( !((ChessPosition.getRow() + 1 <= 8)  && (ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() <= 8) && (ChessPosition.getColumn() >= 1))){
        //    return ChessPosition; 
        //}

        if((direction == "NE") && (ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() + 1 <= 8)){
            //top right
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null))){
                return ChessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return ChessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return ChessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), "NE");
        }




        if((direction == "SE") && (ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() + 1 <= 8)){
            //bottom right
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null))){
                return ChessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return ChessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null));
                return ChessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), "SE");
        }





        if((direction == "NW") && (ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() - 1 >= 1)){
            //top left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null))){
                return ChessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return ChessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null));
                return ChessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), "NW");
        }






        if((direction == "SW") && (ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() - 1 >= 1)){
            //bottom left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null))){
                return ChessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return ChessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1)) != null) && (theBoard.getPiece(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null));
                return ChessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), "SW");
        }
        return ChessPosition;
    }
}
