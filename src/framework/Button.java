/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.io.IOException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Administrator
 */
public class Button extends Node{
    
    Image bt_NormalImage,bt_SelectedImage,bt_DisableImage;
    
    boolean bt_bSelected = false;
    boolean bt_bDisable = false;
    
    String bt_Label = null;
    Font bt_Font = null;
    short bt_LabelOffsetX = 0;
    short bt_LabelOffsetY = 0;
    
    Runnable bt_Event = null;
    
    private Button (){}
    protected void drawSelf(Graphics g){
        if(bt_bDisable && bt_DisableImage != null){
            g.setClip(this.x - bt_DisableImage.getWidth()/2, this.y - bt_DisableImage.getHeight()/2, bt_DisableImage.getWidth(), bt_DisableImage.getHeight());
            g.setColor(n_Color);
            g.drawImage(bt_DisableImage, this.x - bt_DisableImage.getWidth()/2, this.y - bt_DisableImage.getHeight()/2, 0);
        }else if(bt_bSelected && bt_SelectedImage != null){
            g.setClip(this.x - bt_SelectedImage.getWidth()/2, this.y - bt_SelectedImage.getHeight()/2, bt_SelectedImage.getWidth(), bt_SelectedImage.getHeight());
            g.setColor(n_Color);
            g.drawImage(bt_SelectedImage, this.x - bt_SelectedImage.getWidth()/2, this.y - bt_SelectedImage.getHeight()/2, 0);
        }else if(bt_NormalImage != null){
            g.setClip(this.x - bt_NormalImage.getWidth()/2, this.y - bt_NormalImage.getHeight()/2, bt_NormalImage.getWidth(), bt_NormalImage.getHeight());
            g.setColor(n_Color);
            g.drawImage(bt_NormalImage, this.x - bt_NormalImage.getWidth()/2, this.y - bt_NormalImage.getHeight()/2, 0);
        }
        if(bt_Label != null && !bt_Label.equals("")){
            Font font = getFont();
            int w = font.stringWidth(bt_Label);
            int h = font.getHeight();
            g.setClip(this.x - w/2 + bt_LabelOffsetX, this.x - h/2 + bt_LabelOffsetY,w,h);
            g.setColor(n_Color);
            g.drawString(bt_Label,this.x - w/2 + bt_LabelOffsetX, this.x - h/2 + bt_LabelOffsetY,0);
        }
    }
 
    public static Button create(Image normalImage,Image selectedImage,Image disableImage){
        Button bt = new Button();
        bt.bt_NormalImage = normalImage;
        bt.bt_SelectedImage = selectedImage;
        bt.bt_DisableImage = disableImage;
        bt.autoSetRect();        
        return bt;
    }
    public static Button create(Image normalImage,Image selectedImage){
        return create(normalImage,selectedImage,null);
    }
    public static Button create(Image normalImage){
        return create(normalImage,null,null);
    }
    public static Button create(String normalImage,String selectedImage,String disableImage){
        return create(makeImage(normalImage),makeImage(selectedImage),makeImage(disableImage));
    }
    public static Button create(String normalImage,String selectedImage){
        return create(normalImage,selectedImage,null);
    }
    public static Button create(String normalImage){
        return create(normalImage,null,null);
    }
    
    
    private static Image makeImage(String file){

        Image image = null;
        if(file != null && !file.equals("")){
            try {
                image = Image.createImage(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return image;
    }
    
    private void autoSetRect(){
        if(bt_bDisable && bt_DisableImage != null){
            this.n_rect = new Rect(this.x - bt_DisableImage.getWidth()/2, this.y - bt_DisableImage.getHeight()/2, bt_DisableImage.getWidth(), bt_DisableImage.getHeight());
        }else if(bt_bSelected && bt_SelectedImage != null){
            this.n_rect = new Rect(this.x - bt_SelectedImage.getWidth()/2, this.y - bt_SelectedImage.getHeight()/2, bt_SelectedImage.getWidth(), bt_SelectedImage.getHeight());
        }else if(bt_NormalImage != null){
            this.n_rect = new Rect(this.x - bt_NormalImage.getWidth()/2, this.y - bt_NormalImage.getHeight()/2, bt_NormalImage.getWidth(), bt_NormalImage.getHeight());
        }else{
            this.n_rect = Rect.ZERO;
        }
    }
    
    public Button setSelected(boolean selected){
        this.bt_bSelected = selected;
        this.autoSetRect();
        return this;
    }
    public Button setEnable(boolean enable){
        this.bt_bDisable = enable;
        this.autoSetRect();
        return this;
    }
    public Button setLabel(String label){
        this.bt_Label = label;
        return this;
    }
    public String getLabel(){
        return this.bt_Label;
    }
    public Font getFont(){
        if(bt_Font == null){
            bt_Font = Font.getDefaultFont();
        }
        return bt_Font;
    }
    public Button setFont(Font font){
        this.bt_Font = font;
        return this;
    }
    public Button setLabelOffsetPos(int x,int y){
        this.bt_LabelOffsetX = (short)x;
        this.bt_LabelOffsetY = (short)y;
        return this;
    }
    public Button setLabelOffsetPos(short x,short y){
        this.bt_LabelOffsetX = x;
        this.bt_LabelOffsetY = y;
        return this;
    }
    
    public boolean action(){
        boolean res = this.bt_bSelected && !this.bt_bDisable 
                && this.n_bShowed && bt_Event != null
                && this.n_bEntered && !this.n_bExited && !this.n_bRemoved;
        if(res){
            bt_Event.run();
        }
        return res;
    }
    public Button addButtonEvent(Runnable r){
        this.bt_Event = r;
        return this;
    }
    public Runnable getBunttonEvent(){
        return this.bt_Event;
    }
    
    
}
