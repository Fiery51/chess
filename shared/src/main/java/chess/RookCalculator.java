package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    ChessPosition initialPosition; 
    ChessGame.TeamColor pieceColor;
    ChessBoard theBoard; 
    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        //return List.of(new ChessMove(myPosition, endPositions(myPosition), null));
        initialPosition = myPosition; 
        pieceColor = color; 
        theBoard = board;
        endPositions(myPosition,"N");
        endPositions(myPosition,"S");
        endPositions(myPosition,"W");
        endPositions(myPosition,"E");
        return moves; 
    }



    
    private ChessPosition endPositions(ChessPosition chessPosition, String direction){
        if((direction == "N") && (chessPosition.getRow() + 1 <= 8)){
            //up
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn()), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn())) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn())).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn())) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn())).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn()), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn()), null));
            endPositions(new ChessPosition(chessPosition.getRow() + 1, chessPosition.getColumn()), "N");
        }
        if((direction == "S") && (chessPosition.getRow() - 1 >= 1)){
            //Down
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn()), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn())) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn())).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn())) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn())).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn()), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn()), null));
            endPositions(new ChessPosition(chessPosition.getRow() - 1, chessPosition.getColumn()), "S");
        }

        if((direction == "W") && (chessPosition.getColumn() - 1 >= 1)){
            //left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() - 1), "W");
        }
        if((direction == "E") && (chessPosition.getColumn() + 1 <= 8)){
            //Right
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1), null))){
                return chessPosition; 
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1)).pieceColor == pieceColor)){
                //STOP don't keep going
                //moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            if((theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1)) != null)
                    && (theBoard.getPiece(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1)).pieceColor != pieceColor)){
                //MOVE ONE MORE BUT STOP THERE
                moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1), null));
                return chessPosition;
            }

            moves.add(new ChessMove(initialPosition, new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(chessPosition.getRow(), chessPosition.getColumn() + 1), "E");
        }
        return chessPosition;
    }
}
