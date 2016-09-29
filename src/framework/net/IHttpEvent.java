/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework.net;

/**
 *
 * @author Administrator
 */
public interface IHttpEvent {
    public void onHttpSuccess(byte[] data);
    public void onHttpError(int code,String msg);
}
