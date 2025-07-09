
/**
 * Write a description of class Pawn here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Pawn extends Piece
{
    protected String orgPosition;
    public Pawn(String c,String o){
        super.type="pawn";
        super.colour=c;
        super.curPosition=o;
        super.newPosition="";
        this.orgPosition=o;
    }

    public boolean isValidMove(Board board) {
        String a = getcurPosition();
        String b = getnewPosition();
        String[] alpha = {"a","b","c","d","e","f","g","h"};

        String m = "" + a.charAt(0);
        int num = Integer.parseInt("" + a.charAt(1));
        int c = Arrays.asList(alpha).indexOf(m);

        String m1 = "" + b.charAt(0);
        int num1 = Integer.parseInt("" + b.charAt(1));
        int c1 = Arrays.asList(alpha).indexOf(m1);

        int colDiff = c1 - c;
        int rowDiff = num1 - num;
        int direction = colour.equalsIgnoreCase("White") ? 1 : -1;
        //System.out.println("rowDiff: " + rowDiff + "  direction: " + direction);

        // Forward one step
        if (c == c1 && rowDiff == direction) {
            Piece ahead = board.getPiece(newPosition);
            if (ahead.gettype().equalsIgnoreCase("not")) return true;
        }

        // Forward two steps from original position
        if (c == c1 && orgPosition.equalsIgnoreCase(a) && rowDiff == 2 * direction) {
            int intermediateRow = num + direction;
            String intermediatePos = m + intermediateRow;
            if (board.getPiece(intermediatePos).gettype().equalsIgnoreCase("not") &&
            board.getPiece(newPosition).gettype().equalsIgnoreCase("not")) {
                return true;
            }
        }

        // Diagonal capture
        if (Math.abs(colDiff) == 1 && rowDiff == direction) {
            Piece target = board.getPiece(newPosition);            
            if (target != null && !target.gettype().equalsIgnoreCase("not") &&
            !target.colour.equalsIgnoreCase(this.colour)) {
                return true;
            }
        }

        // En Passant
        if (Math.abs(colDiff) == 1 && rowDiff == direction) {
            // Get last move
            String lastMove = board.getLastMove();
            if (lastMove != null && !lastMove.isEmpty()) {
                String[] parts = lastMove.split(" ");
                String lastFrom = parts[0];
                String lastTo = parts[1];
                Piece lastPiece = board.getPiece(lastTo);

                if (lastPiece.gettype().equalsIgnoreCase("pawn") &&
                !lastPiece.getcolour().equalsIgnoreCase(this.colour)) {

                    int[] lastFromPos = board.parsePosition(lastFrom);
                    int[] lastToPos = board.parsePosition(lastTo);

                    // Check if the last pawn moved two squares forward
                    if (Math.abs(lastFromPos[0] - lastToPos[0]) == 2 &&
                    lastToPos[0] == board.parsePosition(newPosition)[0] + (direction * -1) &&
                    lastToPos[1] == board.parsePosition(newPosition)[1]) {

                        return true; // En Passant is valid
                    }
                }
            }
        }

        //System.out.println("Invalid pawn move from " + a + " to " + b);
        return false;
    }

    public String toString(){
        if(colour.equalsIgnoreCase("White")){
            return type.substring(0,1).toUpperCase();
        }
        return type.substring(0,1);
    }

    public String getorgPosition(){
        return orgPosition;
    }
}
