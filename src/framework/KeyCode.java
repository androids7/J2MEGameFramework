/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import javax.microedition.lcdui.Canvas;

/**
 *
 * @author czl
 */
public class KeyCode {
    public final static int UP = 1;
    public final static int DOWN = 1 << 1;
    public final static int LEFT = 1 << 2;
    public final static int RIGHT = 1 << 3;
    public final static int NUM1 = 1 << 4;
    public final static int NUM2 = 1 << 5;
    public final static int NUM3 = 1 << 6;
    public final static int NUM4 = 1 << 7;
    public final static int NUM5 = 1 << 8;
    public final static int NUM6 = 1 << 9;
    public final static int NUM7 = 1 << 10;
    public final static int NUM8 = 1 << 11;
    public final static int NUM9 = 1 << 12;
    public final static int NUM0 = 1 << 13;
    public final static int RETURN = 1 << 14;
    public final static int M = 1 << 15;
    public final static int FIRE = 1 << 16;
    public final static int PAGEUP = 1 << 17;
    public final static int PAGEDOWN = 1 << 18;
    public final static int MENU = 1 << 19;
    public final static int DELETE = 1 << 20;
    public final static int IME = 1 << 21;
    public final static int SOFTKEYPAD = 1 << 22;
    public final static int TV = 1 << 23;
    
    protected static int changeKeyCode(int keyCode){
        switch (keyCode) {
            case -1:
            case Canvas.UP:
                return UP;
            case -2:
            case Canvas.DOWN:
                return DOWN;
            case -3:
            case Canvas.LEFT:
                return LEFT;
            case -4:
            case Canvas.RIGHT:
                return RIGHT;
            case -5:
            case Canvas.FIRE:
                return FIRE;
            case -20:
                return PAGEUP;
            case -21:
                return PAGEDOWN;
            case -7:
                return RETURN;
            case -9:
            case -11:
                return RETURN;
            case -6:
                return MENU;
            case -30:
                return IME;
            case 42:
                return SOFTKEYPAD;
            case -8:
                return RETURN;
            case -31:// 歌华
                return RETURN;
            case 35:
                return TV;
            case Canvas.KEY_NUM1:// 新键值写法：case 0x31:{return NUM1;}
                return NUM1;
            case Canvas.KEY_NUM2:// 新键值写法：case 0x32:{return NUM2;}
                return NUM2;
            case Canvas.KEY_NUM3:// 新键值写法：case 0x33:{return NUM3;}
                return NUM3;
            case Canvas.KEY_NUM4:// 新键值写法：case 0x34:{return NUM4;}
                return NUM4;
            case Canvas.KEY_NUM5:// 新键值写法：case 0x35:{return NUM5;}
                return NUM5;
            case Canvas.KEY_NUM6:// 新键值写法：case 0x36:{return NUM6;}
                return NUM6;
            case Canvas.KEY_NUM7:// 新键值写法：case 0x37:{return NUM7;}
                return NUM7;
            case Canvas.KEY_NUM8:// 新键值写法：case 0x38:{return NUM8;}
                return NUM8;
            case Canvas.KEY_NUM9:// 新键值写法：case 0x39:{return NUM9;}
                return NUM9;
            case Canvas.KEY_NUM0:// 新键值写法：case 0x30:{return NUM0;}
                return NUM0;
            case 468:
                return RETURN;
            default:
                return keyCode;
        }
    }
    
}
