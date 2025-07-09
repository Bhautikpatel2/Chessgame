
/**
 * Write a description of class Piece here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Piece
{
    protected String type;
    protected String colour;
    protected String curPosition;
    protected String newPosition;
    public boolean hasMoved=false;
    abstract boolean isValidMove(Board b); 

    public void setcolour(String c){
        colour=c;
    }
    
    public String getcolour(){
        return colour;
    }

    public void setcurPosition(String m){
        curPosition=m;
    }

    public void setnewPosition(String m){
        newPosition=m;
    }
    
    public String getnewPosition(){
        return newPosition;
    }
    
    public String getcurPosition(){
        return curPosition;
    }

    public String gettype(){
        return type;
    }

    public void settype(String c){
        type=c;
    }
}
