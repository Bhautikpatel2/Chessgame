
/**
 * Write a description of class Knight here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Knight extends Piece
{
    protected String orgPosition;
    public Knight(String c,String o){
        super.type="knight";
        super.colour=c;
        super.curPosition=o;
        super.newPosition="";
        this.orgPosition=o;
    }

    public boolean isValidMove(Board board) {
        String current = getcurPosition();
        String target = getnewPosition();
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h"};

        // Parse current position
        int currentCol = Arrays.asList(alpha).indexOf("" + current.charAt(0));
        int currentRow = Character.getNumericValue(current.charAt(1));

        // Parse new position
        int targetCol = Arrays.asList(alpha).indexOf("" + target.charAt(0));
        int targetRow = Character.getNumericValue(target.charAt(1));

        // Calculate row and column differences
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);

        // Check if move is L-shaped
        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
            return false;
        }

        // Check target piece
        Piece destinationPiece = board.getPiece(target);

        // Empty square or opponent's piece is valid
        if (destinationPiece.gettype().equalsIgnoreCase("not")) {
            return true;
        }

        // Check if the destination is an opponent's piece
        if (!destinationPiece.getcolour().equalsIgnoreCase(this.colour)) {
            return true;
        }

        // Can't capture own piece
        return false;
    }

    public String toString(){
        if(colour.equalsIgnoreCase("White")){
            return type.substring(1,2).toUpperCase();
        }
        return type.substring(1,2);
    }
}
