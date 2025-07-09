
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Board
{
    protected Piece[][] board;
    protected White n;
    protected Black m;
    private boolean checkmate;
    private String lastMove="";
    public Board(){
        board=new Piece[8][8];
        n=new White();
        m=new Black();
        checkmate=false;
    }

    public void setUp(){
        Piece[] black=m.getBsoldiers();
        Piece[] blackp=m.getBpawns();
        Piece[] white=n.getWsoldiers();
        Piece[] whitep=n.getWpawns();
        for(int i=0;i<board.length;i++){
            Piece[] b= board[i];
            for(int k=0;k<b.length;k++){
                String[] a={"a","b","c","d", "e", "f", "g", "h"};
                String o=""+ Arrays.asList(a).indexOf(k)+(board.length-(i));
                board[i][k]=new No(o);
            }
            switch(i){
                case 0:
                    board[i]=black;
                    break;
                case 1:
                    board[i]=blackp;
                    break;
                case 6:
                    board[i]=whitep;
                    break;
                case 7:
                    board[i]=white;
                    break;
            }
        }
        printBoard();
    }

    public void printBoard(){
        String s=" ";
        for(int i=0;i<10;i++){
            s+=" ";
        }
        System.out.println("");
        System.out.println(s +"  A B C D E F G H "+"  - Black");
        for(int i=0;i<board.length;i++){
            String z=""+ (board.length-i)+" ";
            for(int j=0;j<board[i].length;j++){
                z+=board[i][j]+" ";
            }
            System.out.println(s+z);
        }
        System.out.println(s+"  A B C D E F G H"+ "  - White");
        System.out.println("");
    }

    public Piece getPiece(String a){
        Piece q=new No("err");
        //a is the position
        String[] alpha={"a","b","c","d","e","f","g","h"};
        //String a
        String m=""+ a.charAt(0);
        int num=Integer.parseInt(""+a.charAt(1));
        int c= Arrays.asList(alpha).indexOf(m);

        //System.out.println("String a: "+ a+" -- "+ num+" -- "+ c);
        for(int i=0;i<board.length;i++){
            if(i==board.length-num){
                for(int j=0;j<board[i].length;j++){
                    if(j==c){
                        q=(Piece) board[i][j];
                        //System.out.println("Type: "+ q.gettype()+ "Position: "+ q.getcurPosition());
                        return q;
                    }
                }
            }
        }
        return q;
    }

    public Piece[][] getBoard(){
        return board;
    }

    public void update(String c,Scanner kb) {
        String[] a = c.split(" ");
        int[] currentPos = parsePosition(a[0]);
        int[] newPos = parsePosition(a[1]);
        Piece[][] copy=deepCopyBoard();
        int orgRow = currentPos[0];
        int orgCol = currentPos[1];
        int destRow = newPos[0];
        int destCol = newPos[1];

        Piece curentPiece = getPiece(a[0]);
        Piece newPiece=getPiece(a[1]);
        if(newPiece!=null)curentPiece.setnewPosition(a[1]);
        if (curentPiece == null || !curentPiece.isValidMove(this))return;

        board[destRow][destCol] = curentPiece;
        board[orgRow][orgCol] = new No(a[0]);
        lastMove=c;

        if (curentPiece.gettype().equalsIgnoreCase("king") && Math.abs(destCol - orgCol) == 2) {
            int direction = (destCol - orgCol > 0) ? 1 : -1;
            int rookCol = (direction > 0) ? 7 : 0;
            int rookNewCol = (direction > 0) ? destCol - 1 : destCol + 1;

            String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h"};
            String rookPos = alpha[rookCol] + (8 - orgRow);
            String rookTarget = alpha[rookNewCol] + (8 - orgRow);

            Piece rook = getPiece(rookPos);
            if (rook != null && rook.gettype().equalsIgnoreCase("rook")) {
                rook.setcurPosition(rookTarget);
                board[orgRow][rookCol] = new No(rookPos);
                board[orgRow][rookNewCol] = rook;
                rook.hasMoved = true;
            }
        }

        curentPiece.setcurPosition(a[1]);
        // Pawn promotion with user choice
        if (curentPiece.gettype().equalsIgnoreCase("pawn")) {
            int finalRow = parsePosition(a[1])[0];
            int boardRow = 8 - finalRow;

            if ((curentPiece.getcolour().equalsIgnoreCase("white") && boardRow == 0) ||
            (curentPiece.getcolour().equalsIgnoreCase("black") && boardRow == 7)) {

                String pos = a[1];
                String colour = curentPiece.getcolour();
                String choice = "";

                while (true) {
                    System.out.println("Pawn reached the end! Choose promotion piece (Q, R, B, N): ");
                    choice = kb.nextLine().trim().toUpperCase();
                    if (Arrays.asList("Q", "R", "B", "N").contains(choice)) break;
                    System.out.println("Invalid choice. Please enter Q, R, B, or N.");
                }

                Piece promoted = null;
                switch (choice) {
                    case "Q":
                        promoted = new Queen(colour, pos);
                        break;
                    case "R":
                        promoted = new Rook(colour, pos);
                        break;
                    case "B":
                        promoted = new Bishop(colour, pos);
                        break;
                    case "N":
                        promoted = new Knight(colour, pos);
                        break;
                }

                board[finalRow][parsePosition(pos)[1]] = promoted;
                System.out.println("Pawn promoted to " + promoted.gettype() + "!");
            }
        }

        // En Passant capture
        if (curentPiece.gettype().equalsIgnoreCase("pawn")) {
            if (!getPiece(a[1]).gettype().equalsIgnoreCase("not")) {
                // Regular capture
            } else {
                int[] fromPos = parsePosition(a[0]);
                int[] toPos = parsePosition(a[1]);
                int colDiff = Math.abs(fromPos[1] - toPos[1]);

                if (colDiff == 1) {
                    // En Passant capture detected — remove the captured pawn
                    int capturedRow = fromPos[0]; // same row as origin
                    int capturedCol = toPos[1];   // column of destination
                    String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h"};
                    String capturedPos = alpha[capturedCol] + (8 - capturedRow);
                    board[capturedRow][capturedCol] = new No(capturedPos);
                }
            }
        }

        if (curentPiece.gettype().equalsIgnoreCase("king") || curentPiece.gettype().equalsIgnoreCase("rook")) {
            curentPiece.hasMoved = true;
        }

    }
    
    public String getLastMove(){
        return lastMove;
    }

    public int[] parsePosition(String cur){
        String[] alpha={"a","b","c","d","e","f","g","h"};
        String m=""+ cur.charAt(0);

        int row=8 - Integer.parseInt(""+cur.charAt(1));
        int col= Arrays.asList(alpha).indexOf(m);

        return new int[]{row,col};
    }

    public boolean getCheckMate(Player currentPlayer) {
        String color = currentPlayer.getColour();
        String enemyColor = color.equalsIgnoreCase("white") ? "black" : "white";

        //Check if the king is in check
        if (!isKingInCheck(color)) {
            return false; // Not checkmate if the king isn't in check
        }

        //Try every piece of the current player
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece.getcolour().equalsIgnoreCase(color) && !piece.gettype().equalsIgnoreCase("not")) {
                    String from = piece.getcurPosition();

                    for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                            String[] alpha = {"a","b","c","d","e","f","g","h"};
                            String to = alpha[c] + (r + 1);

                            Piece dest = getPiece(to);
                            String backupType = dest.gettype();
                            String backupColor = dest.getcolour();

                            // Simulate move
                            piece.setnewPosition(to);
                            if (piece.isValidMove(this)) {
                                Piece[][] backupBoard = deepCopyBoard();
                                update(from + " " + to);
                                if (!isKingInCheck(color)) {
                                    board = backupBoard;
                                    return false; // Found a legal move to escape
                                }
                                board = backupBoard; // Undo
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    protected boolean isKingInCheck(String color) {
        String kingPos = null;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece.gettype().equalsIgnoreCase("king") &&
                piece.getcolour().equalsIgnoreCase(color)) {
                    kingPos = piece.getcurPosition();
                }
            }
        }

        if (kingPos == null) return true; 

        String enemyColor = color.equalsIgnoreCase("white") ? "black" : "white";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece enemy = board[i][j];
                if (enemy.getcolour().equalsIgnoreCase(enemyColor)) {
                    enemy.setnewPosition(kingPos);
                    if (enemy.isValidMove(this)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void update(String c) {
        Scanner dummyScanner = new Scanner(System.in);
        update(c, dummyScanner);
    }

    protected Piece[][] deepCopyBoard() {
        Piece[][] copy = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece original = board[i][j];
                copy[i][j] = original;
            }
        }
        return copy;
    }
}
