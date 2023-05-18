/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

import gameengine.objects.Weapon;

/**
 *
 * @author Camper2012
 */
public class PoliceGun extends Weapon{
    @Override
    public void initVars(){
        super.initVars();
        wpIcon = "obj\\weapon\\police\\police";
        shotsound = "weapon\\policegun";
        ammotype = new SmallLaser();
        maxammo = 80;
        ammo = 80;
        shotspeed = 30;
        shotdelay = 7;
        holdammo = 800;
        maxholdammo = 800;
        bulletspershot = 5;
        accuracy = 7;
    }
    
}
