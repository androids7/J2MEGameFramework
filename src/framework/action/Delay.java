package framework.action;

/**
 *
 * @author chizl
 */
public class Delay extends Animation{
   
    public Delay(){}
    
    public void update(long dt) {
        if(mStatus < 0){
            mStatus = STATUS_START;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
            mStartTime = System.currentTimeMillis();
            mEndTime += mStartTime;
        }else if(mStatus == 0){
            this.mStartTime += dt;
            if(this.mStartTime >= this.mEndTime){
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
    
    public static Delay create(long dt,Runnable rb){
        Delay delay = new Delay();
        delay.mEndTime = dt;
        delay.onCompleted = rb;
        return delay;
    }
    
}
