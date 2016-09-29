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
public class Delay extends Animation{
   
    public Delay(){}
    
    public void update(long dt) {
        if(mStatus < 0){
            mStatus = 0;
            mStartTime = System.currentTimeMillis();
            mEndTime += mStartTime;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
        }else if(mStatus == 0){
            this.mStartTime += dt;
            if(this.mStartTime >= this.mEndTime){
                mStatus = 1;
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
