/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.DynamicObject;
import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.mobs.Player;

/**
 *
 * @author Camper2012
 */
public class TeleporterTrigger extends Trigger{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\tele";
    }

    @Override
    public void onActivate(GameObject gobj) {
        Player pl = Main.window.master.playerObj;
        for(DynamicObject obj : Main.window.nodes){
            if(obj instanceof TeleporterReceiver){
                TeleporterReceiver tr = (TeleporterReceiver) obj;
                if(tr.id == id && getCollideList(tr.x+tr.width/2, tr.y+tr.height/2, tr.z, false, false, false).isEmpty()){
                    pl.move(tr.x+tr.width/2, tr.y+tr.height/2);
                    pl.moveZ(tr.z);
                }
            }
        }
    }
    
    
}
