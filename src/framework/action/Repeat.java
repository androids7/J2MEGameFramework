/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.action;

import framework.Node;
import framework.action.Animation.IAction;

/**
 *
 * @author Administrator
 */
public class Repeat extends Animation implements IAction{

    private Repeat(){}
    public void update(long dt) {
        if(mStatus == STATUS_UN_START){
            mStatus = STATUS_START;
            mRepCount = 0;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
        }
        if(mStatus != STATUS_COMPLETED){
            if(mAnimation != null){
                mAnimation.update(dt);
            }else{
                mStatus = STATUS_COMPLETED;
                if(onCompleted != null){
                    onCompleted.run();
                }
                if(mListener != null){
                    mListener.onActionExit(this);
                }
            }
        }
    }
    
    Animation mAnimation = null;
    int mRepCount = 0;
    int mRepTimers = -1;
    public static Repeat create(Animation ani){
        Repeat rep = new Repeat();
        rep.mAnimation = ani;
        ani.mListener = rep;
        return rep;
    }
    public static Repeat create(Animation ani,int timer){
        Repeat rep = new Repeat();
        rep.mAnimation = ani;
        rep.mRepTimers = timer;
        ani.mListener = rep;
        return rep;
    }
    
    public void setNode(Node node){
        a_node = node;
        mAnimation.setNode(node);
    }

    public void onActionEnter(Animation ani) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onActionExit(Animation ani) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        boolean bEnded = true;
        if(mRepTimers < 0){
            bEnded = false;
        }else{
            mRepCount++;
            if(mRepCount < mRepTimers){
                bEnded = false;
            }
        }
        if(!bEnded){
            ani.reset();
        }else{
            if(mStatus != STATUS_COMPLETED){
                mStatus = STATUS_COMPLETED;
                if(onCompleted != null){
                    onCompleted.run();
                }
                if(mListener != null){
                    mListener.onActionExit(this);
                }
            }
        }
    }
    
}
