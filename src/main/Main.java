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
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
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

        node =  DrawNode.createRectFill(new Rect(0,0,40,30),(short)3,0xffff00,0xff);
        node.setPos(80, 120);
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
        
        Timer.create(2000, new Runnable() {
            public void run() {
                label.setString("aaaaaaaaaa");
                label.setRotateCCW90();
                Font f = Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN, Font.SIZE_LARGE);
                label.setFont(f);
                //Scene.getCurScene().setCliped(new Rect(0,0,400,300));
            }
        });
        

    }

    protected void onPause() {
        
    }
    
    protected void onResume() {
        
    }

    protected void onDestroy() {
        
    }
}
