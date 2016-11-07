package framework;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author chizl
 */
public class UiThread {
    
    static boolean bExited = false;
    public short curFps = Configs.fps;
    long mStartTime = 0;
    long updateTime;
    
    Runnable mRunnableList[] = new Runnable[10];
    short mRunnableNums = 0;
    
    Object obj;
    
    private UiThread(){
        
    }
    static UiThread mUiThread = null;
    public static UiThread getInstance(){
        if(mUiThread == null)
            mUiThread = new UiThread();
        return mUiThread;
    }
    
    public void start(){
        updateTime = System.currentTimeMillis();
        Display.getDisplay(App.getInstance()).setCurrent(getSceneCanvas());
        //getSceneCanvas().setFullScreenMode(true);
        Configs.screenWidth = getSceneCanvas().getWidth();
        Configs.screenHeight = getSceneCanvas().getHeight();
        Configs.screenCenter = new Vector(Configs.screenWidth/2, Configs.screenHeight/2);
    }
    
    public void runOnUiThread(Runnable r){
        synchronized(mRunnableList){
            if(mRunnableNums == mRunnableList.length){
                Runnable threads[] = mRunnableList;
                mRunnableList = new Runnable[mRunnableNums+10];
                for(short i=0;i<mRunnableNums;i++){
                    mRunnableList[i] = threads[i];
                }
            }
            mRunnableList[mRunnableNums++] = r;
        }
    }
    
    SceneCanvas mSceneCanvas = null;

    int drawCell = 0;
    public int getDrawCell(){
        return drawCell;
    }
    public class SceneCanvas extends Canvas{
        
        boolean bPainted = false;
        
        protected void paint(Graphics g) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            
            if(bPainted)return;
            bPainted = true;
            
            mStartTime = System.currentTimeMillis();
            Scene.drawCell = 0;
            
            // 执行UI线程任务
            if(mRunnableNums > 0){
                short len;
                Runnable threads[];
                synchronized(mRunnableList){
                    threads = mRunnableList;
                    len = mRunnableNums;
                    mRunnableList = new Runnable[10];
                    mRunnableNums = 0;
                }
                for(short i=0;i<len;i++){
                    threads[i].run();
                }
            }
            
            // update
            long upDt = System.currentTimeMillis() - updateTime;
            updateTime = System.currentTimeMillis();
            Timer.update(upDt);
            if(Scene.curScene != null){
                
                // 执行各Node的 update
                Scene.curScene.doUpdate(upDt);
 
                if(App.getInstance().bRuning){
                    // 执行各Node动画的 update
                    Scene.curScene.doAction(upDt);
                    // 清屏
                    g.setColor(0);
                    g.fillRect(0, 0, this.getWidth(), this.getHeight());
                    // 重绘当前场景
                    Scene.curScene.onDraw(g);
                    
                    //System.out.println("currentThread hashCode : "+Thread.currentThread().hashCode());
                }
            }
            
            drawCell = Scene.drawCell; // draw次数
            
            // 帧暂停
            long dt = System.currentTimeMillis() - mStartTime;
            if(dt < 1000/Configs.fps){
                dt = 1000/Configs.fps - dt;
                try {
                    Thread.sleep(dt);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }else{
                //System.out.println("print time over fps ...");
            }
            
            // 计算当前帧频
            dt = System.currentTimeMillis() - mStartTime;
            if(dt > 0){
                curFps = (short)(1000/dt);
            }else{
                curFps = Configs.fps;
            }
            
            bPainted = false;
            System.gc();
            if(!bExited)
                getSceneCanvas().repaint();
        }
        
        protected void keyPressed(int keyCode){
            //System.out.println("KeyEvent down : "+keyCode);
            if(Scene.curScene != null && App.getInstance().bRuning){
                Scene.curScene.doKeyDown(keyCode);
            }
        }
        
        protected void keyReleased(int keyCode){
            //System.out.println("KeyEvent up : "+keyCode);
            if(Scene.curScene != null && App.getInstance().bRuning){
                Scene.curScene.doKeyUp(keyCode);
            }
        }
        
        protected void keyRepeated(int keyCode){
            //System.out.println("KeyEvent press : "+keyCode);
            if(Scene.curScene != null && App.getInstance().bRuning){
                Scene.curScene.doKeyPress(keyCode);
            }
        }
        
    }
    
    public SceneCanvas getSceneCanvas(){
        if(mSceneCanvas == null){
            mSceneCanvas = new SceneCanvas();
            mSceneCanvas.setFullScreenMode(true);
        }
        return mSceneCanvas;
    }
    
    
}
