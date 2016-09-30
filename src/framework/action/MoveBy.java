package framework.action;

/**
 *
 * @author chizl
 */
public class MoveBy extends Animation{
    
    short endX,endY;
    int speedX,speedY;
    
    short curCount = 0;
    short maxCount = -1;
    
    int nx,ny;
    
    private MoveBy(){}
    
    public void update(long dt) {
        if(mStatus == STATUS_UN_START){
            mStatus = STATUS_START;
            if(mListener != null){
                mListener.onActionEnter(this);
            }
            mStartTime = System.currentTimeMillis();
            short x = this.a_node.getPosX();
            short y = this.a_node.getPosY();
            endX = (short)(x + mDx);
            endY = (short)(y + mDy);
            speedX = (int)((mDx)*1000/mdt);
            speedY = (int)((mDy)*1000/mdt);
            mEndTime = mStartTime + mdt;
            nx = this.a_node.getPosX()*1000 + (int)(speedX*dt);
            ny = this.a_node.getPosY()*1000 + (int)(speedY*dt);
            this.a_node.setPos((nx)/1000, (ny)/1000);
        }else if(mStatus == 0){
            this.mStartTime += dt;
            if(this.mStartTime >= this.mEndTime){
                mStatus = STATUS_COMPLETED;
                this.a_node.setPos(endX, endY);
                if(onCompleted != null){
                    onCompleted.run();
                }
                if(mListener != null){
                    mListener.onActionExit(this);
                }
            }else{
                nx += speedX*dt;
                ny += speedY*dt;
                this.a_node.setPos((nx)/1000, (ny)/1000);
            }
        }
    }
    long mdt = 0;
    short mDx,mDy;
    public static MoveBy create(int x,int y,long dt){
        MoveBy ani = new MoveBy();
        ani.mDx = (short)x;
        ani.mDy = (short)y;
        ani.mdt = dt;
        return ani;
    }

}
