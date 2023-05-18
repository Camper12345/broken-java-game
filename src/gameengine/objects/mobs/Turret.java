/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mobs;

import gameengine.objects.Mob;
import gameengine.objects.weapon.TurretGun;

/**
 *
 * @author Camper2012
 */
public class Turret extends Mob{

    @Override
    public void initVars() {
        super.initVars();
        iconsDir = "mob\\turret";
        basicIconName = iconsDir+"\\body";
        maxhp = 500;
        hp = 500;
        canKnockout = false;
        sight_angle = 150;
        sight_dark_dist = 200;
        sight_back_dist = 30;
        weapon = new TurretGun();
    }

    @Override
    public void onDeath() {
        super.onDeath();
        explode(100, 1);
    }

    
    
    @Override
    public boolean isAllied(Mob mob) {
        return mob instanceof Turret;
    }

    @Override
    public void removeWeapon() {
    }

    @Override
    public void moving() {
    }
    
    
    
}
