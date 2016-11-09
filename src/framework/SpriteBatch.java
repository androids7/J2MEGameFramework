/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Administrator
 */
public class SpriteBatch extends Node{
    
    Image s_Image = null;
    Image[][] s_Images = null;
    
    int s_w_num = 0;
    int s_h_num = 0;
    
    private SpriteBatch(){}
    
    protected void drawSelf(Graphics g){
        int xx = this.x;
        int yy = this.y;
        if((this.n_align_model & Node.ALIGN_H_LEFT) != 0){
                
        }else if((this.n_align_model & Node.ALIGN_H_RIGHT) != 0){
            xx -= n_rect.width;
        }else{
            xx -= n_rect.width/2;
        }
        if((this.n_align_model & Node.ALIGN_V_TOP) != 0){

        }else if((this.n_align_model & Node.ALIGN_V_BOTTOM) != 0){
            yy -= n_rect.height;
        }else{
            yy -= n_rect.height/2;
        }
            
        if(s_Image != null){
            int w = s_Image.getWidth();
            int h = s_Image.getHeight();
            setGraphicsCip(g,xx, yy, n_rect.width, n_rect.height);
            g.setColor(n_Color);
            //g.drawImage(s_Image, this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, 0);
            for(int i=0;i<s_w_num;i++){
                for(int j=0;j<s_h_num;j++){
                    g.drawImage(s_Image, xx + i*w, yy + j*h, 0);
                }
            }
            drawCell+= s_w_num * s_h_num;
        }else if(s_Images != null){
            setGraphicsCip(g,xx, yy, n_rect.width, n_rect.height);
            g.setColor(n_Color);
            int len = s_Images.length;
            for(int i=0;i<len;i++){
                Image[] imgs = s_Images[i];
                int maxH = 0;
                int tmpX = xx;
                int tmpY = yy;
                for(int j=0;j<imgs.length;j++){
                    Image img = imgs[j];
                    if(maxH < img.getHeight()) maxH = img.getHeight();
                    g.drawImage(img,tmpX,tmpY, 0);
                    tmpX += img.getWidth();
                    drawCell++;
                }
                yy += maxH;
            }
        }
    }
    
    public static SpriteBatch create(Image img,int wnum,int hnum){
        SpriteBatch sb = new SpriteBatch();
        if(img != null){
            sb.s_Image = img;
            sb.n_rect = new Rect(0,0,img.getWidth() * wnum,img.getHeight() * hnum);
        }
        sb.s_w_num = wnum;
        sb.s_h_num = hnum;
        return sb;
    }
    public static SpriteBatch createLimit(Image img,int wlength,int hlength){
        SpriteBatch sb = new SpriteBatch();
        if(img != null){
            sb.s_Image = img;
            
            int w = img.getWidth();
            int h = img.getHeight();
            
            sb.s_w_num = (int)Math.ceil(wlength/(w+0.0f)); 
            sb.s_h_num = (int)Math.ceil(hlength/(h+0.0f));
            
            sb.n_rect = new Rect(0,0,wlength,hlength);
        }
        return sb;
    }
    
    public static SpriteBatch create(Image[][] imgs){
        SpriteBatch sb = new SpriteBatch();
        sb.s_Images = imgs;
        if(imgs != null){
            int len = imgs.length;
            int w = 0;
            int h = 0;
            for(int i=0;i<len;i++){
                Image[] img = imgs[i];
                int tmpW = 0;
                int tmpH = 0;
                for(int j=0;j<img.length;j++){
                    tmpW += img[j].getWidth();
                    tmpH += img[j].getHeight();
                }
                if(w < tmpW) w = tmpW;
                if(h < tmpH) h = tmpH;
            }
            sb.n_rect = new Rect(0,0,w,h);
        }
        return sb;
    }
    
    public Image getImage(){
        return s_Image;
    }
    public Image[][] getImages(){
        return s_Images;
    }
    public void setImage(Image img){
        s_Image = img;
        s_Images = null;
    }
    public void getImages(Image[][] imgs){
        s_Images = imgs;
        s_Image = null;
        if(imgs != null){
            int len = imgs.length;
            int w = 0;
            int h = 0;
            for(int i=0;i<len;i++){
                Image[] img = imgs[i];
                int tmpW = 0;
                int tmpH = 0;
                for(int j=0;j<img.length;j++){
                    tmpW += img[j].getWidth();
                    tmpH += img[j].getHeight();
                }
                if(w < tmpW) w = tmpW;
                if(h < tmpH) h = tmpH;
            }
            this.n_rect = new Rect(0,0,w,h);
        }
    }
    
    public SpriteBatch clone(){
        SpriteBatch sb = new SpriteBatch();
        sb.s_Image = this.s_Image;
        sb.s_Images = this.s_Images;
        sb.s_w_num = this.s_w_num;
        sb.s_h_num = this.s_h_num;
        sb.x = this.x;
        sb.y = this.y;
        return sb;
    }
    
    protected void onCleanup(){
        super.onCleanup();
        s_Image = null;
        s_Images = null;
    }
}
