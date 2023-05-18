/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

import gameengine.objects.Mob;
import gameengine.objects.Weapon;

/**
 *
 * @author Camper2012
 */
public class TurretGun extends Weapon{

    @Override
    public void initVars(){
        super.initVars();
        wpIcon = "";
        shotsound = "weapon\\turret";
        ammotype = new HeavyLaser();
        shotdelay = 10;
        shotspeed = 27;
        maxammo = 5;
        maxholdammo = 50;
        ammo = 5;
        holdammo = 50;
        reloaddelay = 30;
    }
    
}
