package framework.action;

import framework.Node;

/**
 *
 * @author chizl
 */
public abstract class Animation {
    
    long mStartTime = 0;
    long mEndTime = 0;
    
    protected static final byte STATUS_UN_START = -1;
    protected static final byte STATUS_START = 0;
    protected static final byte STATUS_COMPLETED = 1;
    byte mStatus = STATUS_UN_START;
    
    protected IAction mListener;
    
    abstract public void update(long dt);
    
    public byte getStatus(){
        return mStatus;
    }
    public boolean isCompleted(){
        return mStatus == STATUS_COMPLETED;
    }
    public void reset(){
        mStatus = STATUS_UN_START;
    }
    
    protected Node a_node;
    public void setNode(Node node){
        a_node = node;
    }
    
    protected Runnable onCompleted = null;
    public Animation setOnCompleteListener(Runnable r){
        onCompleted = r;
        return this;
    }

    protected interface IAction{
        public void onActionEnter(Animation ani);
        public void onActionExit(Animation ani);
    }

}
