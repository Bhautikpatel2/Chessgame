
/**
 * Write a description of class Rook here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Rook extends Piece
{
    // Elephant
    protected String orgPosition;
    public Rook(String c, String o){
        super.type="rook";
        super.colour=c;
        super.curPosition=o;
        super.newPosition="";
        this.orgPosition=o;
        this.hasMoved=false;
    }

    public String toString(){
        if(colour.equalsIgnoreCase("White")){
            return type.substring(0,1).toUpperCase();
        }
        return type.substring(0,1);
    }

    public boolean isValidMove(Board board) {
        String current = getcurPosition();
        String target = getnewPosition();
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h"};

        // Parse positions
        int currentCol = Arrays.asList(alpha).indexOf("" + current.charAt(0));
        int currentRow = Character.getNumericValue(current.charAt(1));

        int targetCol = Arrays.asList(alpha).indexOf("" + target.charAt(0));
        int targetRow = Character.getNumericValue(target.charAt(1));

        // Not a valid rook move if not in same row or column
        if (currentCol != targetCol && currentRow != targetRow) {
            return false;
        }

        // Direction and range
        int rowStep = Integer.compare(targetRow, currentRow);   // 1, 0, or -1
        int colStep = Integer.compare(targetCol, currentCol);   // 1, 0, or -1

        // Start stepping from current to one before target
        int row = currentRow + rowStep;
        int col = currentCol + colStep;

        while (row != targetRow || col != targetCol) {
            String pos = alpha[col] + row;
            if (!board.getPiece(pos).gettype().equalsIgnoreCase("not")) {
                return false; // Blocked by another piece
            }
            row += rowStep;
            col += colStep;
        }

        // Check destination
        Piece destination = board.getPiece(target);
        if (destination.gettype().equalsIgnoreCase("not")) {
            return true; // Move to empty square
        }

        // Capture only if opponent's piece
        return !destination.getcolour().equalsIgnoreCase(this.colour);
    }

}
