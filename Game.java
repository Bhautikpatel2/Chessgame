
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Game
{
    public static void main(){
        Scanner kb=new Scanner(System.in);
        System.out.println("---Welcome to Chess---");
        System.out.println("Enter name for Player 1 (White)");
        String w="Bhautik"; //kb.next();
        Player one=new Player(w,"white");
        System.out.println("Enter name for Player 2 (Black)");
        String b= "Vru";  //kb.next();
        Player two=new Player(b,"black");
        System.out.println("Game started. "+ w +" (White) goes first.");

        Board board=new Board();
        board.setUp();
        //System.out.println("  a b c d e f g h");
        Player[] p={one,two};
        int i=0;
        boolean checkmate=false;
        while (!checkmate) {
            String input = "";
            for (int k = 0; k < p.length; k++) {
                System.out.println("[ " + p[k].getName() + "'s Turn - " + p[k].getColour() + " ]");

                do {
                    System.out.println("Enter your move: ");
                    input = kb.nextLine();
                } while (!isLegalMove(input, board, p[k]));

                movement(board, input, kb);
                if (board.isKingInCheck(p[(k + 1) % 2].getColour())) {
                    System.out.println("CHECK! " + p[(k + 1) % 2].getName() + " king is under attack.");
                }

                if (board.getCheckMate(p[(k + 1) % 2])) {
                    System.out.println("CHECKMATE! " + p[k].getColour() + " wins!");
                    checkmate = true;
                    break;
                }
            }

        }

    }

    public static void movement(Board board,String input,Scanner kb){
        board.update(input,kb);
        board.printBoard();
        System.out.println("");
        //Need to mov the piece from old to new position
    }

    public static boolean isValidInput(String input, Board board, Player p) {
        //System.out.println("Checking the input");
        String[] arr = input.trim().split(" ");
        if (arr.length != 2) return false;

        String[] alpha = {"a","b","c","d","e","f","g","h"};

        String from = arr[0];
        String to = arr[1];

        // Basic format checks
        if (from.length() != 2 || to.length() != 2) return false;

        char fromCol = from.charAt(0);
        char fromRow = from.charAt(1);
        char toCol = to.charAt(0);
        char toRow = to.charAt(1);

        if (!Arrays.asList(alpha).contains(String.valueOf(fromCol))) return false;
        if (!Arrays.asList(alpha).contains(String.valueOf(toCol))) return false;
        if (!Character.isDigit(fromRow) || !Character.isDigit(toRow)) return false;

        int fromRowNum = Character.getNumericValue(fromRow);
        int toRowNum = Character.getNumericValue(toRow);

        if (fromRowNum < 1 || fromRowNum > 8 || toRowNum < 1 || toRowNum > 8) return false;

        // Prevent moves to the same square
        if (from.equals(to)) return false;

        // Check if the starting square contains a valid piece
        Piece piece = board.getPiece(from);
        if (piece == null || piece.gettype().equalsIgnoreCase("no")) return false;

        // Check if piece belongs to the current player
        if (!piece.getcolour().equalsIgnoreCase(p.getColour())) return false;

        //System.out.println("Checked the input");
        return true;
    }

    public static boolean isLegalMove(String input, Board board, Player p) {
        if (!isValidInput(input, board, p)) return false;

        String[] arr = input.trim().split(" ");
        String from = arr[0];
        String to = arr[1];

        Piece piece = board.getPiece(from);
        piece.setnewPosition(to);

        Piece[][] backup = board.deepCopyBoard();
        board.update(input);

        boolean stillInCheck = board.isKingInCheck(p.getColour());
        board.board = backup; 

        return !stillInCheck && piece.isValidMove(board);
    }

}
