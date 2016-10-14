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
 * @author chizl
 */
public class SlideBar extends Node{
    
    Image s_Image = null;
    Image s_bgImage = null;
    
    int s_value = 100;
    
    int s_model = 0;
    
    public static final int MODEL_HORIZONTAL_LEFT = 0;
    public static final int MODEL_HORIZONTAL_RIGHT = 1;
    public static final int MODEL_VERTICAL_TOP = 2;
    public static final int MODEL_VERTICAL_BOTTOM = 3;
    
    private SlideBar(){}
    
    protected void drawSelf(Graphics g){
        if(s_Image != null){
            g.setColor(n_Color);
            if(s_bgImage != null){
                setGraphicsCip(g,this.x - s_bgImage.getWidth()/2, this.y - s_bgImage.getHeight()/2, s_bgImage.getWidth(), s_bgImage.getHeight());
                g.drawImage(s_bgImage, this.x - s_bgImage.getWidth()/2, this.y - s_bgImage.getHeight()/2, 0);
            }
            switch(s_model){
                case MODEL_HORIZONTAL_RIGHT:
                    setGraphicsCip(g,this.x - s_Image.getWidth()/2 + s_Image.getWidth()*(100 - s_value)/100 , this.y - s_Image.getHeight()/2, s_Image.getWidth(), s_Image.getHeight());
                    break;
                case MODEL_VERTICAL_TOP:
                    setGraphicsCip(g,this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, s_Image.getWidth(), s_Image.getHeight()*s_value/100);
                    break;
                case MODEL_VERTICAL_BOTTOM:
                    setGraphicsCip(g,this.x - s_Image.getWidth()/2 , this.y - s_Image.getHeight()/2 + s_Image.getHeight()*(100 - s_value)/100, s_Image.getWidth(), s_Image.getHeight());
                    break;
                default:
                    setGraphicsCip(g,this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, s_Image.getWidth()*s_value/100, s_Image.getHeight());
                    break;
            }
            g.drawImage(s_Image, this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, 0);
        }
    }
    
    public static SlideBar create(String file){
        return create(null,makeImage(file),0);
    }
    
    public static SlideBar create(Image image){
        return create(null,image,0);
    }
    
    public static SlideBar create(String file,int model){
        return create(null,makeImage(file),model);
    }
    
    public static SlideBar create(String bgImage,String barImage){
        return create(makeImage(bgImage),makeImage(barImage),0);
    }
    public static SlideBar create(Image bgImage,Image barImage){
        return create(bgImage,barImage,0);
    }
    public static SlideBar create(Image bgImage,Image barImage,int model){
        SlideBar sb = new SlideBar();
        sb.s_Image = barImage;
        sb.setBackgroundImage(bgImage);
        sb.s_model = model;
        return sb;
    }
    
    public static Image makeImage(String file){
        Image img = null;
        try {
            img = Image.createImage(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return img;
    }
    
    public SlideBar setImage(Image image){
        this.s_Image = image;
        if(this.s_bgImage != null){
            this.n_rect = new Rect(this.x - s_bgImage.getWidth()/2, this.y - s_bgImage.getHeight()/2, s_bgImage.getWidth(), s_bgImage.getHeight());
        }else if(this.s_Image != null){
            this.n_rect = new Rect(this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, s_Image.getWidth(), s_Image.getHeight());
        }else{
            this.n_rect = Rect.ZERO;
        }
        return this;
    }
    
    public Image getImage(){
        return this.s_Image;
    }
    public SlideBar setBackgroundImage(Image image){
        this.s_bgImage = image;
        if(this.s_bgImage != null){
            this.n_rect = new Rect(this.x - s_bgImage.getWidth()/2, this.y - s_bgImage.getHeight()/2, s_bgImage.getWidth(), s_bgImage.getHeight());
        }else if(this.s_Image != null){
            this.n_rect = new Rect(this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, s_Image.getWidth(), s_Image.getHeight());
        }else{
            this.n_rect = Rect.ZERO;
        }
        return this;
    }
    public Image getBackgroundImage(){
        return this.s_bgImage;
    }
    
    /**
     * 获取进度条百分比
     * @return 0~100
     */
    public int getValue(){
        return this.s_value;
    }
    
    /**
     * 设置进度条百分比（0~100）
     * @param value
     * @return
     */
    public SlideBar setValue(int value){
        this.s_value = value;
        if(this.s_value < 0){
            this.s_value = 0;
        }else if(this.s_value > 100){
            this.s_value = 100;
        }
        return this;
    }
    
    public SlideBar setModel(int model){
        this.s_model = model;
        return this;
    }
    
}
