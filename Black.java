
/**
 * Write a description of class White here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class Black
{
    protected Piece[] pawns=new Piece[8];
    protected Piece[] sold=new Piece[8];
    public Black(){
        Piece[] pawne={new Pawn("Black","a7"),new Pawn("Black","b7"),new Pawn("Black","c7"),new Pawn("Black","d7"),new Pawn("Black","e7"),new Pawn("Black","f7"),new Pawn("Black","g7"),new Pawn("Black","h7")};
        Piece[] soldi={new Rook("Black","a8"),new Knight("Black","b8"),new Bishop("Black","c8"),new Queen("Black","d8"),new King("Black","e8"),new Bishop("Black","f8"),new Knight("Black","g8"),new Rook("Black","h8")};
        for(int i=0;i<pawne.length;i++){
            pawns[i]=pawne[i];
            sold[i]=soldi[i];
        }
    }

    public Piece[] getBpawns(){
        //System.out.println("Pawns: "+ Arrays.toString(pawns));
        return pawns;
    }

    public Piece[] getBsoldiers(){
        //System.out.println("Soldiers: "+ Arrays.toString(sold));
        return sold;
    }

    //Validate the move 
    //Change the position from the original pos to new pos
    
    

}
