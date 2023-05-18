/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import gameengine.GUI;
import gameengine.Main;
import gameengine.objects.Button;
import java.awt.event.KeyEvent;

/**
 *
 * @author Camper2012
 */
public class GameMenuGUI extends GUI{
    Button cont;
    Button restart;
    Button tomain;    

    @Override
    public void init(int nx, int ny) {
        cont = (Button) addElement(new Button(), 32, 64);
        cont.setTxt("ПРОДОЛЖИТЬ");
        
        restart = (Button) addElement(new Button(), 32, 64+40);
        restart.setTxt("ЗАНОВО");
        
        tomain = (Button) addElement(new Button(), 32, 64+40*2);
        tomain.setTxt("В ГЛАВНОЕ МЕНЮ");
    }

    @Override
    public void onUpdate() {
        
        Main.window.master.pause = true;
        
        if(cont.clicked || checkKey(KeyEvent.VK_ESCAPE)){
            Main.window.master.pause = false;
            remove();
        }
        
        if(restart.clicked){
            Main.window.game.levelRestart = true;
            remove();
        }
        
        if(tomain.clicked){
            remove();
            Main.window.game.goToMainMenu();
        }
    }
    
    
}
