/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;
import gameengine.Main;
import java.io.File;

/**
 *
 * @author Camper2012
 */
public class SmoothMapLoader extends MapLoaderMarker{
    @Override
    public void onActivate(GameObject obj){
        File mapf = new File(Main.window.mapDir+map+Main.window.mapFormat);
        if(mapf.exists() && !Main.window.master.loading){
        Main.window.savePlayer(mapf+".map");
        Main.window.game.currentMap = mapf;
        Main.window.game.levelRestart = true;
        }
    }
}
