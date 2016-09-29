/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Administrator
 */
public class Sprite extends Node{
    
    Image mImage = null;
    
    private Sprite(){}

    protected void drawSelf(Graphics g){
        if(mImage != null){
            g.setClip(this.x, this.y, mImage.getWidth(), mImage.getHeight());
            g.setColor(n_Color);
            g.drawImage(mImage, this.x, this.y, 0);
        }
    }
    
    public static Sprite create(String file){
        Sprite sp = new Sprite();
        sp.makeImage(file);
        return sp;
    }
    
    public static Sprite create(Image image){
        Sprite sp = new Sprite();
        sp.setImage(image);
        return sp;
    }
    
    private void makeImage(String file){
        try {
            mImage = Image.createImage(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void setImage(Image image){
        mImage = image;
    }
    
}