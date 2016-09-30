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
    
    private DrawNode(){}
    
    public static DrawNode createTTF(String msg){
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
    public static DrawNode createRectFill(Rect rect,short lineWidth,int colorLine,int colorFill){
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
    public static DrawNode createCircleFill(short r,short lineWidth,int colorLine,int colorFill){
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
    
    String mString = null;
    void drawString(Graphics g){
        if(mString != null && !mString.equals("")){
            Font font = g.getFont();
            int w = font.stringWidth(mString);
            int h = font.getHeight();
            g.setClip(this.x, this.x,w,h);
            g.setColor(n_Color);
            g.drawString(mString,this.x, this.x,0);
        }
    }
    
    void drawRect(Graphics g){
        g.setClip(this.x + n_rect.x - n_rect.width/2, this.y + n_rect.y - n_rect.height/2, n_rect.width+1, n_rect.height+1);
        g.setColor(n_Color);
        g.drawRect(this.x + n_rect.x - n_rect.width/2, this.y + n_rect.y - n_rect.height/2, n_rect.width, n_rect.height);
    }
    
    void drawRectFill(Graphics g){
        if(mLineRect != null){
            g.setClip(this.x + mLineRect.x - mLineRect.width/2, this.y + mLineRect.y - mLineRect.height/2, mLineRect.width, mLineRect.height);
            g.setColor(mLineColor);
            g.fillRect(this.x + mLineRect.x - mLineRect.width/2, this.y + mLineRect.y - mLineRect.height/2, mLineRect.width, mLineRect.height);
            g.setColor(n_Color);
            g.fillRect(this.x + n_rect.x-n_rect.width/2 - (mLineRect.width - n_rect.width)/2,
                    this.y + n_rect.y - n_rect.height/2 - (mLineRect.height - n_rect.height)/2,
                    n_rect.width, n_rect.height);
        }else{
            g.setClip(this.x + mLineRect.x, this.y + mLineRect.y, mLineRect.width, mLineRect.height);
            g.setColor(n_Color);
            g.fillRect(this.x + n_rect.x-n_rect.width/2,
                    this.y + n_rect.y - n_rect.height/2,
                    n_rect.width, n_rect.height);
        }
    }
    
    void drawCircle(Graphics g){
        g.setClip(this.x + n_rect.x - n_rect.width/2, this.y + n_rect.y - n_rect.height/2, n_rect.width+1, n_rect.height+1);
        g.setColor(n_Color);
        g.drawRoundRect(this.x + n_rect.x - n_rect.width/2, this.y + n_rect.y - n_rect.height/2, n_rect.width, n_rect.height,n_rect.width, n_rect.height);
    }
    
    void drawCircleFill(Graphics g){
        if(mLineRect != null){
            g.setClip(this.x + mLineRect.x - mLineRect.width/2, this.y + mLineRect.y - mLineRect.height/2, mLineRect.width+1, mLineRect.height+1);
            g.setColor(mLineColor);
            g.fillRoundRect(this.x + mLineRect.x - mLineRect.width/2, 
                    this.y + mLineRect.y - mLineRect.height/2
                    , mLineRect.width, mLineRect.height, mLineRect.width, mLineRect.height);
            g.setColor(n_Color);
            g.fillRoundRect(this.x + n_rect.x - mLineRect.width/2
                    , this.y + n_rect.y - mLineRect.height/2
                    , n_rect.width, n_rect.height,n_rect.width, n_rect.height);
        }else{
            g.setClip(this.x + n_rect.x - n_rect.width/2, this.y + n_rect.y - n_rect.height/2, n_rect.width+1, n_rect.height+1);
            g.setColor(n_Color);
            g.fillRoundRect(this.x + n_rect.x - n_rect.width/2 , 
                    this.y + n_rect.y - n_rect.height/2, 
                    n_rect.width, n_rect.height,n_rect.width, n_rect.height);
        }
    }

}
