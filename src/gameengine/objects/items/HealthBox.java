/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.items;

import gameengine.Main;
import gameengine.objects.mobs.Player;

/**
 *
 * @author Camper2012
 */
public class HealthBox extends Item{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\item\\health";
    }

    @Override
    public void onPlayerUse() {
        Player pl = Main.window.master.playerObj;
        if(pl.hp<pl.maxhp){
            pl.hp = pl.maxhp;
            remove();
        }
    }    
}
