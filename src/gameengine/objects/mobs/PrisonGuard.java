/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mobs;

import gameengine.objects.Mob;
import gameengine.objects.weapon.PrisonGuardGun;

/**
 *
 * @author Camper2012
 */
public class PrisonGuard extends Mob{
    @Override
    public void initVars(){
        super.initVars();
        weapon = new PrisonGuardGun();
        iconsDir = "mob\\prisonguard";
        basicIconName = iconsDir+"\\body";
    }

    @Override
    public boolean isAllied(Mob mob) {
        return mob instanceof PrisonGuard;
    }
    
    
}
