package framework;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author chizl
 */
public class Sprite extends Node{
    
    Image s_Image = null;
    
    Image s_Frames[] = null;
    int s_FrameTimes = -1;
    int s_FrameCurDelayTime = -1;
    int s_FrameTotalDelayTime = -1;
    int s_curFrameIndex = 0;
    
    boolean s_bFlipedX = false;
    boolean s_bFlipedY = false;
    
    int s_rotate = 0;
    
    private Sprite(){}

    protected void drawSelf(Graphics g){
        if(s_Image != null){
            setGraphicsCip(g,this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, s_Image.getWidth(), s_Image.getHeight());
            g.setColor(n_Color);
            g.drawImage(s_Image, this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, 0);
        }
    }
    
    public static Sprite create(String file){
        return create(makeImage(file));
    }
    
    public static Sprite create(Image image){
        Sprite sp = new Sprite();
        sp.setImage(image);
        return sp;
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
    
    public Sprite setImage(Image image){
        this.s_Image = image;
        if(this.s_Image != null){
            this.n_rect = new Rect(this.x - s_Image.getWidth()/2, this.y - s_Image.getHeight()/2, s_Image.getWidth(), s_Image.getHeight());
        }else{
            this.n_rect = Rect.ZERO;
        }
        fiped();
        rotate(s_rotate);
        return this;
    }
    
    public Image getImage(){
        return this.s_Image;
    }
    
    /**
     * Sprite帧动画
     * @param 图片数组
     * @param 每一帧的间隔时间
     * @param 循环次数，<=0 : 无限循环， 
     */
    public void runSpriteFrames(Image[] images,int delayTime,int times){
        this.s_Frames = images;
        this.s_FrameTotalDelayTime = delayTime;
        this.s_FrameTimes = times - 1;
        this.s_curFrameIndex = 0;
    }
    
    protected void animUpdate(long dt){
        super.animUpdate(dt);
        if(s_Frames != null){
            s_FrameCurDelayTime += dt;
            if(s_FrameCurDelayTime >= s_FrameTotalDelayTime){
                if(s_curFrameIndex == s_Frames.length){
                    s_curFrameIndex = 0;
                    if(s_FrameTimes < 0){
                        s_Image = s_Frames[s_curFrameIndex];
                    }else if(s_FrameTimes == 0){
                        s_Frames = null;
                    }else{
                        s_FrameTimes--;
                        s_Image = s_Frames[s_curFrameIndex];
                    }
                }else{
                    s_Image = s_Frames[s_curFrameIndex++];
                }
                s_FrameCurDelayTime = 0;
            }
        }
    }
    
    public Sprite setFlipedX(){
        s_bFlipedX = !s_bFlipedX;
        if(s_Image != null){
            Image tmp = s_Image;
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
            s_Image = Image.createRGBImage(rgb2, w, h, true);
        }
        return this;
    }
    public Sprite setFlipedY(){
        s_bFlipedY = !s_bFlipedY;
        if(s_Image != null){
            Image tmp = s_Image;
            int w = tmp.getWidth();
            int h = tmp.getHeight();
            int [] rgb1 = new int[w * h];
            int [] rgb2 = new int[w * h];
            tmp.getRGB(rgb1, 0, w, 0, 0, w, h);
            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    rgb2[i+(h-1-j)*w] = rgb1[i+j*w];
                }
            }
            s_Image = Image.createRGBImage(rgb2, w, h, true);
        }
        return this;
    }
    public Sprite setFlipedXY(){
        s_bFlipedX = !s_bFlipedX;
        s_bFlipedY = !s_bFlipedY;
        if(s_Image != null){
            Image tmp = s_Image;
            int w = tmp.getWidth();
            int h = tmp.getHeight();
            int [] rgb1 = new int[w * h];
            int [] rgb2 = new int[w * h];
            tmp.getRGB(rgb1, 0, w, 0, 0, w, h);
            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    rgb2[(w-i-1)+(h-1-j)*w] = rgb1[i+j*w];
                }
            }
            s_Image = Image.createRGBImage(rgb2, w, h, true);
        }
        return this;
    }
    private void fiped(){
        if(s_Image != null && (s_bFlipedX || s_bFlipedY)){
            Image tmp = s_Image;
            int w = tmp.getWidth();
            int h = tmp.getHeight();
            int [] rgb1 = new int[w * h];
            int [] rgb2 = new int[w * h];
            tmp.getRGB(rgb1, 0, w, 0, 0, w, h);
            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    int index = (s_bFlipedX ? (w-i-1) : i) 
                            + (s_bFlipedY ? (h-1-j)*w : j * w);
                    rgb2[index] = rgb1[i+j*w];
                }
            }
            s_Image = Image.createRGBImage(rgb2, w, h, true);
        }
    }
    
    /**
     * 顺时针
     * @return 
     */
    public Sprite setRotateCW90(){
        s_rotate += 90;
        if(s_rotate > 180){
            s_rotate = 180-s_rotate;
        }
        rotate(90);
        return this;
    }
    /**
     * 逆时针
     * @return 
     */
    public Sprite setRotateCCW90(){
        s_rotate -= 90;
        if(s_rotate < -180){
            s_rotate = -180-s_rotate;
        }
        rotate(-90);
        return this;
    }
    public Sprite setRotate180(){
        s_rotate += 180;
        if(s_rotate > 180){
            s_rotate = 180-s_rotate;
        }
        rotate(180);
        return this;
    }
    private void rotate(int r){
        if(s_Image != null){
            Image tmp = s_Image;
            int w = tmp.getWidth();
            int h = tmp.getHeight();
            int [] rgb1 = new int[w * h];
            int [] rgb2 = new int[w * h];
            tmp.getRGB(rgb1, 0, w, 0, 0, w, h);
            switch(r){
                case -90:
                    for(int i=0;i<w;i++){
                        for(int j=0;j<h;j++){
                            rgb2[h-1-j + i * h] = rgb1[i+j*w];
                        }
                    }
                    s_Image = Image.createRGBImage(rgb2, h, w, true);
                    break;
                case 90:
                    for(int i=0;i<w;i++){
                        for(int j=0;j<h;j++){
                            rgb2[j + (w - 1 - i) * h] = rgb1[i+j*w];
                        }
                    }
                    s_Image = Image.createRGBImage(rgb2, h, w, true);
                    break;
                case -180:
                case 180:
                    for(int i=0;i<w;i++){
                        for(int j=0;j<h;j++){
                            rgb2[w-1-i + (h-1-j)*w] = rgb1[i+j*w];
                        }
                    }
                    s_Image = Image.createRGBImage(rgb2, w, h, true);
                    break;
                default:break;
            }
        }
    }
    
    /*
    public Sprite setRotate(int ratio){
        if(s_Image != null){
            Image tmp = s_Image;
            int w = tmp.getWidth();
            int h = tmp.getHeight();
            int [] rgb1 = new int[w * h];
            tmp.getRGB(rgb1, 0, w, 0, 0, w, h);
            double cos = Math.cos(Math.toRadians(ratio));
            double sin = Math.cos(Math.toRadians(ratio));
            System.out.println("setRotate sin:"+sin+" cos:"+cos);
            int w2 = (int)(w*cos + h*sin);
            int h2 = (int)(w*sin + h*cos);
            System.out.println("setRotate w2:"+w2+" h2:"+h2);
            int [] rgb2 = new int[w2 * h2];
            for(int i=0;i<w2;i++){
                for(int j=0;j<h2;j++){
                    rgb2[i + j*w2] = 0;
                }
            }
            int x,y;
            for(int i=0;i<w;i++){
                for(int j=0;j<h;j++){
                    x = (int)Math.floor((i-w/2)*cos - (j-h/2)*sin + 0.5f + w2/2.0f);
                    y = (int)Math.floor((i-w/2)*sin + (j-h/2)*cos + 0.5f + h2/2.0f);
                    //System.err.println("---> x="+x+" y="+y);
                    if(x + y*w2 < rgb2.length && x + y*w2 >= 0)
                        rgb2[x + y*w2] = rgb1[i+j*w];
                    else
                        System.err.println("outside rgb2 length :" + (x + y*w2));
                }
            }
            
            int left,right,up,down; 
            
            for(int i=0;i<w2;i++){
                for(int j=0;j<h2;j++){
                    if(rgb2[i +j*w2] != 0)continue;
                    left = i - 1;
                    right = i + 1;
                    up = j - 1;
                    down = j + 1;
                    int num = 0;
                    int a = 0;
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for(int ii=left;ii<=right && ii<w2;ii++){
                        if(ii < 0)continue;
                        for(int jj=up;jj<=down && jj<h2;jj++){
                            if(jj < 0)continue;
                            if(rgb2[ii +jj*w2] != 0){
                                num++;
                                a += (rgb2[ii +jj*w2] >> 8*3) & 0xff;
                                r += (rgb2[ii +jj*w2] >> 8*2) & 0xff;
                                g += (rgb2[ii +jj*w2] >> 8*1) & 0xff;
                                b += (rgb2[ii +jj*w2]) & 0xff;
                            }
                        }
                    }
                    if(num >= 6){
                        a /= num;
                        r /= num;
                        g /= num;
                        b /= num;
                        int color = (a << (8*3)) + (r << (8*2)) + (g << (8*1)) + (b);
                        System.err.println("---> num : "+num + " color : " + color);
                        rgb2[i +j*w2] = color;
                    }
                }
            }
            
            left = 0;
            right = 0;
            up = 0;
            down = 0; 
            for(int i=0;i<w2;i++){
                boolean flg = false;
                for(int j=0;j<h2;j++){
                    if(rgb2[i +j*w2] != 0){
                        flg = true;
                        break;
                    }
                }
                if(flg){
                    break;
                }
                up++;
            }
            for(int i=0;i<w2;i++){
                boolean flg = false;
                for(int j=h2-1;j>=0;j--){
                    if(rgb2[i +j*w2] != 0){
                        flg = true;
                        break;
                    }
                }
                if(flg){
                    break;
                }
                down++;
            }
            for(int j=0;j<h2;j++){
                boolean flg = false;
                for(int i=0;i<w2;i++){
                    if(rgb2[i +j*w2] != 0){
                        flg = true;
                        break;
                    }
                }
                if(flg){
                    break;
                }
                left++;
            }
            for(int j=0;j<h2;j++){
                boolean flg = false;
                for(int i=w2-1;i>=0;i++){
                    if(rgb2[i +j*w2] != 0){
                        flg = true;
                        break;
                    }
                }
                if(flg){
                    break;
                }
                right++;
            }
            if(left != 0 || right != 0 ||
                    up != 0 || down != 0){
                rgb1 = rgb2;
                w2 -= left - right;
                h2 -= up - down;
                rgb2 = new int[w2*h2];
                for(int i=0;i<w2;i++){
                    for(int j=0;j<h2;j++){
                        rgb2[i+j*w2] = rgb1[i+left+(j+up)*w2];
                    }
                }
            }
            s_Image = Image.createRGBImage(rgb2, w2, h2, true);
        }
        return this;
    }
    */
    
    /*
    public static Sprite createTestSprite(){
        Sprite sp = new Sprite();
        int[] rgb = new int[50*50];
        for(int i=0;i<50;i++){
            for(int j=0;j<50;j++){
                rgb[i + j*50] = 0xcfffff00;             
            }
        }
        sp.s_Image = Image.createRGBImage(rgb, 50, 50, true);  // true: color = 0xaarrggbb false : color = 0xrrggbb
        return sp;
    }*/
    
}
