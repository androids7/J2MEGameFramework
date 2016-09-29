/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework;

import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author Administrator
 */
public class Scene extends Node {

    Hashtable _childs = new Hashtable();
    short _childNums = 0;
    
    boolean bEnterd = false;
    
    protected static Scene curScene = null;
    public static void run(Scene s){
        if(curScene != null){
            curScene.romoveNow();
        }
        curScene = s;
    }
    public static Scene getCurScene(){
        return curScene;
    }
    
    protected void doUpdate(long dt){
        doUpdateNode(dt,this.n_chd_head);
        if(this.n_bEnableUpdate){
            update(dt);
        }
    }
    private void doUpdateNode(long dt,Node node){
        while(node != null){
            doUpdateNode(dt,node.n_chd_head);
            if(node.n_bEnableUpdate){
                node.update(dt);
            }
            node = node.n_next;
        }
    }
    
    protected void doAction(long dt){
        doActionNode(dt,this.n_chd_head);
        animUpdate(dt);
    }
    private void doActionNode(long dt,Node node){
        while(node != null){
            doActionNode(dt,node.n_chd_head);
            node.animUpdate(dt);
            node = node.n_next;
        }
    }
    
    protected void doKeyDown(int keyCode){
        doKeyEventNode(this,0,keyCode);
    }
    protected void doKeyUp(int keyCode){
        doKeyEventNode(this,2,keyCode);
    }
    protected void doKeyPress(int keyCode){
        doKeyEventNode(this,1,keyCode);
    }
    private boolean doKeyEventNode(Node node,int type,int keyCode){
        while(node != null){
            if(!doKeyEventNode(node.n_chd_head,type,keyCode)){
                if(node.n_KeyEvent != null){
                    switch(type){
                        case 0:
                            if(node.n_KeyEvent.onKeyDown(keyCode)){
                                return true;
                            }
                            break;
                        case 1:
                            if(node.n_KeyEvent.onKeyPress(keyCode)){
                                return true;
                            }
                            break;
                        case 2:
                            if(node.n_KeyEvent.onKeyUp(keyCode)){
                                return true;
                            }
                            break;
                        default:break;
                    }
                }
            }else{
                return true;
            }
            node = node.n_next;
        }
        return false;
    }
    
    public void update(long dt){
        System.out.println("... update:"+dt);
    }
    
    /*
    public void onDraw(Graphics g){
        g.setColor(0xff00ff);
        String msg = "fps:" + UiThread.getInstance().curFps;
        g.drawChars(msg.toCharArray(), 0, msg.length(), 100, 50, 0);
    }
    */

    protected void drawSelf(Graphics g){
        g.setColor(0xff00ff);
        String msg = "fps:" + UiThread.getInstance().curFps;
        g.drawChars(msg.toCharArray(), 0, msg.length(), 8, 8, 0);
    }

}
