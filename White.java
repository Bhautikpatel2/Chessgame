
/**
 * Write a description of class White here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.util.*;
public class White
{
    protected Piece[] pawns=new Piece[8];
    protected Piece[] sold=new Piece[8];
    public White(){
        Piece[] pawne={new Pawn("White","a2"),new Pawn("White","b2"),new Pawn("White","c2"),new Pawn("White","d2"),new Pawn("White","e2"),new Pawn("White","f2"),new Pawn("White","g2"),new Pawn("White","h2")};
        Piece[] soldi={new Rook("White","a1"),new Knight("White","b1"),new Bishop("White","c1"),new Queen("White","d1"),new King("White","e1"),new Bishop("White","f1"),new Knight("White","g1"),new Rook("White","h1")};
        for(int i=0;i<pawne.length;i++){
            pawns[i]=pawne[i];
            sold[i]=soldi[i];
        }
    }

    public Piece[] getWpawns(){
        //System.out.println("Pawns: "+ Arrays.toString(pawns));
        return pawns;
    }

    public Piece[] getWsoldiers(){
        //System.out.println("Soldiers: "+ Arrays.toString(sold));
        return sold;
    }

    //Validate the move 
    //Change the position from the original pos to new pos
    
    

}
