
/**
 * Write a description of class King here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class King extends Piece
{
    protected String orgPosition;
    public King(String c,String o){
        super.type="King";
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
        return type.substring(0,1).toLowerCase();
    }

    public boolean isValidMove(Board board) {
        String current = getcurPosition();
        String target = getnewPosition();
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h"};

        int currentCol = Arrays.asList(alpha).indexOf("" + current.charAt(0));
        int currentRow = Character.getNumericValue(current.charAt(1));

        int targetCol = Arrays.asList(alpha).indexOf("" + target.charAt(0));
        int targetRow = Character.getNumericValue(target.charAt(1));

        int rowDiff = Math.abs(currentRow - targetRow);
        int colDiff = Math.abs(currentCol - targetCol);

        // Regular 1-square move in any direction
        if ((rowDiff <= 1 && colDiff <= 1) && !(rowDiff == 0 && colDiff == 0)) {
            Piece targetPiece = board.getPiece(target);
            return targetPiece.gettype().equalsIgnoreCase("not") ||
            !targetPiece.getcolour().equalsIgnoreCase(this.colour);
        }

        // Castling: king must be in starting position and move 2 squares horizontally
        if (!hasMoved && rowDiff == 0 && colDiff == 2) {
            int direction = (targetCol - currentCol > 0) ? 1 : -1; // Kingside or Queenside
            int rookCol = direction > 0 ? 7 : 0; // h-file or a-file

            String rookPos = alpha[rookCol] + currentRow;
            Piece rook = (Rook) board.getPiece(rookPos);
            boolean moved=rook.hasMoved;

            if (rook != null && rook.gettype().equalsIgnoreCase("rook") && rook.getcolour().equalsIgnoreCase(this.colour) && !rook.hasMoved) {

                // Check squares between king and rook are empty
                for (int c = currentCol + direction; c != rookCol; c += direction) {
                    String intermediate = alpha[c] + currentRow;
                    if (!board.getPiece(intermediate).gettype().equalsIgnoreCase("not")) {
                        return false; // Obstruction
                    }
                }

                // Check king is not in check and doesn't cross over check
                for (int c = currentCol; c != targetCol + direction; c += direction) {
                    String tempPos = alpha[c] + currentRow;
                    Piece[][] backup = board.deepCopyBoard();

                    this.setnewPosition(tempPos);
                    board.update(current + " " + tempPos);

                    boolean inCheck = board.isKingInCheck(this.colour);
                    board.board = backup; // Restore

                    if (inCheck) return false; // Cannot castle through check
                }

                return true; // Castling is legal
            }
        }

        return false;
    }

}
