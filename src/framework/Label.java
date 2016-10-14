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
public class Label extends Node{
    
    private Label(){};
    Image l_Image = null;
    Image l_bgImage = null;

    int l_FrameTimes = -1;
    int l_FrameCurDelayTime = -1;
    int l_FrameTotalDelayTime = -1;
    int l_curFrameIndex = 0;
    
    boolean l_bFlipedX = false;
    boolean l_bFlipedY = false;
    
    int l_rotate = 0;
    
    Font l_font = null;
    String l_content = null;
    int l_str_color = -1;
    int l_bg_color = -1;
    int l_anchor = 0;
    boolean l_b_rich = false;

    protected void drawSelf(Graphics g){
        if(l_Image != null){
            setGraphicsCip(g,this.x - l_Image.getWidth()/2, this.y - l_Image.getHeight()/2, l_Image.getWidth(), l_Image.getHeight());
            //g.setColor(n_Color);
            g.drawImage(l_Image, this.x - l_Image.getWidth()/2, this.y - l_Image.getHeight()/2, 0);
        }
    }
    
    public static Label create(String str){
        Label label = new Label();
        label.initString(null,str,0xffffffff,0);
        return label;
    }
    public static Label create(String str,int str_color){
        Label label = new Label();
        label.initString(null,str,str_color,0);
        return label;
    }
    public static Label create(String str,int str_color,int bg_color){
        Label label = new Label();
        label.initString(null,str,str_color,bg_color);
        return label;
    }
    
    public static Label create(String str,int str_color,Image bg_image){
        Label label = new Label();
        label.initString(null,str,bg_image,str_color);
        return label;
    }
    
    void initString(Font font,String str,int str_color,int bg_color){
        if(font != null)
            this.l_font = font;
        else
            l_font = Font.getDefaultFont();
        if(str == null){
            str = "";
        }
        int w = l_font.stringWidth(str);
        int h = l_font.getHeight();
        Image img = Image.createImage(w,h);
        Graphics g = img.getGraphics();
        g.setFont(l_font);
        g.setColor(bg_color);
        g.fillRect(0, 0, w, h);
        g.setColor(str_color);
        g.drawString(str,0, 0, 0);
        l_content = str;
        l_Image = Image.createImage(img, 0, 0, w, h, 0);
        l_str_color = str_color;
        l_bg_color = bg_color;
        //fiped();
        //rotate(l_rotate);
        transform();
        n_rect = new Rect(this.x - l_Image.getWidth()/2, this.y - l_Image.getHeight()/2, l_Image.getWidth(), l_Image.getHeight());
    }
    void initString(Font font,String str,Image bg,int str_color){
        if(font != null)
            this.l_font = font;
        else
            l_font = Font.getDefaultFont();
        if(str == null){
            str = "";
        }
        int w = l_font.stringWidth(str);
        int h = l_font.getHeight();
        Image img = Image.createImage(w,h);
        Graphics g = img.getGraphics();
        g.setFont(l_font);
        g.drawImage(bg, bg.getWidth(), bg.getHeight(), 0);
        g.setColor(str_color);
        g.drawString(str, h, h, 0);
        l_content = str;
        l_Image = Image.createImage(img, 0, 0, w, h, 0);
        l_bgImage = bg;
        l_str_color = str_color;
        //fiped();
        //rotate(l_rotate);
        transform();
        n_rect = new Rect(this.x - l_Image.getWidth()/2, this.y - l_Image.getHeight()/2, l_Image.getWidth(), l_Image.getHeight());
    }
    
    public static Label createRich(String str){
        Label label = new Label();
        label.initRichString(null,Graphics.TOP | Graphics.LEFT,str,0xffffffff,0);
        return label;
    }
    /*
    public static Label createRich(String str,int anchor){
        Label label = new Label();
        label.initRichString(null,anchor,str,0xffffffff,0);
        return label;
    }
    */
    public static Label createRich(String str,int str_color,int anchor){
        Label label = new Label();
        label.initRichString(null,anchor,str,str_color,0);
        return label;
    }
    public static Label createRich(String str,int str_color,int bg_color,int anchor){
        Label label = new Label();
        label.initRichString(null,anchor,str,str_color,bg_color);
        return label;
    }
    public static Label createRich(String str,int str_color,Image bg_Image,int anchor){
        Label label = new Label();
        label.initRichString(null,anchor,str,bg_Image,str_color);
        return label;
    }
    
    /**
     * 富文本功能，目前只实现换行
     * @param font 字体
     * @param anchor 对方方式
     * @param str 内容 \n : 换行
     * @param str_color 字体颜色
     * @param bg_color 背景颜色 
     */
    void initRichString(Font font,int anchor,String str,int str_color,int bg_color){
        l_b_rich = true;
        if(font != null)
            this.l_font = font;
        else
            l_font = Font.getDefaultFont();
        if(str == null){
            str = "";
        }
        
        String[] res = new String[5];
        int pos = str.indexOf('\n');
        int index = 0;
        int start = 0;
        int maxWidth = 0;
        while(pos != -1){
            if(index == res.length){
                String[] tmp = res;
                res = new String[index + 5];
                System.arraycopy(tmp, 0, res, 0, index);
            }
            String sub = str.substring(start, pos);
            res[index++] = sub;
            start = pos + 1;
            pos = str.indexOf('\n', start);
            int w = l_font.stringWidth(sub);
           /* int cStart = sub.indexOf("<color");
            int cEnded = sub.indexOf("</color>");
            
            while(cStart != -1 && cEnded != -1){
                w -= (cEnded - cStart);
                cStart = sub.indexOf("<color",cStart+6);
                cEnded = sub.indexOf("</color>",cEnded+8);
            }*/
            if(maxWidth < w){
                maxWidth = w;
            }
        }
        if(/*start > 0 && */start < str.length()){
            if(index == res.length){
                String[] tmp = res;
                res = new String[index + 1];
                System.arraycopy(tmp, 0, res, 0, index);
            }
            String sub = str.substring(start);
            res[index++] = sub;
            int w = l_font.stringWidth(sub);
            if(maxWidth < w){
                maxWidth = w;
            }
        }
        int h = l_font.getHeight();
        int maxHeight = h  * index;
        //System.err.println("============== maxWidth"+maxWidth+" maxHeight:"+maxHeight);
        Image img = Image.createImage(maxWidth,maxHeight);
        Graphics g = img.getGraphics();
        g.setFont(l_font);
        g.setColor(bg_color);
        g.fillRect(0, 0, maxWidth,maxHeight);
        g.setColor(str_color);
        for(int i=0;i<index;i++){
            g.drawString(res[i],0, i*h, anchor);
        }
        l_content = str;
        l_Image = Image.createImage(img, 0, 0, maxWidth, maxHeight, 0);
        l_str_color = str_color;
        l_bg_color = bg_color;
        //fiped();
        //rotate(l_rotate);
        transform();
        n_rect = new Rect(this.x - l_Image.getWidth()/2, this.y - l_Image.getHeight()/2, l_Image.getWidth(), l_Image.getHeight()); 
    }
    
     void initRichString(Font font,int anchor,String str,Image bgImage,int str_color){
        l_b_rich = true;
        if(font != null)
            this.l_font = font;
        else
            l_font = Font.getDefaultFont();
        if(str == null){
            str = "";
        }
        
        String[] res = new String[5];
        int pos = str.indexOf('\n');
        int index = 0;
        int start = 0;
        int maxWidth = 0;
        while(pos != -1){
            if(index == res.length){
                String[] tmp = res;
                res = new String[index + 5];
                System.arraycopy(tmp, 0, res, 0, index);
            }
            String sub = str.substring(start, pos);
            res[index++] = sub;
            start = pos + 1;
            pos = str.indexOf('\n', start);
            int w = l_font.stringWidth(sub);
           /* int cStart = sub.indexOf("<color");
            int cEnded = sub.indexOf("</color>");
            
            while(cStart != -1 && cEnded != -1){
                w -= (cEnded - cStart);
                cStart = sub.indexOf("<color",cStart+6);
                cEnded = sub.indexOf("</color>",cEnded+8);
            }*/
            if(maxWidth < w){
                maxWidth = w;
            }
        }
        if(start < str.length()){
            if(index == res.length){
                String[] tmp = res;
                res = new String[index + 1];
                System.arraycopy(tmp, 0, res, 0, index);
            }
            String sub = str.substring(start);
            res[index++] = sub;
            int w = l_font.stringWidth(sub);
            if(maxWidth < w){
                maxWidth = w;
            }
        }
        int h = l_font.getHeight();
        int maxHeight = h  * index;
        Image img = Image.createImage(maxWidth,maxHeight);
        Graphics g = img.getGraphics();
        g.setFont(l_font);
        g.drawImage(bgImage, bgImage.getWidth(), bgImage.getHeight(), 0);
        g.fillRect(0, 0, maxWidth,maxHeight);
        g.setColor(str_color);
        for(int i=0;i<index;i++){
            g.drawString(res[i],0, i*h, anchor);
        }
        l_content = str;
        l_Image = Image.createImage(img, 0, 0, maxWidth, maxHeight, 0);
        l_str_color = str_color;
        //fiped();
        //rotate(l_rotate);
        transform();
        n_rect = new Rect(this.x - l_Image.getWidth()/2, this.y - l_Image.getHeight()/2, l_Image.getWidth(), l_Image.getHeight()); 
    }
     
    public Label setString(String str){
        if(l_bgImage != null){
            if(!l_b_rich)
                initString(getFont(),str,l_bgImage,l_str_color);
            else
                initRichString(getFont(),l_anchor, str,l_bgImage,l_str_color);
        }else{
            if(!l_b_rich)
                initString(getFont(), str, l_str_color, l_bg_color);
            else
                initRichString(getFont(),l_anchor,str, l_str_color, l_bg_color);
        }
        return this;
    }
    public String getString(){
        return this.l_content;
    }
    public Label setBackgroundColor(int color){
        if(l_bgImage == null){
            if(!l_b_rich)
                initString(getFont(), l_content, l_str_color, color);
            else{
                initRichString(getFont(),l_anchor, l_content, l_str_color, color);
            }
        }
        return this;
    }
    
    public Font getFont(){
        if(l_font == null){
            l_font = Font.getDefaultFont();
        }
        return l_font;
    }
    public Label setFont(Font font){
        Font old = this.l_font;
        if(font != null)
            this.l_font = font;
        else
            l_font = Font.getDefaultFont();
        if(old != this.l_font && old != null){
            if(l_bgImage != null){
                if(!l_b_rich)
                    initString(l_font,l_content,l_bgImage,l_str_color);
                else
                    initRichString(l_font,l_anchor,l_content,l_bgImage,l_str_color);
            }else{
                if(!l_b_rich)
                    initString(l_font, l_content, l_str_color, l_bg_color);
                else
                    initRichString(l_font,l_anchor,l_content,l_str_color,l_bg_color);
            }
        }
        return this;
    }

    
    public Label setFlipedX(){
        l_bFlipedX = !l_bFlipedX;
        if(l_Image != null){
            /*Image tmp = l_Image;
            int w = tmp.getWidth();
            int h = tmp.getHeight();
            int [] rgb1 = new int[w * h];
            int [] rgb2 = new int[w * h];
            tmp.getRGB(rgb1, 0, w, 0, 0, w, h);
            
            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    rgb2[(w-i-1)+j*w] = rgb1[i+j*w];
                }
            }
            l_Image = Image.createRGBImage(rgb2, w, h, true);*/
            l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), javax.microedition.lcdui.game.Sprite.TRANS_MIRROR);
        }
        return this;
    }
    public Label setFlipedY(){
        l_bFlipedY = !l_bFlipedY;
        if(l_Image != null){
            l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), 
                    l_Image.getHeight(), 
                    javax.microedition.lcdui.game.Sprite.TRANS_MIRROR 
                    | javax.microedition.lcdui.game.Sprite.TRANS_ROT180);
        }
        return this;
    }
    public Label setFlipedXY(){
        l_bFlipedX = !l_bFlipedX;
        l_bFlipedY = !l_bFlipedY;
        if(l_Image != null){
            l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), 
                    l_Image.getHeight(), 
                    javax.microedition.lcdui.game.Sprite.TRANS_ROT180);
        }
        return this;
    }
    private void fiped(){
        if(l_Image != null && (l_bFlipedX || l_bFlipedY)){
            if(l_bFlipedX && l_bFlipedY){
                setFlipedXY();
            }else if(l_bFlipedX){
                setFlipedX();
            }else{
                setFlipedY();
            }
        }
    }
    
    /**
     * 逆时针
     * @return 
     */
    public Label setRotateCCW90(){
        l_rotate += 270;
        if(l_rotate >= 360){
            l_rotate -= 360;
        }
        rotate(270);
        return this;
    }
    /**
     * 顺时针
     * @return 
     */
    public Label setRotateCW90(){
        l_rotate += 90;
        if(l_rotate >= 360){
            l_rotate -= 360;
        }
        rotate(90);
        return this;
    }
    public Label setRotate180(){
        l_rotate += 180;
        if(l_rotate >= 360){
            l_rotate -= 360;
        }
        rotate(180);
        return this;
    }
    private void rotate(int r){
        if(l_Image != null){
            switch(r){
                case 90:
                    l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), javax.microedition.lcdui.game.Sprite.TRANS_ROT90);
                    break;
                case 180:
                    l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), javax.microedition.lcdui.game.Sprite.TRANS_ROT180);
                    break;
                case 270:
                    l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), javax.microedition.lcdui.game.Sprite.TRANS_ROT270);
                    break;
                default:break;
            }
        }
    }
    
    void transform(){
        if(l_Image != null){
            int r = 0;
            boolean bMirror = false;
            if(l_bFlipedX && l_bFlipedY){
                r += 180;
            }else if(l_bFlipedX){
                bMirror = true;
            }else if(l_bFlipedY){
                r += 180;
            }
            r += l_rotate;
            if(r >= 360){
                r -= 360;
            }
            int tf;
            switch(r){
                case 90:
                    tf = bMirror ? javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT90 : javax.microedition.lcdui.game.Sprite.TRANS_ROT90;
                    l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), tf);
                    break;
                case 180:
                    tf = bMirror ? javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT180 : javax.microedition.lcdui.game.Sprite.TRANS_ROT180;
                    l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), tf);
                    break;
                case 270:
                    tf = bMirror ? javax.microedition.lcdui.game.Sprite.TRANS_MIRROR_ROT270 : javax.microedition.lcdui.game.Sprite.TRANS_ROT270;
                    l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), tf);
                    break;
                default:
                    if(bMirror){
                        l_Image = Image.createImage(l_Image, 0,0, l_Image.getWidth(), l_Image.getHeight(), javax.microedition.lcdui.game.Sprite.TRANS_MIRROR);
                    }
                    break;
            }
        }
    }
    
}

