/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mobs;

import gameengine.DynamicObject;
import gameengine.objects.Mob;
import gameengine.objects.weapon.Fists;
import gameengine.objects.weapon.PrisonGuardGun;

/**
 *
 * @author Camper2012
 */
public class Hobo extends Mob{
    @Override
    public void initVars(){
        super.initVars();
        weapon = new Fists();
        iconsDir = "mob\\hobo";
        basicIconName = iconsDir+"\\body";
        hasFlashLight = false;
        hasStandPoint = false;
        maxhp = 70;
        hp = 70;
    }

    @Override
    public boolean isAllied(Mob mob) {
        return false;
    }

    @Override
    public boolean isAttack(Mob mob) {
        return false;
    }

    @Override
    public boolean reactOn(DynamicObject obj) {
        return false;
    }
    
    
    
}
