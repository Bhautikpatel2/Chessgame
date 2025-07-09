
/**
 * Write a description of class No here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class No extends Piece
{
    protected String orgPosition;
    public No(String c){
        super.type="Not";
        super.colour="none";
        super.curPosition="";
        super.newPosition="";
        orgPosition=c;
    }
    
    public boolean isValidMove(Board board){
        return false;
    }
    
    public String toString(){
        return ".";
    }
}
