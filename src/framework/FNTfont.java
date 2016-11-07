/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.microedition.lcdui.Image;

/**
 *
 * @author czl
 */
public class FNTfont {
    
    private FNTfont(){}
    
    String _fnt_file = null;
    public static FNTfont create(String fileName){
        byte [] data = null;
        try{
            InputStream in = App.getInstance().getClass().getResourceAsStream(fileName);
            int len = in.available();
            data = new byte[len];
            in.read(data);
            in.close();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
        FNTfont fnt = new FNTfont();
        fnt._fnt_file = fileName;
        fnt.initFntFileData(data);
        data = null;
        return fnt;
    }
    
    private void initFntFileData(byte [] data){
        int index = 0;
        byte[] ndata = new byte[data.length];
        for(int i=0;i<data.length;i++){
            //if("")
            if(data[i] != '\r' && data[i] != '\n'){
                ndata[index++] = data[i];
            }else if(data[i] != '\n' || i == data.length-1){
                String res = new String(ndata,0,index);
                index = 0;
                initFntFileReadLine(res);
            }
        }
        ndata = null;
    }
    
    private void initFntFileReadLine(String line){
        if(line == null || line.length() <= 1)return;
        if(line.startsWith("info")){
            initFontFileInfo(line);
        }else if(line.startsWith("common")){
            initFontFileCommon(line);
        }else if(line.startsWith("page")){
            initFontFilePage(line);
        }else if(line.startsWith("chars")){
            initFontFileChars(line);
        }else if(line.startsWith("char")){
            initFontFileChar(line);
        }
    }
    
    private String _fnt_face = null;
    private int _fnt_size = 0;
    private boolean _fnt_bold = false;
    private boolean _fnt_italic = false;
    private String _fnt_charset = null;
    private boolean _fnt_unicode = true;
    private int _fnt_stretchH = 0;
    private boolean _fnt_smooth = false;
    private boolean _fnt_aa = false;
    private int [] _fnt_padding = new int[4];
    private int [] _fnt_spacing = new int[2];
    private int _fnt_outline = 0;
    private void initFontFileInfo(String info){
        //System.err.println("fnt line:"+line);
        int pos = info.indexOf("info");
        int len = info.length();
        // 过滤nfo级后面的空格
        while(info.charAt(pos) != ' ' && pos < len){
            pos++;
        }

        String key = null;
        String value = null;
        int start;
        while(pos < len){
            while(pos < len && info.charAt(pos) == ' '){pos++;} // 过滤空格
            if(pos >= len)break;
            start = pos;
            while(pos < len && info.charAt(pos) != '='){
                pos++;
            }
            key = info.substring(start, pos++);
            if(pos >= len)break;
            if(info.charAt(pos) == 34)pos++;// 34="
            start = pos;
            while(pos < len && info.charAt(pos) != ' '){ // 34="
                pos++;
            }
            value = info.substring(start, info.charAt(pos-1) == 34 ? pos-1 : pos);
            //System.out.println("key = " + key + " value = "+value);
            
            if(key.equals("face")){
                _fnt_face = value;
            }else if(key.equals("size")){
                _fnt_size = Integer.parseInt(value);
            }else if(key.equals("bold")){
                _fnt_bold = Integer.parseInt(value) == 1;
            }else if(key.equals("italic")){
                _fnt_italic = Integer.parseInt(value) == 1;
            }else if(key.equals("charset")){
                _fnt_charset = value;
            }else if(key.equals("unicode")){
                _fnt_unicode = Integer.parseInt(value) == 1;
            }else if(key.equals("stretchH")){
                _fnt_stretchH = Integer.parseInt(value);
            }else if(key.equals("smooth")){
                _fnt_smooth = Integer.parseInt(value) == 1;
            }else if(key.equals("aa")){
                _fnt_aa = Integer.parseInt(value) == 1;
            }else if(key.equals("padding")){
                int start2 = 0;
                int pos2 = 0;
                int len2 = value.length();
                int index = 0;
                while(pos2 < len2){
                    if(value.charAt(pos2) == ','){
                        String v = value.substring(start2, pos2);
                        //System.err.println("_fnt_padding :" + index + " v:" + v);
                        _fnt_padding[index++] = Integer.parseInt(v);
                        start2 = pos2+1;
                    }
                    pos2++;
                }
                String v = value.substring(start2, pos2);
                //System.err.println("_fnt_padding :" + index + " v:" + v);
                _fnt_padding[index++] = Integer.parseInt(v);
            }else if(key.equals("spacing")){
                int start2 = 0;
                int pos2 = 0;
                int len2 = value.length();
                int index = 0;
                while(pos2 < len2){
                    if(value.charAt(pos2) == ','){
                        String v = value.substring(start2, pos2);
                        //System.err.println("_fnt_spacing :" + index + " v:" + v);
                        _fnt_spacing[index++] = Integer.parseInt(v);
                        start2 = pos2+1;
                    }
                    pos2++;
                }
                String v = value.substring(start2, pos2);
                //System.err.println("_fnt_spacing :" + index + " v:" + v);
                _fnt_spacing[index++] = Integer.parseInt(v);
            }else if(key.equals("outline")){
                _fnt_outline = Integer.parseInt(value);
            }
            
            if(pos >= len)break;
        }
    }
    private int _fnt_lineHeight = 0;
    private int _fnt_base = 0;
    private int _fnt_scaleW = 0;
    private int _fnt_scaleH = 0;
    private int _fnt_pages = 0;
    private int _fnt_packed = 0;
    private int _fnt_alphaChnl = 0;
    private int _fnt_redChnl = 0;
    private int _fnt_greenChnl = 0;
    private int _fnt_blueChnl = 0;
    private void initFontFileCommon(String info){
        //System.err.println("fnt line:"+info);
        int pos = info.indexOf("common");
        int len = info.length();
        // 过滤nfo级后面的空格
        while(info.charAt(pos) != ' ' && pos < len){
            pos++;
        }

        String key = null;
        String value = null;
        int start;
        while(pos < len){
            while(pos < len && info.charAt(pos) == ' '){pos++;} // 过滤空格
            if(pos >= len)break;
            start = pos;
            while(pos < len && info.charAt(pos) != '='){
                pos++;
            }
            key = info.substring(start, pos++);
            if(pos >= len)break;
            if(info.charAt(pos) == 34)pos++;// 34="
            start = pos;
            while(pos < len && info.charAt(pos) != ' '){ // 34="
                pos++;
            }
            value = info.substring(start, info.charAt(pos-1) == 34 ? pos-1 : pos);
            //System.out.println("key = " + key + " value = "+value);
            
            if(key.equals("lineHeight")){
                _fnt_lineHeight = Integer.parseInt(value);
            }else if(key.equals("base")){
                _fnt_base = Integer.parseInt(value);
            }else if(key.equals("scaleW")){
                _fnt_scaleW = Integer.parseInt(value);
            }else if(key.equals("scaleH")){
                _fnt_scaleH = Integer.parseInt(value);
            }else if(key.equals("pages")){
                _fnt_pages = Integer.parseInt(value);
            }else if(key.equals("packed")){
                _fnt_packed = Integer.parseInt(value);
            }else if(key.equals("alphaChnl")){
                _fnt_alphaChnl = Integer.parseInt(value);
            }else if(key.equals("redChnl")){
                _fnt_redChnl = Integer.parseInt(value);
            }else if(key.equals("greenChnl")){
                _fnt_greenChnl = Integer.parseInt(value);
            }else if(key.equals("blueChnl")){
                _fnt_blueChnl = Integer.parseInt(value);
            }
            if(pos >= len)break;
        }
    }
    
    private String[] _fnt_page_file = new String[2];
    private Image[] _fnt_file_image = new Image[2];
    private void initFontFilePage(String info){
        //System.err.println("fnt line:"+info);
        int pos = info.indexOf("page");
        int len = info.length();
        // 过滤nfo级后面的空格
        while(info.charAt(pos) != ' ' && pos < len){
            pos++;
        }

        String key = null;
        String value = null;
        int start;
        
        int id = -1;
        String file = null;
        
        while(pos < len){
            while(pos < len && info.charAt(pos) == ' '){pos++;} // 过滤空格
            if(pos >= len)break;
            start = pos;
            while(pos < len && info.charAt(pos) != '='){
                pos++;
            }
            key = info.substring(start, pos++);
            if(pos >= len)break;
            if(info.charAt(pos) == 34)pos++;// 34="
            start = pos;
            while(pos < len && info.charAt(pos) != ' '){ // 34="
                pos++;
            }
            value = info.substring(start, info.charAt(pos-1) == 34 ? pos-1 : pos);
            //System.out.println("key = " + key + " value = "+value);
            
            if(key.equals("id")){
                id = Integer.parseInt(value);
            }else if(key.equals("file")){
                file = value;
            }
            if(pos >= len)break;
        }
        if(id == -1 || file == null){
            System.out.println("read page line error !");
            return;
        }
        len = _fnt_page_file.length;
        if(id >= len){
            String [] temp = _fnt_page_file;
            _fnt_page_file = new String[len + 2];
            System.arraycopy(temp, 0, _fnt_page_file, 0, len);
            
            Image [] img = _fnt_file_image;
            _fnt_file_image = new Image[len + 2];
            System.arraycopy(img, 0, _fnt_file_image, 0, len);
        }
        try {
            pos = _fnt_file.lastIndexOf('/');
            String imgFile = _fnt_file.substring(0, pos+1) + file;
            _fnt_file_image[id] = Image.createImage(imgFile);
            _fnt_page_file[id] = file;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private int _fnt_count = 0;
    private void initFontFileChars(String info){
        //System.err.println("fnt line:"+info);
        int pos = info.indexOf("chars");
        int len = info.length();
        // 过滤nfo级后面的空格
        while(info.charAt(pos) != ' ' && pos < len){
            pos++;
        }

        String key = null;
        String value = null;
        int start;
        
        while(pos < len){
            while(pos < len && info.charAt(pos) == ' '){pos++;} // 过滤空格
            if(pos >= len)break;
            start = pos;
            while(pos < len && info.charAt(pos) != '='){
                pos++;
            }
            key = info.substring(start, pos++);
            if(pos >= len)break;
            if(info.charAt(pos) == 34)pos++;// 34="
            start = pos;
            while(pos < len && info.charAt(pos) != ' '){ // 34="
                pos++;
            }
            value = info.substring(start, info.charAt(pos-1) == 34 ? pos-1 : pos);
            //System.out.println("key = " + key + " value = "+value);
            
            if(key.equals("count")){
                _fnt_count = Integer.parseInt(value);
                _fnt_chars = new FntChar[_fnt_count];
                return;
            }
            if(pos >= len)break;
        }
    }
    
    private FntChar[] _fnt_chars = null;
    int _cur_fnt_grp_index = 0;
    private void initFontFileChar(String info){
        if(_fnt_chars == null){
            System.err.println("initFontFileChar error _fnt_chars is null");
            return;
        }
        _fnt_chars[_cur_fnt_grp_index++] = new FntChar(info);
    }

    public Image getCharImage(int ch){
        int charId = ch;
        //System.err.println("charId:"+charId);
        int pos = 0;
        int lastPosStart = 0;
        int lastPosEnd = _fnt_chars.length;
        FntChar fntChar = null;
        // 二分法查找
        do{
            if(fntChar == null){
                pos = _fnt_chars.length/2;
            }else if(fntChar._char_id > charId){
                lastPosEnd = pos;
                pos = (lastPosEnd + lastPosStart)/2;
            }else{
                lastPosStart = pos;
                pos = (lastPosEnd + lastPosStart)/2;
            }
            fntChar = _fnt_chars[pos];
        }while(fntChar._char_id != charId && lastPosEnd-lastPosStart>1);
        
        if(fntChar != null && fntChar._char_id == charId){
            int page = fntChar._char_page;
            int x = fntChar._char_x + fntChar._char_xoffset;
            int y = fntChar._char_y + fntChar._char_yoffset;
            int w = fntChar._char_width;
            int h = fntChar._char_height;
            Image img = _fnt_file_image[page];
            int[] colors = new int[w*h];
            int[] srcColors = new int[img.getWidth() * img.getHeight()];
            
            img.getRGB(srcColors, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
            
            for(int i=0;i<img.getWidth();i++){
                for(int j=0;j<img.getHeight();j++){
                    if(i>=x && i< x+w &&
                        j >= y && j < y+h){
                        colors[(i-x) + (j-y)*w] = srcColors[i + j*img.getWidth()];
                    }
                }
            }
            return Image.createRGBImage(colors, w, h, true);
        }else{
            System.err.println("getCharImage error con not find char id:" + ch);
        }
        return null;
    }
    
    /**
     * 获取字符对应的图片
     * 中文可直接使用，java的char直接对应的就是unicode码
     * fnt的id使用的就是unicode码
     * @param ch
     * @return 
     */
    public Image getCharImage(char ch){
        return getCharImage((int)ch);
    }
    
    public static int [][] UTF8MAP = new int[][]{
        {7},
        {5,6},
        {4,6,6},
        {3,6,6,6},
        {2,6,6,6,6},
        {1,6,6,6,6,6},
    };
    
    public Image getUTF8CharImage(byte[] chars){
        int len = -1;
        if((chars[0] & 0x80) == 0){
            len = 1;
        }else if((chars[0] & 0x20) == 0){
            len = 2;
        }else if((chars[0] & 0x10) == 0){
            len = 3;
        }else if((chars[0] & 0x08) == 0){
            len = 4;
        }else if((chars[0] & 0x04) == 0){
            len = 5;
        }else if((chars[0] & 0x02) == 0){
            len = 6;
        }
        if(len == -1 || len > chars.length){
            System.err.println("getUTF8CharImage utf8Char error!");
            return null;
        }
        int id = 0;
        for(int i=0;i<len;i++){
            int c = (int)(((byte)chars[i])&0xff);
            id += (c & (0xff>>(8 -UTF8MAP[len][i]))) << 6*(len-i-1);
            //System.err.println("================ chars[i]:"+chars[i]+ " c:" + c);
        }
        //System.err.println("================:"+id);
        return getCharImage(id);
    }
    public Image getUTF8CharImage(String utf8){
        return getUTF8CharImage(utf8.getBytes());
    }
    
    private class FntChar{
        
        protected int _char_id = -1;
        protected int _char_x = -1;
        protected int _char_y = -1;
        protected int _char_width = -1;
        protected int _char_height = -1;
        protected int _char_xoffset = -1;
        protected int _char_yoffset = -1;
        protected int _char_xadvance = -1;
        protected int _char_page = -1;
        protected int _char_chnl = -1;
        
        public FntChar(String info){
            //System.err.println("fnt line:"+info);
            int pos = info.indexOf("char");
            int len = info.length();
            // 过滤nfo级后面的空格
            while(info.charAt(pos) != ' ' && pos < len){
                pos++;
            }

            String key = null;
            String value = null;
            int start;

            while(pos < len){
                while(pos < len && info.charAt(pos) == ' '){pos++;} // 过滤空格
                if(pos >= len)break;
                start = pos;
                while(pos < len && info.charAt(pos) != '='){
                    pos++;
                }
                key = info.substring(start, pos++);
                if(pos >= len)break;
                if(info.charAt(pos) == 34)pos++;// 34="
                start = pos;
                while(pos < len && info.charAt(pos) != ' '){ // 34="
                    pos++;
                }
                value = info.substring(start, info.charAt(pos-1) == 34 ? pos-1 : pos);
                //System.out.println("key = " + key + " value = "+value);

                if(key.equals("id")){
                    _char_id = Integer.parseInt(value);
                }else if(key.equals("x")){
                    _char_x = Integer.parseInt(value);
                }else if(key.equals("y")){
                    _char_y = Integer.parseInt(value);
                }else if(key.equals("width")){
                    _char_width = Integer.parseInt(value);
                }else if(key.equals("height")){
                    _char_height = Integer.parseInt(value);
                }else if(key.equals("xoffset")){
                    _char_xoffset = Integer.parseInt(value);
                }else if(key.equals("yoffset")){
                    _char_yoffset = Integer.parseInt(value);
                }else if(key.equals("xadvance")){
                    _char_xadvance = Integer.parseInt(value);
                }else if(key.equals("page")){
                    _char_page = Integer.parseInt(value);
                }else if(key.equals("chnl")){
                    _char_chnl = Integer.parseInt(value);
                }
                if(pos >= len)break;
            }
        }
    }
    
}
