package framework;

/**
 *
 * @author chizl
 */
public class Rect {
    
    public int x,y,width,height;
    
    public Rect(){
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public Rect(int x,int y,int width,int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Rect clone(){
        return new Rect(this.x,this.y,this.width,this.height);
    }
    
    public final static Rect ZERO = new Rect();

}
