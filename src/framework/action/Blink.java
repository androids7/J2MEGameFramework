package framework.action;

/**
 *
 * @author chizl
 */
public class Blink extends Animation{

    private Blink(){}
    public void update(long dt) {
        if(mStatus == STATUS_UN_START){
            mStatus = STATUS_START;
            b_nums = b_timer;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
            this.mStartTime = 0;
        }else if(mStatus == 0){
            this.mStartTime += dt;
            if(this.mStartTime >= this.mEndTime){
                this.mStartTime = 0;
                a_node.setVisible(!a_node.getVisible());
                if(--b_nums <= 0){
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
    
    byte b_nums = -1;
    byte b_timer = -1;
    public static Blink create(int nums,long dt){
        Blink b = new Blink();
        b.b_timer = (byte)(nums * 2);
        b.mEndTime = dt;
        return b;
    }
    
}
