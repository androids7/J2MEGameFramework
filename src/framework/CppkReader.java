/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.io.IOException;
import java.io.InputStream;

/**
 * ��ȡ�Զ������Դ����ļ�
 * @author czl
 */
public class CppkReader {
    
    public String m_FileName = null;        //cppk�ļ���
    public int m_FileNumber = 0;            //������ļ�����
    //public String[] m_Files = null;         //����������ļ���
        
    public boolean isValid = false;         //�ļ��Ƿ���Ч
    
    public String m_Version = null;         //�汾��
    
    InputStream m_input = null;
    int total_len = 0;
    
    public CppkReader(String fileName){
        m_FileName = fileName;
        checkFile(fileName);
    }
    
    private void checkFile(String file){
        m_input = App.getInstance().getClass().getResourceAsStream(file);
        try {
            total_len = m_input.available();
            if(total_len < 12){
                System.out.println("cppk file head error!");
            }else{
                byte[] head = new byte[12];
                m_input.read(head, 0, 12);
                
                isValid = head[0] == 'c';
                isValid &= head[1] == 'p';
                isValid &= head[2] == 'p';
                isValid &= head[3] == 'k';
                if(!isValid){
                    System.out.println("cppk file head flag!");
                }else{
                    m_Version = head[4] + "." + head[5] + "." + head[6];
                    m_FileNumber = (((int)(head[7])) & 0xff) << 24;
                    m_FileNumber += (((int)(head[8])) & 0xff) << 16;
                    m_FileNumber += (((int)(head[9])) & 0xff) << 8;
                    m_FileNumber += (((int)(head[10])) & 0xff);
                    System.out.println("cppk file :" + file + " packaged files:" + m_FileNumber + " version:"+m_Version);
                }
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public int getFileCount(){
        return m_FileNumber;
    }
    
    public void read(final IOnReadFile listener){
        if(listener == null){
            System.out.println("cppk read warning : listener == null");
        }else if(!isValid){
            System.out.println("cppk read error : isValid == false");
        }else{                
            try {
                
                int curPoint = 12;
                
                while(curPoint < total_len){
                    int n1 = m_input.read();
                    int n2 = m_input.read();
                    int len = (n1 << 8) + n2;
                    curPoint += 2;
                    byte[] head = new byte[len];
                    m_input.read(head, 0, len);
                    curPoint += len;
                    
                    String desc = new String(head);
                    
                    int pos = desc.indexOf("n>");
                    if(pos == -1){
                        //System.out.println("��ȡn>λ�ó���!!!");
                        if(listener != null){
                            listener.onReadError("��ȡn>λ�ó���!!!");
                        }
                        return;
                    }
                    int pos2 = desc.indexOf('<',pos);
                    if(pos2 == -1){
                        //System.out.println("��ȡn>���λ�ó���!!!");
                        if(listener != null){
                            listener.onReadError("��ȡn>���λ�ó���!!!");
                        }
                        return;
                    }
                    String name = desc.substring(pos+2, pos2);
                    //System.out.println("name:"+name);
                    
                    pos = desc.indexOf("t>",pos2);
                    if(pos == -1){
                        //System.out.println("��ȡt>λ�ó���!!!");
                        if(listener != null){
                            listener.onReadError("��ȡt>λ�ó���!!!");
                        }
                        return;
                    }
                    pos2 = desc.indexOf('<',pos);
                    if(pos2 == -1){
                        //System.out.println("��ȡt>���λ�ó���!!!");
                        if(listener != null){
                            listener.onReadError("��ȡt>���λ�ó���!!!");
                        }
                        return;
                    }
                    String type = desc.substring(pos+2, pos2);
                    //System.out.println("type:"+type);
                    
                    pos = desc.indexOf("l>",pos2);
                    if(pos == -1){
                        //System.out.println("��ȡl>λ�ó���!!!");
                        if(listener != null){
                            listener.onReadError("��ȡl>λ�ó���!!!");
                        }
                        return;
                    }
                    pos2 = desc.indexOf('<',pos);
                    if(pos2 == -1){
                        //System.out.println("��ȡl>���λ�ó���!!!");
                        if(listener != null){
                            listener.onReadError("��ȡl>���λ�ó���!!!");
                        }
                        return;
                    }
                    String length = desc.substring(pos+2, pos2);
                    //System.out.println("length:"+length);
                    
                    int contextLen = Integer.parseInt(length);
                    byte[] context = new byte[contextLen];
                    m_input.read(context, 0, contextLen);
                    curPoint += contextLen;
                    m_input.read();         // ��ȡһ���س���
                    curPoint += 1;
                    
                    if(type.equals("image")){
                        if(listener != null)
                            listener.onReadedImage(context, name);
                        
                    }else if(type.equals("audio")){
                        pos = desc.indexOf("m>",pos2);
                        if(pos == -1){
                            //System.out.println("��ȡm>λ�ó���!!!");
                            if(listener != null){
                                listener.onReadError("��ȡm>λ�ó���!!!");
                            }
                            return;
                        }
                        pos2 = desc.indexOf('<',pos);
                        if(pos2 == -1){
                            //System.out.println("��ȡm>���λ�ó���!!!");
                            if(listener != null){
                                listener.onReadError("��ȡm>���λ�ó���!!!");
                            }
                            return;
                        }
                        String subType = desc.substring(pos+2, pos2);
                        //System.out.println("subType:"+subType);
                        if(listener != null)
                            listener.onReadedAudio(context, name, subType);
                    }else{
                        if(listener != null)
                            listener.onReadedOther(context, name, type);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void destory(){
        if(isValid && m_input != null){
            try {
                m_input.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        isValid = false;
        m_input = null;
    }
    
    public interface IOnReadFile{
        public void onReadedOther(byte [] context,String file,String type);
        public void onReadedImage(byte [] context,String file);
        public void onReadedAudio(byte [] context,String file,String type);
        public void onReadError(String msg);
    }
    
}
