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
public class PrisonGuardGun extends Weapon{
    
    @Override
    public void initVars(){
        super.initVars();
        wpIcon = "obj\\weapon\\prison\\prison";
        shotsound = "weapon\\prisongun";
        ammotype = new Laser();
    }
}
