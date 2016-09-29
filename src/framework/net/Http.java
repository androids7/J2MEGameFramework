/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author Administrator
 */
public class Http {
    HttpConnection c;
    InputStream is;
    OutputStream os;
    
    IHttpEvent mIHttpEvent;
    
    private void _get(final String url){
        new Thread(){
            public void run(){
                try {
                    c = (HttpConnection)Connector.open(url);
                    c.setRequestMethod(HttpConnection.GET);
                    int status = c.getResponseCode();
                    if(status != HttpConnection.HTTP_OK){
                        if(mIHttpEvent != null){
                            mIHttpEvent.onHttpError(status,"http get response code error!");
                        }
                    }else{
                        is = c.openInputStream();
                        int len = is.available();
                        byte[] buf = null;
                        if(len > 0){
                            buf = new byte[len];
                            is.read(buf, 0, len);
                        }
                        if(mIHttpEvent != null){
                            mIHttpEvent.onHttpSuccess(buf);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    if(mIHttpEvent != null){
                        mIHttpEvent.onHttpError(-1,ex.getMessage());
                    }
                }
                try {
                    if(is != null){
                        is.close();
                    }
                    if(c != null){
                        c.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }
    
    public static void get(String url,IHttpEvent listener){
        Http h = new Http();
        h.mIHttpEvent = listener;
        h._get(url);
    }
    
    private void _post(final String url,final String params){
        new Thread(){
            public void run(){
                try {
                    c = (HttpConnection)Connector.open(url);
                    c.setRequestMethod(HttpConnection.POST);
                    c.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    c.setRequestProperty("Content-Length",""+params.length());
                    
                    os = c.openOutputStream();
                    os.write(params.getBytes());
                    os.flush();
                    
                    int status = c.getResponseCode();
                    if(status != HttpConnection.HTTP_OK){
                        if(mIHttpEvent != null){
                            mIHttpEvent.onHttpError(status,"http post response code error!");
                        }
                    }else{
                        is = c.openInputStream();
                        int len = is.available();
                        byte[] buf = null;
                        if(len > 0){
                            buf = new byte[len];
                            is.read(buf, 0, len);
                        }
                        if(mIHttpEvent != null){
                            mIHttpEvent.onHttpSuccess(buf);
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    if(mIHttpEvent != null){
                        mIHttpEvent.onHttpError(-1,ex.getMessage());
                    }
                }
                try {
                    if(is != null){
                        is.close();
                    }
                    if(os != null){
                        os.close();
                    }
                    if(c != null){
                        c.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }.start();
    }
    
    public static void post(String url,String params, IHttpEvent listener){
        Http h = new Http();
        h.mIHttpEvent = listener;
        h._post(url,params);
    }
    public static void post(String url,HttpParams params, IHttpEvent listener){
        Http h = new Http();
        h.mIHttpEvent = listener;
        h._post(url,params.params);
    }
    
    public static class HttpParams{
        String params = null;
        public void put(String key,String value){
            if(params != null){
                params += "&";
            }else{
                params = "";
            }
            params += key + "=" + value;
        }
        public String getParams(){
            return params;
        }
    }
    
}
