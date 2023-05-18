/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.mobs.Player;
import java.io.File;

/**
 *
 * @author Camper2012
 */
public class MapLoaderOnDeath extends MapLoaderMarker{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\damage";
    }
    
    @Override
    public void onActivate(GameObject obj){
        Player pl = Main.window.master.playerObj;
        File mapf = new File(Main.window.mapDir+map+Main.window.mapFormat);
        if(mapf.exists()){
            pl.loadMapOnDeath = true;
            pl.deathMap = map;
        }
    }
}
