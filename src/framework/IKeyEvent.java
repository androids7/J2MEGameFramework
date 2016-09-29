/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 *
 * @author Administrator
 */
public interface IKeyEvent {
    
    public boolean onKeyDown(int keyCode);
    public boolean onKeyPress(int keyCode);
    public boolean onKeyUp(int keyCode);
    
}
