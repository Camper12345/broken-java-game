/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import static gameengine.Frame.savesDir;
import gameengine.GUI;
import gameengine.Main;
import gameengine.objects.Button;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 *
 * @author Camper2012
 */
public class MainMenuGUI extends GUI{
    Button play;
    Button cont;
    Button exit;    

    @Override
    public void init(int nx, int ny) {
        play = (Button) addElement(new Button(), 64, 64+40);
        play.setTxt("НОВАЯ ИГРА");
        
        cont = (Button) addElement(new Button(), 64, 64+40*2);
        cont.setTxt("ПРОДОЛЖИТЬ");
        
        exit = (Button) addElement(new Button(), 64, 64+40*3);
        exit.setTxt("ВЫХОД");
    }

    @Override
    public void onUpdate() {
        
        Main.window.master.pause = true;
        
        File save = new File(savesDir+"save.sav");
        if(save.exists()){
            cont.clickable = true;
            cont.setTxtColor(Color.white);
        }else{
            cont.clickable = false;
            cont.setTxtColor(Color.black);
        }
        
        if(cont.clicked){
            if(save.exists()){
                Main.window.game.loadGame();
            }
        }
        
        if(exit.clicked){
            System.exit(0);
        }
        
        if(play.clicked){
            remove();
            Main.window.game.startNewGame();
        }
    }
}
