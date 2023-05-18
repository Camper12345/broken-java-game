/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.Main;
import gameengine.objects.items.Item;
import gameengine.objects.mobs.Player;

/**
 *
 * @author Camper2012
 */
public class lvl0PrisonKey extends Item{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\item\\l0key";
    }
    
    @Override
    public void onPlayerUse(){
        Main.window.master.playerObj.scenarioFlags |= Player.SFL0_HAS_PRISON_KEY;
        remove();
    }
}
