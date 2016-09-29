/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework;

/**
 *
 * @author Administrator
 */
public class Rect {
    
    protected short x,y,width,height;
    
    public Rect(){
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }
    
    public Rect(short x,short y,short width,short height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public Rect(int x,int y,int width,int height){
        this.x = (short)x;
        this.y = (short)y;
        this.width = (short)width;
        this.height = (short)height;
    }
    
    public Rect clone(){
        return new Rect(this.x,this.y,this.width,this.height);
    }
    
    public final static Rect ZERO = new Rect();

}
