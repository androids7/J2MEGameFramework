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
public class AniDelay extends Animation{
   
    public AniDelay(){}
    
    public void update(long dt) {
        if(mStatus < 0){
            mStatus = 0;
            mStartTime = System.currentTimeMillis();
            mEndTime += mStartTime;
        }else if(mStatus == 0){
            this.mStartTime += dt;
            if(this.mStartTime >= this.mEndTime){
                mStatus = 1;
                if(mRunnable != null){
                    mRunnable.run();
                }
            }
        }
    }
    
    Runnable mRunnable = null;
    public static AniDelay create(long dt,Runnable rb){
        AniDelay delay = new AniDelay();
        delay.mEndTime = dt;
        delay.mRunnable = rb;
        return delay;
    }
    
}
