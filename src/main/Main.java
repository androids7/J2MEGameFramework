/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.sun.perseus.parser.ColorParser;
import framework.action.MoveTo;
import framework.action.Animation;
import framework.*;
import framework.action.Blink;
import framework.action.MoveBy;
import framework.action.Repeat;
import framework.action.Sequence;
import framework.action.Spawn;
import framework.net.Http;
import framework.net.IHttpEvent;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;
//import javax.microedition.lcdui.game.Sprite;
/**
 *
 * @author Administrator
 */
public class Main extends App{

    protected void onStart() {
        
        Scene.run(new Scene());

        DrawNode node =  DrawNode.createLabel("aaaaa");
        node.setPos((short)100, (short)50);
        node.setColor(0xff00ff);
        Scene.getCurScene().addChild(node);

        node =  DrawNode.createRect(new Rect(20,60,30,30));
        Scene.getCurScene().addChild(node);

        node =  DrawNode.createRectFill(new Rect(0,0,40,40),(short)3,0xffff00,0xff0000);
        node.setPos(80, 120);
        //node.setAlignModel(Node.ALIGN_H_LEFT,Node.ALIGN_V_TOP);
        node.setAlignModel(Node.ALIGN_H_LEFT,Node.ALIGN_V_BOTTOM);
        Scene.getCurScene().addChild(node);

        node =  DrawNode.createCircle((short)35);
        node.setPos(60, 160);
        Scene.getCurScene().addChild(node);

        node =  DrawNode.createCircleFill((short)100,(short)3,0xffffff,0xff00ff00);
        node.setPos(50, 200);
        Scene.getCurScene().addChild(node);

        final Sprite sp =  Sprite.create("/logo1.png");
        node.addChild(sp);
        sp.setLocalPos(0, 0);

        final Sprite sp2 =  Sprite.create("/logo1.png");
        node.addChild(sp2);
        sp2.setLocalPos(-20, 30);
        node.runAction(Repeat.create(Sequence.create(new Animation[]{MoveBy.create(0, 100, 600),MoveBy.create(0, -100, 600)})));
        final Node tmp = node;
        Animation a = MoveTo.create(300,300,2000);
        a.onCompleted = new Runnable(){
            public void run(){
                //tmp.setVisible(false);
                sp.setZorder(1);
                //sp.removeSelf();
                //tmp.removeSelf();
            }
        };
        //node.runAction(a);
        Animation b = Blink.create(4, 150);
        /*b.onCompleted = new Runnable(){
            public void run(){
                //App.getInstance().pause();
                Http.post("http://198.11.179.32/httpTest.php","a=132&b=654" ,new IHttpEvent() {
                    public void onHttpSuccess(byte[] data) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        System.out.println("onHttpSuccess:" + new String(data,0,data.length));
                    }

                    public void onHttpError(int code, String msg) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        System.out.println("onHttpError:" + code + " " + msg);
                    }
                });
            }
        };*/
        //node.runAction(b);
        Spawn asp = Spawn.create();
        asp.addAction(a);
        asp.addAction(b);
        //node.runAction(asp);

        Sequence seq = Sequence.create(new Animation[]{a,b});
        //node.runAction(seq
        //node.runAction(Repeat.create(a));
        //node.setCliped(new Rect(-80,-80,150,120));
        node.runAction(Repeat.create(Sequence.create(new Animation[]{MoveBy.create(0, 100, 600),MoveBy.create(0, -100, 600)})));

        tmp.setKeyEventListener(new IKeyEvent() {
            public boolean onKeyDown(int keyCode) {
                System.out.println("tmp keyDown : " + keyCode);
                return true;
            }

            public boolean onKeyPress(int keyCode) {
                System.out.println("tmp onKeyPress : " + keyCode);
                return true;
            }

            public boolean onKeyUp(int keyCode) {
                System.out.println("tmp onKeyUp : " + keyCode);
                return true;
            }
        });
        sp.setKeyEventListener(new IKeyEvent() {
            public boolean onKeyDown(int keyCode) {
                System.out.println("sp keyDown : " + keyCode);
                return false;
            }

            public boolean onKeyPress(int keyCode) {
                System.out.println("sp onKeyPress : " + keyCode);
                return true;
            }

            public boolean onKeyUp(int keyCode) {
                System.out.println("sp onKeyUp : " + keyCode);
                return true;
            }
        });

        /*final long timerStartTime = System.currentTimeMillis();
        Timer.create(2000, new Runnable() {
            public void run() {
                System.out.println("Timer ...:"+(System.currentTimeMillis() - timerStartTime));
            }
        },true);*/

        Button bt = Button.create("/button.png");
        bt.setLabel("Button");
        bt.setPos(450, 450);
        Scene.getCurScene().addChild(bt);

        Image[] frames = new Image[2];
        frames[0] = Sprite.makeImage("/button.png");
        frames[1] = Sprite.makeImage("/button.png");
        Sprite sp3 = Sprite.create(frames[0]);
        sp3.setPos(550, 450);
        //sp3.runSpriteFrames(frames,350, -1);
        Scene.getCurScene().addChild(sp3);
        //sp3.setFlipedXY();
        //sp3.setRotateLeft90();
        //sp3.setRotate180();
        //sp3.setRotateRight90();

        SlideBar sb = SlideBar.create("/loadgg1.png");
        sb.setPos(350, 450);
        sb.setValue(50);
        //sb.setModel(SlideBar.MODEL_VERTICAL_TOP);
        Scene.getCurScene().addChild(sb);

        //System.out.println("sin:"+Math.sin(Math.PI/2));

        final Label label = Label.createRich("  hello world!  ");
        label.setPos(350, 350);
        label.setFlipedY();
        //label.setRotateCCW90();
        Scene.getCurScene().addChild(label);
        
        /*byte [] data = null;
        try{
            InputStream in = App.getInstance().getClass().getResourceAsStream("/world.txt");
            int len = in.available();
            data = new byte[len];
            in.read(data);
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        FNTfont fnt = FNTfont.create("/number02.fnt");
        String u8 = null;
        try {
            //Image img = fnt.getCharImage('9');
            u8 = new String(data,"utf8");
            System.err.println("+++++++++++:"+u8);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        //Image img = fnt.getUTF8CharImage(u8);
        Image img = fnt.getCharImage('8');
        Sprite fntSp = Sprite.create(img);
        Scene.getCurScene().addChild(fntSp);
        fntSp.setPos(20, 20);*/
    }

    protected void onPause() {
        
    }
    
    protected void onResume() {
        
    }

    protected void onDestroy() {
        
    }
    
    void testRecordStore(){
        long t1 = System.currentTimeMillis();
        LocalData ld = LocalData.create("myLocalData3");
        
        /*boolean exists = ld.contains("key1");
        System.err.println("exists key1 : " + exists);
        if(!exists){
            ld.addKey("key1", "abcdefg".getBytes());
        }else{
            byte[] bs = ld.getKey("key1");
            if(bs != null){
                System.err.println("====  bs : " + new String(bs));
            }else{
                System.err.println("====  bs is null");
            }
        }*/
        
        if(ld.contains("bKey")){
            System.err.println("exists bKey : " + ld.getBoolean("bKey", false));
        }else{
            System.err.println("not exists bKey and set ");
            ld.setBoolean("bKey", true);
        }
        if(ld.contains("lKey")){
            System.err.println("exists lKey : " + ld.getLong("lKey", 1));
        }else{
            System.err.println("not exists lKey and set ");
            ld.setLong("lKey", 333333);
        }
        if(ld.contains("iKey")){
            System.err.println("exists iKey : " + ld.getInt("iKey", 1));
        }else{
            System.err.println("not exists iKey and set ");
            ld.setInt("iKey", 2222);
        }
        if(ld.contains("sKey")){
            System.err.println("exists sKey : " + ld.getString("sKey", "nil"));
        }else{
            System.err.println("not exists sKey and set ");
            ld.setString("sKey", "nnnnnnnnn");
        }
        //ld.clean();
        System.err.println("user time : " + (System.currentTimeMillis()-t1));
    }
    
    public static String fromatNumber(long value,int ratio){
        String res = "";
        switch(ratio){
            case 0:
                if(value > 10000){
                    long v1 = value/10000;
                    long v2 = value%10000;
                    v2 = v2/100;
                    res = v1+"."+v2+"Íò";
                }else{
                    res += value;
                }
                break;
            case 1:
                if(value > 1000000){
                    long v1 = value/1000000;
                    long v2 = value%1000000;
                    v2 = v2/10000;
                    res = v1+"."+v2+"°ÙÍò";
                }else{
                    res += value;
                }
                break;
            default:
                res += value;
                break;
        }
        return res;
    }
}
