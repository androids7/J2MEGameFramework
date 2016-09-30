package framework;

/**
 *
 * @author chizl
 */
public class Timer {
    
    private Timer(){}
    Runnable m_Runnable = null;
    
    long m_cur_count = 0;
    long m_max_count = 0;
    byte bRepet = 0;
    byte bRepetMax = 0;
    boolean bStart = true;
    private boolean updateCount(long dt){
        if(!bStart)return false;
        m_cur_count += dt;
        if(m_cur_count >= m_max_count){
            if(m_Runnable != null){
                m_Runnable.run();
            }
            if(bRepet < 0){
                m_cur_count = 0;
            }else if(bRepet == 0){
                m_cur_count = 0;
                return true;
            }else{
                bRepet--;
                m_cur_count = 0;
            }
        }
        return false;
    }
    public void stop(){
        bStart = false;
        if(m_Timer != null){
            for(int i=0;i<m_Timer.length;i++){
                if(m_Timer[i] == this){
                    m_Timer[i] = null;
                }
            }
        }
    }
    public void pause(){
        bStart = false;
    }
    public void resume(){
        bStart = true;
    }
    public void restart(){
        bStart = true;
        m_cur_count = 0;
        bRepet = bRepetMax;
    }
    public static Timer create(long dt,Runnable callback){
        if(dt <= 0 || callback == null){
            System.out.println("create Timer error!  condition 'dt > 0 && callback != null && count > 0'");
            return null;
        }
        Timer t = new Timer();
        t.m_max_count = dt;
        t.m_Runnable = callback;
        addTimer(t);
        return t;
    }
    public static Timer create(long dt,Runnable callback,boolean bRepeat){
        if(dt <= 0 || callback == null){
            System.out.println("create Timer error!  condition 'dt > 0 && callback != null'");
            return null;
        }
        Timer t = new Timer();
        t.m_max_count = dt;
        t.bRepetMax = (byte)(bRepeat ? -1 : 0);
        t.bRepet = t.bRepetMax;
        t.m_Runnable = callback;
        addTimer(t);
        return t;
    }
    public static Timer create(long dt,Runnable callback,int count){
        if(dt <= 0 || callback == null || count < 1){
            System.out.println("create Timer error!  condition 'dt > 0 && callback != null && count > 0'");
            return null;
        }
        Timer t = new Timer();
        t.m_max_count = dt;
        t.bRepetMax = (byte)(count-1);
        t.bRepet = t.bRepetMax;
        t.m_Runnable = callback;
        addTimer(t);
        return t;
    }
    
    static void addTimer(Timer t){
        if(t == null)return;
        if(m_Timer == null){
            m_Timer = new Timer[3];
            m_index = 0;
        }
        if(m_index == m_Timer.length){
            Timer[] tmp = m_Timer;
            m_Timer = new Timer[m_index+3];
            System.arraycopy(tmp, 0, m_Timer, 0, m_index);
        }
        m_Timer[m_index++] = t;
    }
    
    private static Timer[] m_Timer = null;
    private static short m_index = 0;
    protected static void update(long dt){
        if(m_Timer != null){
            boolean flg = true;
            for(int i=0;i<m_Timer.length;i++){
                if(m_Timer[i] != null){
                    if(m_Timer[i].updateCount(dt)){
                        m_Timer[i] = null;
                    }else{
                        flg = false;
                    }
                }
            }
            if(flg){
                m_index = 0;
                m_Timer = null;
            }
        }
    }
    
}
