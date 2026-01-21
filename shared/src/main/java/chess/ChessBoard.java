package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    //8x8 grid
    ChessPiece[][] board; 
    public ChessBoard() {
        board = new ChessPiece[8][8]; 
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece; 
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1]; 
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //Create a new board
        board = new ChessPiece[9][9]; 
        ChessPiece APawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece BPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece CPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece DPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece EPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece FPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece GPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece HPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        ChessPiece APawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece BPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece CPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece DPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece EPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece FPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece GPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece HPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        
        addPiece(new ChessPosition(2, 1), APawnWhite);
        addPiece(new ChessPosition(2, 2), BPawnWhite);
        addPiece(new ChessPosition(2, 3), CPawnWhite);
        addPiece(new ChessPosition(2, 4), DPawnWhite);
        addPiece(new ChessPosition(2, 5), EPawnWhite);
        addPiece(new ChessPosition(2, 6), FPawnWhite);
        addPiece(new ChessPosition(2, 7), GPawnWhite);
        addPiece(new ChessPosition(2, 8), HPawnWhite);

        addPiece(new ChessPosition(7, 1), APawnBlack);
        addPiece(new ChessPosition(7, 2), BPawnBlack);
        addPiece(new ChessPosition(7, 3), CPawnBlack);
        addPiece(new ChessPosition(7, 4), DPawnBlack);
        addPiece(new ChessPosition(7, 5), EPawnBlack);
        addPiece(new ChessPosition(7, 6), FPawnBlack);
        addPiece(new ChessPosition(7, 7), GPawnBlack);
        addPiece(new ChessPosition(7, 8), HPawnBlack);

        ChessPiece LeftRookWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece LeftKnightWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece LeftBishopWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece QueenWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece KingWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece RightBishopWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece RightKnightWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece RightRookWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        ChessPiece LeftRookBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece LeftKnightBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece LeftBishopBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece QueenBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece KingBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece RightBishopBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece RightKnightBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece RightRooBlacke = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        
        addPiece(new ChessPosition(1, 1), LeftRookWhite);
        addPiece(new ChessPosition(1, 2), LeftKnightWhite);
        addPiece(new ChessPosition(1, 3), LeftBishopWhite);
        addPiece(new ChessPosition(1, 4), QueenWhite);
        addPiece(new ChessPosition(1, 5), KingWhite);
        addPiece(new ChessPosition(1, 6), RightBishopWhite);
        addPiece(new ChessPosition(1, 7), RightKnightWhite);
        addPiece(new ChessPosition(1, 8), RightRookWhite);

        addPiece(new ChessPosition(8, 1), LeftRookBlack);
        addPiece(new ChessPosition(8, 2), LeftKnightBlack);
        addPiece(new ChessPosition(8, 3), LeftBishopBlack);
        addPiece(new ChessPosition(8, 4), QueenBlack);
        addPiece(new ChessPosition(8, 5), KingBlack);
        addPiece(new ChessPosition(8, 6), RightBishopBlack);
        addPiece(new ChessPosition(8, 7), RightKnightBlack);
        addPiece(new ChessPosition(8, 8), RightRooBlacke);
        
    }
}
