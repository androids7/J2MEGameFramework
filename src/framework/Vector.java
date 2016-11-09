/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 *
 * @author czl
 */
public class Vector {
    public int x,y;
    public Vector(int x,int y){
        this.x = x;
        this.y = y;
    }
    public Vector(){
        this(0,0);
    }
    
    public static final Vector ZERO = new Vector(0,0);
    
    public Vector clone(){
        return new Vector(this.x,this.y);
    }
}
