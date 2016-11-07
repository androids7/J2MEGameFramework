package framework;

import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author chizl
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
                if(node.n_KeyEvent != null && node.n_bShowed){
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
    
    protected void drawSelf(Graphics g){
        g.setColor(0xff00ff);
        String msg = "fps:" + UiThread.getInstance().curFps;
        g.drawString(msg,8, 8, 0);
        long tm = Runtime.getRuntime().totalMemory();
        long fm = Runtime.getRuntime().freeMemory();
        long um = (tm - fm)/1024;
        msg = "memory : total->" + (tm/1024) + "k used->" + um + "k";
        g.drawString(msg, 8, 8 + g.getFont().getHeight() + 2, Graphics.TOP | Graphics.LEFT);
        msg = "draw cell : " + UiThread.getInstance().getDrawCell();
        g.drawString(msg, 8, 8 + g.getFont().getHeight()*2 + 2, Graphics.TOP | Graphics.LEFT);
        drawCell+=3;
    }
}
