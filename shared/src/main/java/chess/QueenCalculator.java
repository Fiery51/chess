package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
        Collection<ChessMove> rookMoves = new RookCalculator().returnAllMoves(board, myPosition, color);
        Collection<ChessMove> bishopMoves = (new BishopCalculator().returnAllMoves(board, myPosition, color));
        
        moves.addAll(rookMoves);
        moves.addAll(bishopMoves);
        return moves;
    }
}
