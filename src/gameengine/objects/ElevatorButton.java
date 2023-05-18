/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.DynamicObject;
import gameengine.Main;
import gameengine.objects.mobs.Player;

/**
 *
 * @author Camper2012
 */
public class ElevatorButton extends GameButton{
    @Override
    public void onActivate() {
        for(DynamicObject mrk : Main.window.nodes){
            if(mrk instanceof ElevatorButton){
                ElevatorButton btn = (ElevatorButton) mrk;
                if(btn.id == id && !btn.isTimer(0)){
                    Player pl = Main.window.master.playerObj;
                    if(pl.getCollideList(pl.x, pl.y, btn.z, false, false, false).isEmpty()){
                        pl.moveZ(btn.z);
                        setTimer(0,10);
                        btn.setTimer(0,10);
                    }
                }
            }
        }
    }
    
}
