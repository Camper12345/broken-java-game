/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.items;

import gameengine.Main;
import gameengine.objects.Weapon;
import gameengine.objects.mobs.Player;

/**
 *
 * @author Camper2012
 */
public class Battery extends Item{
    public int power = 20;
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\item\\battery";
    }

    @Override
    public void onPlayerUse() {
        int addp = (int)(Math.random()*power)+power/2;
        Player pl = Main.window.master.playerObj;
        Weapon wp = pl.weapon;
        if(!wp.isMelee() && wp.holdammo<wp.maxholdammo){
            wp.holdammo = Math.max(wp.maxholdammo,wp.holdammo+power);
            remove();
        }
    } 
}
