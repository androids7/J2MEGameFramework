/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package framework;

/**
 *
 * @author Administrator
 */
public abstract class Animation {
    
    long mStartTime = 0;
    long mEndTime = 0;
    
    byte mStatus = -1;
    
    protected IAction mListener;
    
    abstract public void update(long dt);
    
    public byte getStatus(){
        return mStatus;
    }
    
    protected Node a_node;
    public void setNode(Node node){
        a_node = node;
    }
    
    public Runnable onCompleted = null;

    public interface IAction{
        public void onActionEnter(Animation ani);
        public void onActionExit(Animation ani);
    }

}
