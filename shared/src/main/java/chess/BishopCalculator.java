package chess;
import java.util.ArrayList;
import java.util.Collection;

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



    
    
    private ChessPosition endPositions(ChessPosition chessPosition, String direction){


        if((direction == "NE") && (chessPosition.getRow() + 1 <= 8) && (chessPosition.getColumn() + 1 <= 8)){
            //top right
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), "NE");
        }




        if((direction == "SE") && (chessPosition.getRow() - 1 >= 1) && (chessPosition.getColumn() + 1 <= 8)){
            //bottom right
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() + 1), "SE");
        }





        if((direction == "NW") && (chessPosition.getRow() + 1 <= 8) && (chessPosition.getColumn() - 1 >= 1)){
            //top left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() - 1), "NW");
        }






        if((direction == "SW") && (chessPosition.getRow() - 1 >= 1) && (chessPosition.getColumn() - 1 >= 1)){
            //bottom left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn() - 1), "SW");
        }
        return chessPosition;
    }
}
