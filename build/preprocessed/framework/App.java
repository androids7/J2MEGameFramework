/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author Administrator
 */
public abstract class App extends MIDlet{

    static App mApp = null;
    public static App getInstance(){
        return mApp;
    }

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("... startApp ...");
        mApp = this;
        UiThread.getInstance().start();
        UiThread.getInstance().runOnUiThread(new Runnable(){
            public void run(){
                onStart();
                resume();
            }
        });
    }

    protected void pauseApp() {
        System.out.println("... pauseApp ...");
        pause();
    }

    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        System.out.println("... destroyApp ...");
        onDestroy();
    }
    
    protected boolean bRuning = false;
    public void pause(){
        if(bRuning){
            bRuning = false;
            onPause();
        }
    }
    public void resume(){
        if(!bRuning){
            bRuning = true;
            onResume();
        }
    }
    
    abstract protected void onStart();
    abstract protected void onPause();
    abstract protected void onResume();
    abstract protected void onDestroy();
    
}
