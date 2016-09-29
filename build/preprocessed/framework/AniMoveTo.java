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
public class AniMoveTo extends Animation{
    
    short endX,endY;
    int speedX,speedY;
    
    short curCount = 0;
    short maxCount = -1;
    
    int nx,ny;
    
    private AniMoveTo(){}
    
    public void update(long dt) {
        if(mStatus < 0){
            mStatus = 0;
            mStartTime = System.currentTimeMillis();
            short x = this.a_node.getPosX();
            short y = this.a_node.getPosY();
            speedX = (int)((endX - x)*1000/mEndTime);
            speedY = (int)((endY - y)*1000/mEndTime);
            
            mEndTime += mStartTime;
            
            nx = this.a_node.getPosX()*1000 + (int)(speedX*dt);
            ny = this.a_node.getPosY()*1000 + (int)(speedY*dt);
            
            this.a_node.setPos((nx)/1000, (ny)/1000);
            System.out.println("speedX:"+speedX);
            System.out.println("speedY:"+speedY);
            System.out.println("mStartTime:"+mStartTime);
            System.out.println("mEndTime:"+mEndTime);
        }else if(mStatus == 0){
            this.mStartTime += dt;
            if(this.mStartTime >= this.mEndTime){
                mStatus = 1;
                this.a_node.setPos(endX, endY);
            }else{
                nx += speedX*dt;
                ny += speedY*dt;
                this.a_node.setPos((nx)/1000, (ny)/1000);
            }
        }
    }
    
    public static AniMoveTo create(int x,int y,long dt){
        AniMoveTo ani = new AniMoveTo();
        ani.endX = (short)x;
        ani.endY = (short)y;
        ani.mEndTime = dt;
        return ani;
    }
   

}
