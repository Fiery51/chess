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

            //normal moves
            if (board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn())) == null) {
                //if we're moving to the back rank and need to promote
                if(myPosition.getRow() + 1 == 8){
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), ChessPiece.PieceType.QUEEN));
                }
                else{
                    moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                }
            }

            //Capturing
            //if your on the left edge ONLY run the capture right portion
            if(myPosition.getColumn() == 1){
                //capture right
                if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1)).getTeamColor() != color){
                        if(myPosition.getRow() + 1 == 8){
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                        }
                    }
                }
            }
            //if your on the right edge ONLY right the capture left portion
            else if(myPosition.getColumn() == 8){
                //Capture left
                if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1)).getTeamColor() != color){
                        if(myPosition.getRow() + 1 == 8){
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                        }
                    }
                }
            }
            //else we're in the middle, we should be fine to capture left or right
            else{
                //capture right
                if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1)).getTeamColor() != color){
                        if(myPosition.getRow() + 1 == 8){
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                        }
                    }
                }
                //Capture left
                if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1)).getTeamColor() != color){
                        if(myPosition.getRow() + 1 == 8){
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.KNIGHT));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), ChessPiece.PieceType.QUEEN));
                        }
                        else {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                        }
                    }
                }
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


            if(myPosition.getColumn() == 1){
                //Capture right
                if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1)).getTeamColor() != color){
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                    }
                }
            }
            else if(myPosition.getColumn() == 8){
                //Capture left
                if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1)).getTeamColor() != color){
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                    }
                }
            }
            else{
                //Capture right
                if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1)).getTeamColor() != color){
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                    }
                }

                //Capture left
                if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1)) != null){
                    if(board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1)).getTeamColor() != color){
                        moves.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                    }
                }
            }
        }
        
        return moves;
    }
}
