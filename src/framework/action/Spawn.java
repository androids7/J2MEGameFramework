package framework.action;

import framework.Node;

/**
 *
 * @author chizl
 */
public class Spawn extends Animation{

    private Spawn(){}
    
    public void update(long dt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(mStatus == STATUS_UN_START){
            mStatus = STATUS_START;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
        }
        if(mAnis != null && mStatus != STATUS_COMPLETED){
            boolean flg = false;
            for(int i=0;i<mAnis.length;i++){
                if(mAnis[i] != null && mAnis[i].mStatus != STATUS_COMPLETED){
                    mAnis[i].update(dt);
                    flg = true;
                }
            }
            if(!flg){
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
    
    public Spawn addAction(Animation ani){
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
    
    public static Spawn create(){
        Spawn sp = new Spawn();
        return sp;
    }
    public static Spawn create(Animation[] ans){
        Spawn sp = new Spawn();
        if(ans != null){
            sp.mAnis = new Animation[ans.length];
            for(int i=0;i<ans.length;i++){
                if(ans[i] != null){
                    sp.mAnis[sp.mLen++] = ans[i];
                }
            }
        }
        return sp;
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
