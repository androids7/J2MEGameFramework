package framework;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author chizl
 */
public abstract class App extends MIDlet{

    public static App Instance = null;
    public static App getInstance(){
        return Instance;
    }

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("... startApp ...");
        Instance = this;
        initApp();
        UiThread.getInstance().start();
        UiThread.getInstance().runOnUiThread(new Runnable(){
            public void run(){
                onStart();
                resume();
            }
        });
    }
    
    private void initApp(){
        String fps = this.getAppProperty("p_fps");
        if(fps != null){
            Configs.fps = Short.parseShort(fps);
        }
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
    
    public String getAppProperty(String name,String def){
        String property = this.getAppProperty(name);
        return property == null ? def : property;
    }
    
    abstract protected void onStart();
    abstract protected void onPause();
    abstract protected void onResume();
    abstract protected void onDestroy();
    
}
