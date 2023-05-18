/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.items;

import gameengine.Defines;
import gameengine.Main;
import gameengine.objects.Weapon;
import gameengine.objects.mobs.Player;
import gameengine.objects.weapon.Fists;

/**
 *
 * @author Camper2012
 */
public class LyingWeapon extends Item{
    public Weapon weapon;
    @Override
    public void initVars(){
        super.initVars();
    }
    
    @Override
    public void onUpdate(){
        if(!Main.isNull(weapon)){
        iconName = weapon.wpIcon+"_lying";
        basicIconName = weapon.wpIcon+"_lying";
        layer = Defines.LAYER_ITEM_UP;
        }
    }
    
    @Override
    public void onPlayerUse(){
        Player pl = Main.window.master.playerObj;
        if(!(pl.weapon instanceof Fists)){
            if(weapon.getClass().equals(pl.weapon.getClass())){
                pl.getWeapon(weapon);
                remove();
            }
        }else{
            pl.getWeapon(weapon);
            remove();
        }
    }
}
