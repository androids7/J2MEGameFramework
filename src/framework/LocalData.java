/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.util.Hashtable;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author czl
 */
public class LocalData {
    
    private LocalData(){}
    
    public static LocalData create(String fileName){
        LocalData ld = new LocalData();
        ld.init(fileName);
        return ld;
    }
    
    String ld_name = null;
    RecordStore m_RecordStore = null;
    int ld_model = RecordStore.AUTHMODE_ANY;
    boolean ld_writeable = true;
    
    public static final int AUTHMODE_ANY = RecordStore.AUTHMODE_ANY;
    public static final int AUTHMODE_PRIVATE = RecordStore.AUTHMODE_PRIVATE;
    
    private static final int SP_CH = 9;
    
    void init(String name){
        if(name == null || name.equals("")){
            name = "localData.save";
        }
        ld_name = name;
        try {
            m_RecordStore = RecordStore.openRecordStore(name, true, ld_model, true);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        if(m_RecordStore != null){
            
            try {
                byte[] bs = m_RecordStore.getRecord(1);
                if(bs != null)
                    for(int i=0;i<bs.length;i++){
                        System.err.println("init record 1: "+i+" : " + bs[i]);
                    }
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
                try {
                   int id = m_RecordStore.addRecord(new byte[0], 0, 0);
                    System.err.println("init record id : " + id);
                } catch (RecordStoreException ex1) {
                    ex1.printStackTrace();
                }
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
            try {
                byte[] bs = m_RecordStore.getRecord(2);
                if(bs != null)
                    for(int i=0;i<bs.length;i++){
                        System.err.println("init record 2 : "+i+" : " + bs[i]);
                    }
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
                try {
                   int id = m_RecordStore.addRecord(new byte[0], 0, 0);
                    System.err.println("init record id : " + id);
                } catch (RecordStoreException ex1) {
                    ex1.printStackTrace();
                }
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
        /*
        if(m_RecordStore != null){
            try {
                String data_key = new String(m_RecordStore.getRecord(1));
                byte[] data_pos = m_RecordStore.getRecord(2);
                ld_data  = m_RecordStore.getRecord(3);
                
                int start = 0;
                int pos = data_key.indexOf(SP_CH, start);
                int num = 0;
                while(pos != -1){
                    String key = data_key.substring(start, pos-1);
                    int start_pos = data_pos[num]<<4 + data_pos[num+1];
                    int end_pos = data_pos[num+2]<<4 + data_pos[num+3];
                    ld_map_start.put(key, new Integer(start_pos));
                    ld_map_end.put(key, new Integer(end_pos));
                }
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
                //m_RecordStore = null;
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
                m_RecordStore = null;
            }
        }
        */
    }
    
    public int getAuthmodel(){
        return ld_model;
    }
    public boolean getWritable(){
        return ld_writeable;
    }
    public LocalData setModel(int model,boolean writeable){
        ld_model = model == RecordStore.AUTHMODE_ANY ? RecordStore.AUTHMODE_ANY : RecordStore.AUTHMODE_PRIVATE;
        ld_writeable = writeable;
        if(m_RecordStore != null){
            try {
                m_RecordStore.setMode(model, writeable);
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }
    
    // key1[start_pos:end_pos]key2[start_pos:end_pos]
    public boolean removeKey(String key){
        boolean flag = false;
        if(m_RecordStore != null){
            try {
                String data_key = new String(m_RecordStore.getRecord(1));
                int pos = data_key.indexOf(key);
                if(pos != -1){
                    byte[] datas = m_RecordStore.getRecord(2);
                    int pos2 = pos + key.length();
                    int pm = data_key.indexOf(':',pos2);
                    int pr = data_key.indexOf(']',pos2);
                    String strTmp = "";
                    if(pos > 0)
                        data_key.substring(0, pos-1);
                    if(pr < data_key.length()-1){
                        strTmp += data_key.substring(pr+1);
                    }
                    m_RecordStore.setRecord(1, strTmp.getBytes(), 0, strTmp.length());
                    
                    String strStart = data_key.substring(pos2+1, pm-1);
                    String strEnd = data_key.substring(pm+1, pr-1);
                    int start = Integer.parseInt(strStart);
                    int end = Integer.parseInt(strEnd);
                    byte[] bTemp = new byte[datas.length - (start-end) + 1];
                    System.arraycopy(datas, 0, bTemp, 0, start);
                    if(end < datas.length-1)
                        System.arraycopy(datas,end+1, bTemp,end+1, datas.length-end);
                    m_RecordStore.setRecord(2, bTemp, 0, bTemp.length);
                    
                    data_key = null;
                    strTmp = null;
                    datas = null;
                    bTemp = null;
                }
                flag = true;
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
                //m_RecordStore = null;
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
                //m_RecordStore = null;
            }
        }
        return flag;
    }
    public boolean addKey(String key,byte [] data){
        boolean flag = false;
        if(m_RecordStore != null && data != null && data.length>0){
            try {
                byte[] bKey = m_RecordStore.getRecord(1);
                String data_key = "";
                if(bKey != null && bKey.length>0)
                    data_key = new String(bKey);
                byte[] ldata = m_RecordStore.getRecord(2);
                if(ldata == null){
                    ldata = new byte[0];
                }
                if(data_key.indexOf(key) != -1){
                    System.err.println("warning : LocalData is exists : " + key);
                    return false;
                }
                String str = key + "[" + ldata.length + ":" + (ldata.length+data.length-1) + "]";
                data_key += str;
                byte[] bTemp = new byte[ldata.length + data.length];
                System.arraycopy(ldata, 0, bTemp, 0, ldata.length);
                System.arraycopy(data, 0, bTemp, ldata.length, data.length);
                m_RecordStore.setRecord(1,data_key.getBytes(), 0, data_key.getBytes().length);
                m_RecordStore.setRecord(2,bTemp, 0, bTemp.length);
                flag = true;
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
        return flag;
    }
    public byte[] getKey(String key){
        if(m_RecordStore != null){
            try {
                byte[] bKey = m_RecordStore.getRecord(1);
                String data_key = "";
                if(bKey != null)
                    data_key = new String(bKey);
                System.err.println("getKey getRecord:"+data_key);
                int pos = data_key.indexOf(key);
                if(pos != -1){
                    byte[] datas = m_RecordStore.getRecord(2);
                    if(datas == null)
                        datas = new byte[0];
                    System.err.println("datas length : " + datas.length);
                    for(int i=0;i<datas.length;i++){
                        //System.err.println("datas : " + datas[i] + " : "+i);
                    }
                    int pos2 = pos + key.length();
                    int pm = data_key.indexOf(':',pos2);
                    int pr = data_key.indexOf(']',pos2);
                    /*String strTmp = "";
                    if(pos>0)
                        strTmp = data_key.substring(0, pos-1);
                    if(pr < data_key.length()-1){
                        strTmp += data_key.substring(pr+1);
                    }*/
                    System.err.println("get Key pos : " + pos);
                    System.err.println("get Key pos2 : " + pos2);
                    System.err.println("get Key pm : " + pm);
                    String strStart = data_key.substring(pos2+1, pm);
                    System.err.println("getKey strStart : " + strStart);
                    String strEnd = data_key.substring(pm+1, pr);
                    System.err.println("getKey strEnd : " + strEnd);
                    int start = Integer.parseInt(strStart);
                    int end = Integer.parseInt(strEnd);
                    System.err.println("start : "+start+" end : "+end);
                    byte[] bTemp = new byte[end-start+1];
                    System.arraycopy(datas, start, bTemp, 0, end-start+1);
                    data_key = null;
                    //strTmp = null;
                    datas = null;
                    return bTemp;
                }
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
                //m_RecordStore = null;
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
                //m_RecordStore = null;
            }
        }
        return null;
    }
    public void setBoolean(String key,boolean value){
        if(m_RecordStore != null){
            byte[] bs = new byte[1];
            bs[0] = (byte)(value ? 1 : 0);
            addKey("ldb_"+key,bs);
        }
    }
    public void setLong(String key,long value){
        if(m_RecordStore != null){
            byte[] bs = new byte[8];
            bs[0] = (byte)(value & 0xffL);
            bs[1] = (byte)((value & 0xff00L)>>8);
            bs[2] = (byte)((value & 0xff0000L)>>8*2);
            bs[3] = (byte)((value & 0xff000000L)>>8*3);
            bs[4] = (byte)((value & 0xff00000000L)>>8*4);
            bs[5] = (byte)((value & 0xff0000000000L)>>8*5);
            bs[6] = (byte)((value & 0xff000000000000L)>>8*6);
            bs[7] = (byte)((value & 0xff000000000000L)>>8*7);
            addKey("ldl_"+key,bs);
        }
    }
    public void setInt(String key,int value){
        if(m_RecordStore != null){
            byte[] bs = new byte[4];
            bs[0] = (byte)(value & 0xff);
            bs[1] = (byte)((value & 0xff00)>>8);
            bs[2] = (byte)((value & 0xff0000)>>8*2);
            bs[3] = (byte)((value & 0xff000000)>>8*3);
            addKey("ldi_"+key,bs);
        }
    }
    public void setString(String key,String value){
        if(m_RecordStore != null){
            addKey("lds_"+key,value.getBytes());
        }
    }
    
    public boolean contains(String key){
        boolean flag = false;
        if(m_RecordStore != null){
            String data_key = null;
            try {
                byte[] bKey = m_RecordStore.getRecord(1);
                if(bKey != null && bKey.length>0)
                    data_key = new String(bKey);
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
            System.err.println("contains data_key : " + data_key);
            if(data_key != null && data_key.indexOf(key) != -1){
                flag = true;
            }
        }
        return flag;
    }
    public boolean getBoolean(String key,boolean defValue){
        if(m_RecordStore != null){
            byte[] res = getKey("ldb_"+key);
            if(res != null){
                return res[0] == 1;
            }
        }
        return defValue;
    }
    public long getLong(String key,long defValue){
        if(m_RecordStore != null){
            byte[] bs = getKey("ldl_"+key);
            if(bs != null){
                long v = 0;
                v += bs[0];
                v += ((long)bs[1])<<8;
                v += ((long)bs[2])<<8*2;
                v += ((long)bs[3])<<8*3;
                v += ((long)bs[4])<<8*4;
                v += ((long)bs[5])<<8*5;
                v += ((long)bs[6])<<8*6;
                v += ((long)bs[7])<<8*7;
                return v;
            }
        }
        return defValue;
    }
    public int getInt(String key,int defValue){
        if(m_RecordStore != null){
            byte[] bs =  getKey("ldi_"+key);
            if(bs != null){
                int v = 0;
                v += bs[0];
                v += (((int)bs[1])<<8) & 0xff00;
                v += (((int)bs[2])<<8*2) & 0xff0000;
                v += (((int)bs[3])<<8*3) & 0xff000000;
                return v;
            }
        }
        return defValue;
    }
    public String getString(String key,String defValue){
        if(m_RecordStore != null){
            byte[] bs =  getKey("lds_"+key);
            if(bs != null){
                return new String(bs);
            }
        }
        return defValue;
    }
        
    public void test(){
        
    }
    
    public void clean(){
        if(m_RecordStore != null){
            try {
                m_RecordStore.setRecord(1,new byte[0], 0, 0);
                m_RecordStore.setRecord(2,new byte[0], 0, 0);
            } catch (InvalidRecordIDException ex) {
                ex.printStackTrace();
            } catch (RecordStoreException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public RecordStore getRecordStore(){
        return m_RecordStore;
    }
    
}
