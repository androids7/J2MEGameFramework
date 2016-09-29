/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import framework.action.MoveTo;
import framework.action.Animation;
import framework.*;
import framework.action.Blink;
/**
 *
 * @author Administrator
 */
public class Main extends App{

    protected void onStart() {
        
        Scene.run(new Scene());
        
        DrawNode node =  DrawNode.createTTF("aaaaa");
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

        node =  DrawNode.createCircleFill((short)60,(short)3,0xff0f00,0xff);
        node.setPos(50, 200);
        Scene.getCurScene().addChild(node);

        final Sprite sp =  Sprite.create("/logo1.png");
        node.addChild(sp);
        sp.setLocalPos(0, 0);

        final Sprite sp2 =  Sprite.create("/logo1.png");
        node.addChild(sp2);
        sp2.setLocalPos(-20, 30);

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
        node.runAction(a);
        Animation b = Blink.create(4, 150);
        b.onCompleted = new Runnable(){
            public void run(){
                App.getInstance().pause();
            }
        };
        node.runAction(b);
        
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
    }

    protected void onPause() {
        
    }
    
    protected void onResume() {
        
    }

    protected void onDestroy() {
        
    }
}
