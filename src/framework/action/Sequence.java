package framework.action;

import framework.Node;
import framework.action.Animation.IAction;

/**
 *
 * @author chizl
 */
public class Sequence extends Animation implements IAction{

    private Sequence(){}
    
    public void update(long dt) {
        if(mStatus == STATUS_UN_START){
            mStatus = STATUS_START;
            curIndex = 0;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
        }
        if(mStatus != STATUS_COMPLETED && mAnis != null && curIndex<mLen){
            Animation ani = mAnis[curIndex];
            ani.update(dt);
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
    
    Animation[] mAnis = null;
    byte mLen = 0;
    byte curIndex = 0;
    
    public Sequence addAction(Animation ani){
        if(ani == null)return this;
        if(mAnis == null){
            mAnis = new Animation[3];
            mLen = 0;
        }
        if(mLen == mAnis.length){
            Animation[] tmp = mAnis;
            mAnis = new Animation[mLen+3];
            System.arraycopy(tmp, 0, mAnis, 0, mLen);
        }
        mAnis[mLen++] = ani;
        if(a_node != null){
            ani.mListener = this;
            ani.setNode(a_node);
        }
        return this;
    }
    
    public void setNode(Node node){
        a_node = node;
        if(mAnis != null){
            for(int i=0;i<mAnis.length;i++){
                if(mAnis[i] != null){
                    mAnis[i].setNode(node);
                }
            }
        }
    }
    
    public static Sequence create(){
        Sequence seq = new Sequence();
        return seq;
    }
        
    public static Sequence create(Animation[] ans){
        Sequence seq = new Sequence();
        if(ans != null){
            seq.mAnis = new Animation[ans.length];
            for(int i=0;i<ans.length;i++){
                if(ans[i] != null){
                    ans[i].mListener = seq;
                    seq.mAnis[seq.mLen++] = ans[i];
                }
            }
        }
        return seq;
    }

    public void onActionEnter(Animation ani) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onActionExit(Animation ani) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        curIndex++;
    }
    
    public void reset(){
        mStatus = STATUS_UN_START;
        if(mAnis != null){
            for(int i=0;i<mAnis.length;i++){
                if(mAnis[i] != null){
                    mAnis[i].reset();
                }
            }
        }
    }
    
}
