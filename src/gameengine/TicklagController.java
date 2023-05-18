/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camper2012
 */
public class TicklagController extends Thread {
    public boolean active = true;
    public TicklagController(){
        Main.info("TicklagController started with ticklag "+Defines.TICKLAGDIV+"/sec");
    } 
    
    @Override
    public void run(){
        Main.info("-----===== GAME STARTED =====-----");
        process();
    }   
    public void process(){
        while(active){
            if(!Main.isNull(Main.window) && Main.window.resource.isReady){
            Main.window.tick();}
            try {
                sleep(Defines.TICKLAG);
            } catch (InterruptedException ex) {
                Main.error("Exception at TickalgController "+ex);
            }
        }
    }
}
