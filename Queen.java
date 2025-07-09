
/**
 * Write a description of class Queen here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;

public class Queen extends Piece
{
    protected String orgPosition;
    public Queen(String c,String o){
        super.type="queen";
        super.colour=c;
        super.curPosition=o;
        super.newPosition="";
        this.orgPosition=o;
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

        int rowDiff = targetRow - currentRow;
        int colDiff = targetCol - currentCol;

        // Validate movement direction: must be straight or diagonal
        boolean isStraight = currentCol == targetCol || currentRow == targetRow;
        boolean isDiagonal = Math.abs(rowDiff) == Math.abs(colDiff);

        if (!isStraight && !isDiagonal) {
            return false;
        }

        // Determine step direction
        int rowStep = Integer.compare(targetRow, currentRow); // -1, 0, 1
        int colStep = Integer.compare(targetCol, currentCol); // -1, 0, 1

        // Step from current to one before target
        int row = currentRow + rowStep;
        int col = currentCol + colStep;

        while (row != targetRow || col != targetCol) {
            String intermediatePos = alpha[col] + row;
            if (!board.getPiece(intermediatePos).gettype().equalsIgnoreCase("not")) {
                return false; // Blocked by another piece
            }
            row += rowStep;
            col += colStep;
        }

        // Check final destination
        Piece destination = board.getPiece(target);
        if (destination.gettype().equalsIgnoreCase("not")) {
            return true; // Move to empty square
        }

        // Capture allowed only on opponent piece
        return !destination.getcolour().equalsIgnoreCase(this.colour);
    }

}
