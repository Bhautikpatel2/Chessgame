
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    protected String name;
    protected String colour;
    protected String input;
    
    public Player(){
        name="";
        colour="";
        input="";
    }
    
    public Player(String n,String c){
        name=n;
        colour=c;
    }
    
    public String getColour(){
        return colour;
    }
    
    public void setColour(String c){
        colour=c;
    }
    
    public String getName(){
        return name;
    }
    
    
    public void setName(String c){
        name=c;
    }
    
    public String getInput(){
        return input;
    }
    
    public void setInput(String c){
        input=c;
    }
}
