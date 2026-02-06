package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor; 
        this.type = type;
    }


    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        if(piece.getPieceType() == PieceType.BISHOP){
            BishopCalculator bishopCalculator = new BishopCalculator(); 
            return bishopCalculator.returnAllMoves(board, myPosition, pieceColor);
        }
        if(piece.getPieceType() == PieceType.ROOK){
            RookCalculator rookCalculator = new RookCalculator();
            return rookCalculator.returnAllMoves(board, myPosition, pieceColor);
        }
        if(piece.getPieceType() == PieceType.QUEEN){
            QueenCalculator queenCalculator = new QueenCalculator();
            return queenCalculator.returnAllMoves(board, myPosition, pieceColor);
        }
        if(piece.getPieceType() == PieceType.KNIGHT){
            KnightCalculator knightCalculator = new KnightCalculator();
            return knightCalculator.returnAllMoves(board, myPosition, pieceColor);
        }
        if(piece.getPieceType() == PieceType.KING){
            KingCalculator kingCalculator = new KingCalculator();
            return kingCalculator.returnAllMoves(board, myPosition, pieceColor);
        }
        else{
            PawnCalculator pawnCalculator = new PawnCalculator();
            return pawnCalculator.returnAllMoves(board, myPosition, pieceColor);
        }
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}

