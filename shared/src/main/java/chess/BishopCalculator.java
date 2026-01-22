package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopCalculator {
    ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
    ChessPosition initialPosition; 

    public Collection<ChessMove> returnAllMoves(ChessBoard board, ChessPosition myPosition){
        //return List.of(new ChessMove(myPosition, endPositions(myPosition), null));
        initialPosition = myPosition; 
        endPositions(myPosition);
        return moves; 
    }


    //OVERLOAD ENDPOSITIONS. WHERE THERES A SECOND METHOD. SO WHAT IM THINKING IS YOU HAVE ONE METHOD THATS THE ROOT METHOD.
    //THE ROOT METHOD IS THE ONE YOU CALL UP TOP. THIS IS WHAT SENDS OUT ALL THE "RAYS". THEN FROM HERE ON ALL THE RAYS
    //THEN RECURSE ONWARDS DOWN EACH OF THEIR INDIVIDUAL LINES AND KEEP RETURNING STUFF OVER AND OVER ADDING TO THE 
    //MOVES ARRAYLIST. THEN WE CAN JUST RETURN MOVES AS NORMAL. IM SO TIRED :SOB:. IN THEORY THIS SHOULD WORK FOR ALMOST
    //EVERY SINGLE PIECE? START WITH A ROOT, THEN JUST SEND OUT "RAYS" IN EVERY SINGLE DIRECTION THAT HAVE THEIR OWN 
    //SET DIRECTION PASSED IN. THE OVERLOADED METHOD JUST HAS 2 PARAMETERES instead of one, and more stuff. java picks
    //which one to do on runtime remember. Then i think from here to figure out which piece we actually need to do if
    //theres an enemy piece in the way, we can just either A) cross that bridge when we get there or B) just keep track
    //of each of the hops starting from the root -> as we make our way away from it. the first enemey we arrive at we 
    //stop at and thats just where we stop at and we can just make a base case as that. just make a simple team color check. 
    //then for queens just literally combine this entire code i assume into one thing with the rook as well whenever you get
    //that done. 


    //hi to whoever reads this git commit later, yes this is what happens when i code at 1:15 am. yes i am tired. please have mercy on my poor soul :0 :)

     __
   _|==|_  
    ('')___/
>--(`^^')
  (`^'^'`)
  `======' 


    private ChessPosition endPositions(ChessPosition ChessPosition){
        String currentDirection; 
        enum String{
            NE,
            NW,
            SE,
            SW
        }

        if( !((ChessPosition.getRow() + 1 <= 8)  && (ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() <= 8) && (ChessPosition.getColumn() >= 1))){
            return ChessPosition; 
        }



        //top right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() + 1 <= 8)){
            //top right
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null))){
                return ChessPosition; 
            }
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() + 1));
        }
        //top left
        if((ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() + 1 <= 8)){
            //top left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null))){
                return ChessPosition; 
            }
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() + 1));
        }
        //bottom right
        if((ChessPosition.getRow() + 1 <= 8) && (ChessPosition.getColumn() - 1 >= 1)){
            //bottom right 
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null))){
                return ChessPosition; 
            }
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() + 1, ChessPosition.getColumn() - 1));
        }
        //bottom left
        if((ChessPosition.getRow() - 1 >= 1) && (ChessPosition.getColumn() - 1 >= 1)){
            //bottom left
            if(moves.contains(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null))){
                return ChessPosition; 
            }
            moves.add(new ChessMove(initialPosition, new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1), null));
            endPositions(new ChessPosition(ChessPosition.getRow() - 1, ChessPosition.getColumn() - 1));
        }
        //itself
        return ChessPosition; 
    } 
}
