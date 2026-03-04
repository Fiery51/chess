package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard{

    //8x8 grid
    ChessPiece[][] board; 
    public ChessBoard() {
        board = new ChessPiece[8][8]; 
    }

    //Copy constructor
    public ChessBoard(ChessBoard board) {
        this.board = new ChessPiece[8][8];
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                if(board.getPiece(new ChessPosition(i, j)) != null){
                    //Read off the attributes
                    ChessGame.TeamColor theTeamColor = board.getPiece(new ChessPosition(i, j)).getTeamColor();
                    ChessPiece.PieceType thePieceType = board.getPiece(new ChessPosition(i, j)).getPieceType(); 
                    //set the new piece up
                    addPiece(new ChessPosition(i, j), new ChessPiece(theTeamColor, thePieceType));
                }
                else{
                    addPiece(new ChessPosition(i, j), null);
                }
            }
        }
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
        board = new ChessPiece[8][8]; 
        ChessPiece aPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece bPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece cPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece dPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece ePawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece fPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece gPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece hPawnWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        ChessPiece aPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece bPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece cPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece dPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece ePawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece fPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece gPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece hPawnBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        
        addPiece(new ChessPosition(2, 1), aPawnWhite);
        addPiece(new ChessPosition(2, 2), bPawnWhite);
        addPiece(new ChessPosition(2, 3), cPawnWhite);
        addPiece(new ChessPosition(2, 4), dPawnWhite);
        addPiece(new ChessPosition(2, 5), ePawnWhite);
        addPiece(new ChessPosition(2, 6), fPawnWhite);
        addPiece(new ChessPosition(2, 7), gPawnWhite);
        addPiece(new ChessPosition(2, 8), hPawnWhite);

        addPiece(new ChessPosition(7, 1), aPawnBlack);
        addPiece(new ChessPosition(7, 2), bPawnBlack);
        addPiece(new ChessPosition(7, 3), cPawnBlack);
        addPiece(new ChessPosition(7, 4), dPawnBlack);
        addPiece(new ChessPosition(7, 5), ePawnBlack);
        addPiece(new ChessPosition(7, 6), fPawnBlack);
        addPiece(new ChessPosition(7, 7), gPawnBlack);
        addPiece(new ChessPosition(7, 8), hPawnBlack);

        ChessPiece leftRookWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece leftKnightWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece leftBishopWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece queenWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece kingWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece rightBishopWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece rightKnightWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece rightRookWhite = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        ChessPiece leftRookBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece leftKnightBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece leftBishopBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece queenBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece kingBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece rightBishopBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece rightKnightBlack = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece rightRooBlacke = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        
        addPiece(new ChessPosition(1, 1), leftRookWhite);
        addPiece(new ChessPosition(1, 2), leftKnightWhite);
        addPiece(new ChessPosition(1, 3), leftBishopWhite);
        addPiece(new ChessPosition(1, 4), queenWhite);
        addPiece(new ChessPosition(1, 5), kingWhite);
        addPiece(new ChessPosition(1, 6), rightBishopWhite);
        addPiece(new ChessPosition(1, 7), rightKnightWhite);
        addPiece(new ChessPosition(1, 8), rightRookWhite);

        addPiece(new ChessPosition(8, 1), leftRookBlack);
        addPiece(new ChessPosition(8, 2), leftKnightBlack);
        addPiece(new ChessPosition(8, 3), leftBishopBlack);
        addPiece(new ChessPosition(8, 4), queenBlack);
        addPiece(new ChessPosition(8, 5), kingBlack);
        addPiece(new ChessPosition(8, 6), rightBishopBlack);
        addPiece(new ChessPosition(8, 7), rightKnightBlack);
        addPiece(new ChessPosition(8, 8), rightRooBlacke);
        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(board);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChessBoard other = (ChessBoard) obj;
        if (!Arrays.deepEquals(board, other.board))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChessBoard [board=" + Arrays.toString(board) + "]";
    }


}
