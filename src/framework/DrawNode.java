package framework;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author chizl
 */
public class DrawNode extends Node {
    
    byte mModel = -1;
    
    int mLineColor = 0xffffff;
    Rect mLineRect = null;
    
    String mString = null;
    
    private DrawNode(){}
    
    Font mFont = null;
    public static DrawNode createLabel(String msg){
        DrawNode node = new DrawNode();
        node.mModel = 0;
        node.mString = msg;
        return node;
    }
    
    public static DrawNode createRect(Rect rect){
        DrawNode node = new DrawNode();
        node.mModel = 1;
        node.n_rect = rect;
        return node;
    }
    public static DrawNode createRectFill(Rect rect,int lineWidth,int colorLine,int colorFill){
        DrawNode node = new DrawNode();
        node.mModel = 2;
        node.n_rect = rect;
        node.n_Color = colorFill;
        node.mLineColor = colorLine;
        if(lineWidth > 0){
            node.mLineRect = new Rect(rect.x - lineWidth,
                    rect.y - lineWidth,
                    rect.width + 2*lineWidth,
                    rect.height + 2*lineWidth);
        }
        return node;
    }
    
    public static DrawNode createCircle(short r){
        DrawNode node = new DrawNode();
        node.mModel = 3;
        node.n_rect = Rect.ZERO;
        node.n_rect.width = r;
        node.n_rect.height = r;
        return node;
    }
    public static DrawNode createCircleFill(int r,int lineWidth,int colorLine,int colorFill){
        DrawNode node = new DrawNode();
        node.mModel = 4;
        node.n_rect = Rect.ZERO.clone();
        node.n_rect.width = r;
        node.n_rect.height = r;
        node.n_Color = colorFill;
        node.mLineColor = colorLine;
        if(lineWidth > 0){
            node.mLineRect = new Rect(node.n_rect.x - lineWidth,
                    node.n_rect.y - lineWidth,
                    node.n_rect.width + 2*lineWidth,
                    node.n_rect.height + 2*lineWidth);
        }
        return node;
    }
    
    protected void drawSelf(Graphics g){
        switch(mModel){
            case 0:
                drawString(g);
                break;
            case 1:
                drawRect(g);
                break;
            case 2:
                drawRectFill(g);
                break;
            case 3:
                drawCircle(g);
                break;
            case 4:
                drawCircleFill(g);
                break;
            default:break;
        }
        
    }
    
    void drawString(Graphics g){
        if(mString != null && !mString.equals("")){
            Font font = this.getFont();
            int w = font.stringWidth(mString);
            int h = font.getHeight();
            
            int xx = this.x;
            int yy = this.y;
            if((this.n_align_model & Node.ALIGN_H_LEFT) != 0){
                
            }else if((this.n_align_model & Node.ALIGN_H_RIGHT) != 0){
                xx -= w;
            }else{
                xx -= w/2;
            }
            if((this.n_align_model & Node.ALIGN_V_TOP) != 0){
                
            }else if((this.n_align_model & Node.ALIGN_V_BOTTOM) != 0){
                yy -= h;
            }else{
                yy -= h/2;
            }
            
            setGraphicsCip(g,xx, yy,w,h);
            g.setColor(n_Color);
            g.drawString(mString,xx, yy,0);
            drawCell++;
        }
    }
    
    void drawRect(Graphics g){
        
        int xx = this.x + n_rect.x;
        int yy = this.y + n_rect.y;
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
            
        setGraphicsCip(g,xx, yy, n_rect.width+1, n_rect.height+1);
        g.setColor(n_Color);
        g.drawRect(xx, yy, n_rect.width, n_rect.height);
        drawCell++;
    }
    
    void drawRectFill(Graphics g){
        
        int xx = this.x + n_rect.x;
        int yy = this.y + n_rect.y;
        int xx2 = mLineRect != null ? this.x + mLineRect.x : 0;
        int yy2 = mLineRect != null ? this.y + mLineRect.y : 0;
        if((this.n_align_model & Node.ALIGN_H_LEFT) != 0){
            
        }else if((this.n_align_model & Node.ALIGN_H_RIGHT) != 0){
            if(mLineRect != null){
                xx2 -= mLineRect.width;
                xx -= mLineRect.width;
            }else{
                xx -= n_rect.width;
            }
        }else{
            if(mLineRect != null){
                xx2 -= mLineRect.width/2;
                xx -= mLineRect.width/2;
            }else{
                xx -= n_rect.width/2;
            }
        }
        if((this.n_align_model & Node.ALIGN_V_TOP) != 0){

        }else if((this.n_align_model & Node.ALIGN_V_BOTTOM) != 0){
            if(mLineRect != null){
                yy2 -= mLineRect.height;
                yy -= mLineRect.height;
            }else{
                yy -= n_rect.height;
            }
        }else{
            if(mLineRect != null){
                yy2 -= mLineRect.height/2;
                yy -= mLineRect.height/2;
            }else{
                yy -= n_rect.height/2;
            }
        }
        
        if(mLineRect != null){
            setGraphicsCip(g,xx2,yy2, mLineRect.width, mLineRect.height);
            g.setColor(mLineColor);
            g.fillRect(xx2, yy2, mLineRect.width, mLineRect.height);
            g.setColor(n_Color);
            g.fillRect(xx,yy,n_rect.width, n_rect.height);
            drawCell+=2;
        }else{
            setGraphicsCip(g,xx, yy, n_rect.width, n_rect.height);
            g.setColor(n_Color);
            g.fillRect(xx,yy,n_rect.width, n_rect.height);
            drawCell++;
        }
    }
    
    void drawCircle(Graphics g){
        int xx = this.x + n_rect.x;
        int yy = this.y + n_rect.y;
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
        
        setGraphicsCip(g,xx, yy, n_rect.width+1, n_rect.height+1);
        g.setColor(n_Color);
        g.drawRoundRect(xx, yy, n_rect.width, n_rect.height,n_rect.width, n_rect.height);
        drawCell++;
    }
    
    void drawCircleFill(Graphics g){
        
        int xx = this.x + n_rect.x + 1;
        int yy = this.y + n_rect.y + 1;
        int xx2 = mLineRect != null ? this.x + mLineRect.x + 1 : 0;
        int yy2 = mLineRect != null ? this.y + mLineRect.y + 1 : 0;
        if((this.n_align_model & Node.ALIGN_H_LEFT) != 0){
            
        }else if((this.n_align_model & Node.ALIGN_H_RIGHT) != 0){
            if(mLineRect != null){
                xx2 -= mLineRect.width;
                xx -= mLineRect.width;
            }else{
                xx -= n_rect.width;
            }
        }else{
            if(mLineRect != null){
                xx2 -= mLineRect.width/2;
                xx -= mLineRect.width/2;
            }else{
                xx -= n_rect.width/2;
            }
        }
        if((this.n_align_model & Node.ALIGN_V_TOP) != 0){

        }else if((this.n_align_model & Node.ALIGN_V_BOTTOM) != 0){
            if(mLineRect != null){
                yy2 -= mLineRect.height;
                yy -= mLineRect.height;
            }else{
                yy -= n_rect.height;
            }
        }else{
            if(mLineRect != null){
                yy2 -= mLineRect.height/2;
                yy -= mLineRect.height/2;
            }else{
                yy -= n_rect.height/2;
            }
        }
        
        if(mLineRect != null){
            setGraphicsCip(g,xx2, yy2, mLineRect.width+1, mLineRect.height+1);
            g.setColor(mLineColor);
            g.fillRoundRect(xx2,yy2,mLineRect.width, mLineRect.height, mLineRect.width, mLineRect.height);
            g.setColor(n_Color);
            g.fillRoundRect(xx, yy, 
                n_rect.width, n_rect.height,n_rect.width, n_rect.height);
            drawCell+=2;
        }else{
            setGraphicsCip(g,xx,yy, n_rect.width+1, n_rect.height+1);
            g.setColor(n_Color);
            g.fillRoundRect(xx,yy, 
                n_rect.width, n_rect.height,n_rect.width, n_rect.height);
            drawCell++;
        }
    }
    
    public Font getFont(){
        if(this.mModel != 0){
            return null;
        }else if(this.mFont == null){
            this.mFont = Font.getDefaultFont();
            int w = this.mFont.stringWidth(mString);
            int h = this.mFont.getHeight();
            this.n_rect = new Rect(this.x,this.y,w,h);
        }
        return this.mFont;
    }
    public DrawNode setFont(Font font){
        if(this.mModel == 0){
            if(font != null){
                this.mFont = font;
            }else{
                this.mFont = Font.getDefaultFont();
            }
            int w = this.mFont.stringWidth(mString);
            int h = this.mFont.getHeight();
            this.n_rect = new Rect(this.x,this.y,w,h);
        }else{
            System.err.println("DrawNode is not Label model");
        }
        return this;
    }
    public DrawNode setString(String msg){
        if(this.mModel == 0){
            mString = msg;
            int w = this.getFont().stringWidth(mString);
            int h = this.getFont().getHeight();
            this.n_rect = new Rect(this.x,this.y,w,h);
        }else{
            System.err.println("DrawNode is not Label model");
        }
        return this;
    }
}
