
/**
 * Write a description of class Bishop here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Bishop extends Piece
{
    protected String orgPosition;
    public Bishop(String c,String o){
        super.type="bishop";
        super.colour=c;
        super.curPosition=o;
        super.newPosition="";
        this.orgPosition=o;
    }

    public boolean isValidMove(Board board) {
        String current = getcurPosition();
        String target = getnewPosition();
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h"};

        int currentCol = Arrays.asList(alpha).indexOf("" + current.charAt(0));
        int currentRow = Character.getNumericValue(current.charAt(1));

        int targetCol = Arrays.asList(alpha).indexOf("" + target.charAt(0));
        int targetRow = Character.getNumericValue(target.charAt(1));

        int rowDiff = targetRow - currentRow;
        int colDiff = targetCol - currentCol;

        // Check diagonal move
        if (Math.abs(rowDiff) != Math.abs(colDiff)) {
            return false;
        }

        int rowStep = Integer.compare(targetRow, currentRow);
        int colStep = Integer.compare(targetCol, currentCol);

        int row = currentRow + rowStep;
        int col = currentCol + colStep;

        while (row != targetRow || col != targetCol) {
            String intermediatePos = alpha[col] + row;
            if (!board.getPiece(intermediatePos).gettype().equalsIgnoreCase("not")) {
                return false; // Path blocked
            }
            row += rowStep;
            col += colStep;
        }

        // Check destination square
        Piece destination = board.getPiece(target);
        if (destination.gettype().equalsIgnoreCase("not")) {
            return true;
        }

        // Allow capture only if opponent
        return !destination.getcolour().equalsIgnoreCase(this.colour);
    }

    public String toString(){
        if(colour.equalsIgnoreCase("White")){
            return type.substring(0,1).toUpperCase();
        }
        return type.substring(0,1);
    }
}
