/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;

/**
 *
 * @author czl
 */
public class Res {
    
    // 全局，不受removeAll删除资源
    static Image[] g_imgs = new Image[5];
    static int g_add_img_index = 0;
    // 受removeAll删除的资源
    static Image[] c_imgs = new Image[5];
    static int c_add_img_index = 0;
    
    static Player[] g_player = new Player[5];
    static int g_add_player_index = 0;
    static Player[] c_player = new Player[5];
    static int c_add_player_index = 0;
    
    static byte[][] g_bytes = new byte[5][];
    static int g_add_byte_index = 0;
    static byte[][] c_bytes = new byte[5][];
    static int c_add_byte_index = 0;
    
    static Hashtable res_map = new Hashtable();         // key:String value:Integer
    
    public static final int RES_TYPE_G_IMG = 100000;
    public static final int RES_TYPE_C_IMG = 10000;
    
    public static final int RES_TYPE_G_PLAYER = 200000;
    public static final int RES_TYPE_C_PLAYER = 20000;
    
    public static final int RES_TYPE_G_OTHER = 300000;
    public static final int RES_TYPE_C_OTHER = 30000;
    
    public static void removeAll(){
        c_imgs = new Image[5];
        c_add_img_index = 0;
        c_player = new Player[5];
        c_add_player_index = 0;
        c_bytes = new byte[5][];
        c_add_byte_index = 0;
        Enumeration e = res_map.keys();
        Hashtable tmp = new Hashtable();
        while(e.hasMoreElements()){
            String key = (String)e.nextElement();
            Integer v = (Integer)res_map.get(key);
            if(v.intValue() >= 100000){
                tmp.put(key, v);
            }
        }
        res_map = tmp;
        tmp = null;
        System.gc();
    }
    public static void removeType(int type){
        Hashtable tmp = null;
        Enumeration e = null;
        switch(type){
            case RES_TYPE_G_IMG:
                tmp = new Hashtable();
                e = res_map.keys();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    Integer v = (Integer)res_map.get(key);
                    if(v.intValue() >= 100000 && v.intValue() < 200000){
                        tmp.put(key, v);
                    }
                }
                break;
            case RES_TYPE_C_IMG:
                tmp = new Hashtable();
                e = res_map.keys();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    Integer v = (Integer)res_map.get(key);
                    if(v.intValue() >= 10000 && v.intValue() < 20000){
                        tmp.put(key, v);
                    }
                }
                break;
            case RES_TYPE_G_PLAYER:
                tmp = new Hashtable();
                e = res_map.keys();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    Integer v = (Integer)res_map.get(key);
                    if(v.intValue() >= 200000 && v.intValue() < 300000){
                        tmp.put(key, v);
                    }
                }
                break;
            case RES_TYPE_C_PLAYER:
                tmp = new Hashtable();
                e = res_map.keys();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    Integer v = (Integer)res_map.get(key);
                    if(v.intValue() >= 20000 && v.intValue() < 30000){
                        tmp.put(key, v);
                    }
                }
                break;
            case RES_TYPE_G_OTHER:
                tmp = new Hashtable();
                e = res_map.keys();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    Integer v = (Integer)res_map.get(key);
                    if(v.intValue() >= 300000 && v.intValue() < 400000){
                        tmp.put(key, v);
                    }
                }
                break;
            case RES_TYPE_C_OTHER:
                tmp = new Hashtable();
                e = res_map.keys();
                while(e.hasMoreElements()){
                    String key = (String)e.nextElement();
                    Integer v = (Integer)res_map.get(key);
                    if(v.intValue() >= 30000 && v.intValue() < 40000){
                        tmp.put(key, v);
                    }
                }
                break;
            default:
                System.out.println("Res.removeType unknow type of:" + type);
                break;
        }
        if(tmp != null){
            res_map = tmp;
            tmp = null;
            e = null;
            System.gc();
        }
    }
    
    public static void addImageToG(Image img,String name){
        name = "#"+name;
        if(res_map.containsKey(name)){
            System.err.println("add res error (exists) :"+name);
            return;
        }
        if(img == null){
            System.err.println("add res error (data) :"+name);
            return;
        }
        if(g_add_img_index == g_imgs.length){
            Image[] tmp = g_imgs;
            g_imgs = new Image[g_add_img_index+5];
            System.arraycopy(tmp, 0, g_imgs, 0, g_add_img_index);
            tmp = null;
        }
        res_map.put(name,new Integer(RES_TYPE_G_IMG+g_add_img_index));
        g_imgs[g_add_img_index++] = img;
    }
    public static void addImageToC(Image img,String name){
        name = "#"+name;
        if(res_map.containsKey(name)){
            System.err.println("add res error (exists) :"+name);
            return;
        }
        if(img == null){
            System.err.println("add res error (data) :"+name);
            return;
        }
        if(c_add_img_index == c_imgs.length){
            Image[] tmp = c_imgs;
            c_imgs = new Image[c_add_img_index+5];
            System.arraycopy(tmp, 0, c_imgs, 0, c_add_img_index);
            tmp = null;
        }
        res_map.put(name,new Integer(RES_TYPE_C_IMG+c_add_img_index));
        c_imgs[c_add_img_index++] = img;
    }
    static void addImageRes(String path,boolean g){
        try {
            Image img = Image.createImage(path);
            if(g){
                addImageToG(img,path);
            }else{
                addImageToC(img,path);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("addImageRes error:"+path);
        }
    }
    
    public static void addPlayerToG(Player player,String name){
        name = "#"+name;
        if(res_map.containsKey(name)){
            System.err.println("add res error (exists) :"+name);
            return;
        }
        if(player == null){
            System.err.println("add res error (data) :"+name);
            return;
        }
        if(g_add_player_index == g_player.length){
            Player[] tmp = g_player;
            g_player = new Player[g_add_player_index+5];
            System.arraycopy(tmp, 0, g_player, 0, g_add_player_index);
            tmp = null;
        }
        res_map.put(name,new Integer(RES_TYPE_G_PLAYER+g_add_player_index));
        g_player[g_add_player_index++] = player;
    }
    public static void addPlayerToC(Player player,String name){
        name = "#"+name;
        if(res_map.containsKey(name)){
            System.err.println("add res error (exists) :"+name);
            return;
        }
        if(player == null){
            System.err.println("add res error (data) :"+name);
            return;
        }
        if(c_add_player_index == c_player.length){
            Player[] tmp = c_player;
            c_player = new Player[c_add_player_index+5];
            System.arraycopy(tmp, 0, c_player, 0, c_add_player_index);
            tmp = null;
        }
        res_map.put(name,new Integer(RES_TYPE_C_PLAYER+c_add_player_index));
        c_player[c_add_player_index++] = player;
    }
    static void addPlayerRes(String url,boolean g){
        try {
            InputStream is = App.getInstance().getClass().getResourceAsStream(url);
            String type = null;
            if ((url.indexOf(".mid") != -1) || (url.indexOf(".MID") != -1)) {
                    type = "audio/midi";
            } else if (url.indexOf(".wav") != -1) {
                    type = "audio/x-wav";
            } else if ((url.indexOf(".mp3") != -1) || url.indexOf(".MP3") != -1) {
                    type = "audio/mpeg";
            } 
            Player player = Manager.createPlayer(is, type);// 创建声音播放器
            player.realize();// 获取声音信息
            player.prefetch();// 获取声音资源
            if(g){
                addPlayerToG(player,url);
            }else{
                addPlayerToC(player,url);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void addOtherToG(byte[] bs,String name){
        name = "#"+name;
        if(res_map.containsKey(name)){
            System.err.println("add res error (exists) :"+name);
            return;
        }
        if(bs == null || bs.length == 0){
            System.err.println("add res error (data) :"+name);
            return;
        }
        if(g_add_byte_index == g_bytes.length){
            byte[][] tmp = g_bytes;
            g_bytes = new byte[g_add_byte_index+5][];
            System.arraycopy(tmp, 0, g_bytes, 0, g_add_byte_index);
            tmp = null;
        }
        res_map.put(name,new Integer(RES_TYPE_G_OTHER+g_add_byte_index));
        g_bytes[g_add_byte_index++] = bs;
    }
    public static void addOtherToC(byte[] bs,String name){
        name = "#"+name;
        if(res_map.containsKey(name)){
            System.err.println("add res error (exists) :"+name);
            return;
        }
        if(c_add_byte_index == c_bytes.length){
            byte[][] tmp = c_bytes;
            c_bytes = new byte[c_add_byte_index+5][];
            System.arraycopy(tmp, 0, c_bytes, 0, c_add_byte_index);
            tmp = null;
        }
        res_map.put(name,new Integer(RES_TYPE_C_OTHER+c_add_byte_index));
        c_bytes[c_add_byte_index++] = bs;
    }
    static void addByteRes(String url,boolean g){
        try {
            InputStream is = App.getInstance().getClass().getResourceAsStream(url);
            
            int len = is.available();
            byte[] bs = new byte[len];
            is.read(bs, 0, len);
            if(g){
                addOtherToG(bs,url);
            }else{
                addOtherToC(bs,url);
            }
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void addResAsType(String path,int type){
        switch(type){
            case RES_TYPE_G_IMG:
            case RES_TYPE_C_IMG:
                addImageRes(path, type == RES_TYPE_G_IMG);
                break;
            case RES_TYPE_G_PLAYER:
            case RES_TYPE_C_PLAYER:
                addPlayerRes(path,type == RES_TYPE_G_PLAYER);
                break;
            case RES_TYPE_G_OTHER:
            case RES_TYPE_C_OTHER:
                addByteRes(path, type == RES_TYPE_G_OTHER);
                break;
            default:
                System.out.println("Res.addResAsType unknow type of:" + type);
                break;
        }
    }
    
    static int getFileType(String file,boolean g){
        int type = 0;
        int pos = file.lastIndexOf('.');
        if(pos != -1){
            String tail = file.substring(pos+1);
            tail = tail.toLowerCase();
            if(tail.equals("jpg") 
                || tail.equals("jpeg")
                || tail.equals("png")){
                if(g){
                    type = RES_TYPE_G_IMG;
                }else{
                    type = RES_TYPE_C_IMG;
                }
            }else if(file.indexOf(".mid") != -1
                || file.indexOf(".wav") != -1
                || file.indexOf(".mp3") != -1){
                if(g){
                    type = RES_TYPE_G_PLAYER;
                }else{
                    type = RES_TYPE_C_PLAYER;
                }
            }else if(file.indexOf(".txt") != -1
                || file.indexOf(".json") != -1){
                if(g){
                    type = RES_TYPE_G_OTHER;
                }else{
                    type = RES_TYPE_C_OTHER;
                }
            }
        }
        return type;
    }
    
    /**
     * 同步加载资源
     * @param files     要加载的资源数据，支持png,jpg,wav,mid,mp3,txt,json格式
     * @param g         是否加载到全局资源里，全局资源不受removeAll卸载
     * @param listener  加载进度监听
     */
    public static void autoLoadRes(final String[] files,final boolean g,final IOnLoadRes listener){
        if(listener != null){
            listener.onLoadResProcess(null, 0);
        }
        if(files != null){
            int len = files.length;
            for(int i=0;i<len;i++){
                String file = files[i];
                if(file != null){
                    addResAsType(file,getFileType(file,g));
                }
                if(listener != null){
                    listener.onLoadResProcess(file, (i+1)*100 / len);
                }
            }
        }
        if(listener != null){
            listener.onLoadResCompleted();
        }
    }

    /**
     * 异步加载资源
     * @param files     要加载的资源数据，支持png,jpg,wav,mid,mp3,txt,json格式
     * @param g         是否加载到全局资源里，全局资源不受removeAll卸载
     * @param listener  加载进度监听
     */
    public static void autoLoadResAsyc(final String[] files,final boolean g,final IOnLoadRes listener){
        new Thread(){
            public void run(){
                autoLoadRes(files,g,listener);
            }
        }.start();
    }
    
    public interface IOnLoadRes{
        public void onLoadResCompleted();
        public void onLoadResProcess(String file,int value); // value : 0~100
    }

    /**
     * 获取Image,file如果是'#'开关，则从缓存内获取
     * @param file
     * @return 
     */
    public static Image getImage(String file){
        Image img = null;
        if(file.startsWith("#")){
            if(res_map.containsKey(file)){
                int tag = ((Integer)res_map.get(file)).intValue();
                int index = tag % 10000;
                int type = tag - index;
                if(type == RES_TYPE_G_IMG){
                    img = g_imgs[index];
                }else if(type == RES_TYPE_C_IMG){
                    img = c_imgs[index];
                }
            }
        }else{
            try {
                img = Image.createImage(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(img == null){
            System.err.println("getImage error (not find:image):"+file);
        }
        return img;
    }
    
    /**
     * 获取声音Player,file如果是'#'开关，则从缓存内获取
     * @param file
     * @return 
     */
    public static Player getSound(String file){
        Player player = null;
        if(file.startsWith("#")){
            if(res_map.containsKey(file)){
                int tag = ((Integer)res_map.get(file)).intValue();
                int index = tag % 10000;
                int type = tag - index;
                if(type == RES_TYPE_G_PLAYER){
                    player = g_player[index];
                }else if(type == RES_TYPE_C_PLAYER){
                    player = c_player[index];
                }
            }
        }else{
            try {
                InputStream is = App.getInstance().getClass().getResourceAsStream(file);
                String type = null;
                if ((file.indexOf(".mid") != -1) || (file.indexOf(".MID") != -1)) {
                        type = "audio/midi";
                } else if (file.indexOf(".wav") != -1) {
                        type = "audio/x-wav";
                } else if ((file.indexOf(".mp3") != -1) || file.indexOf(".MP3") != -1) {
                        type = "audio/mpeg";
                } 
                player = Manager.createPlayer(is, type);// 创建声音播放器
                player.realize();// 获取声音信息
                player.prefetch();// 获取声音资源
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(player == null){
            System.err.println("getImage error (not find:image):"+file);
        }
        return player;
    }
    
    public static byte[] getBytes(String file){
        byte[] bs = null;
        if(file.startsWith("#")){
            if(res_map.containsKey(file)){
                int tag = ((Integer)res_map.get(file)).intValue();
                int index = tag % 10000;
                int type = tag - index;
                if(type == RES_TYPE_G_OTHER){
                    bs = g_bytes[index];
                }else if(type == RES_TYPE_C_OTHER){
                    bs = c_bytes[index];
                }
            }
        }else{
            try {
                InputStream is = App.getInstance().getClass().getResourceAsStream(file);
                int len = is.available();
                bs = new byte[len];
                is.read(bs, 0, len);
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(bs == null){
            System.err.println("getImage error (not find:image):"+file);
        }
        return bs;
    }
    
    public static void remove(String file){
        if(!file.startsWith("#")){
            file = "#" + file;
        }
        if(res_map.containsKey(file)){
            int tag = ((Integer)res_map.get(file)).intValue();
            int index = tag % 10000;
            int type = tag - index;
            switch(type){
                case RES_TYPE_G_IMG:
                    g_imgs[index] = null;
                    break;
                case RES_TYPE_C_IMG:
                    c_imgs[index] = null;
                    break;
                case RES_TYPE_G_PLAYER:
                    g_player[index] = null;
                    break;
                case RES_TYPE_C_PLAYER:
                    c_player[index] = null;
                    break;
                case RES_TYPE_G_OTHER:
                    g_bytes[index] = null;
                    break;
                case RES_TYPE_C_OTHER:
                    c_bytes[index] = null;
                    break;
                default:
                    System.out.println("Res.remove unknow type of:" + type);
                    break;
            }
            res_map.remove(file);
            return ;
        }
        System.out.println("Res.remove file error (unknow file):" + file);
    }
    public static void remove(Image img){
        for(int i=0;i<g_imgs.length;i++){
            if(g_imgs[i] == img){
                g_imgs[i] = null;
                Integer v = new Integer(RES_TYPE_G_IMG + i);
                if(res_map.contains(v)){
                    Enumeration e = res_map.keys();
                    while(e.hasMoreElements()){
                        String key = (String)e.nextElement();
                        if(res_map.get(key) == v){
                            res_map.remove(key);
                        }
                    }
                }else{
                    System.out.println("Res.remove image warning (res_map not contains):"+v);
                }
                return;
            }
        }
        for(int i=0;i<c_imgs.length;i++){
            if(c_imgs[i] == img){
                c_imgs[i] = null;
                Integer v = new Integer(RES_TYPE_C_IMG + i);
                if(res_map.contains(v)){
                    Enumeration e = res_map.keys();
                    while(e.hasMoreElements()){
                        String key = (String)e.nextElement();
                        if(res_map.get(key) == v){
                            res_map.remove(key);
                        }
                    }
                }else{
                    System.out.println("Res.remove image warning (res_map not contains):"+v);
                }
                return;
            }
        }
        System.out.println("Res.remove Image error (unknow img):");
    }
    public static void remove(Player player){
        for(int i=0;i<g_player.length;i++){
            if(g_player[i] == player){
                g_player[i] = null;
                Integer v = new Integer(RES_TYPE_G_PLAYER + i);
                if(res_map.contains(v)){
                    Enumeration e = res_map.keys();
                    while(e.hasMoreElements()){
                        String key = (String)e.nextElement();
                        if(res_map.get(key) == v){
                            res_map.remove(key);
                        }
                    }
                }else{
                    System.out.println("Res.remove Player warning (res_map not contains):"+v);
                }
                return;
            }
        }
        for(int i=0;i<c_player.length;i++){
            if(c_player[i] == player){
                c_player[i] = null;
                Integer v = new Integer(RES_TYPE_C_PLAYER + i);
                if(res_map.contains(v)){
                    Enumeration e = res_map.keys();
                    while(e.hasMoreElements()){
                        String key = (String)e.nextElement();
                        if(res_map.get(key) == v){
                            res_map.remove(key);
                        }
                    }
                }else{
                    System.out.println("Res.remove Player warning (res_map not contains):"+v);
                }
                return;
            }
        }
        System.out.println("Res.remove Player error (unknow player):");
    }
    public static void remove(byte [] bs){
        for(int i=0;i<g_bytes.length;i++){
            if(g_bytes[i] == bs){
                g_bytes[i] = null;
                Integer v = new Integer(RES_TYPE_G_OTHER + i);
                if(res_map.contains(v)){
                    Enumeration e = res_map.keys();
                    while(e.hasMoreElements()){
                        String key = (String)e.nextElement();
                        if(res_map.get(key) == v){
                            res_map.remove(key);
                        }
                    }
                }else{
                    System.out.println("Res.remove byte[] warning (res_map not contains):"+v);
                }
                return;
            }
        }
        for(int i=0;i<g_bytes.length;i++){
            if(g_bytes[i] == bs){
                g_bytes[i] = null;
                Integer v = new Integer(RES_TYPE_C_OTHER + i);
                if(res_map.contains(v)){
                    Enumeration e = res_map.keys();
                    while(e.hasMoreElements()){
                        String key = (String)e.nextElement();
                        if(res_map.get(key) == v){
                            res_map.remove(key);
                        }
                    }
                }else{
                    System.out.println("Res.remove byte[] warning (res_map not contains):"+v);
                }
                return;
            }
        }
        System.out.println("Res.remove byte[] error (unknow bs):");
    }
}
