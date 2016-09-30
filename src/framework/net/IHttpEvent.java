package framework.net;

/**
 *
 * @author chizl
 */
public interface IHttpEvent {
    public void onHttpSuccess(byte[] data);
    public void onHttpError(int code,String msg);
}
