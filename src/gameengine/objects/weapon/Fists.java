/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

import gameengine.objects.MeleeAmmo;
import gameengine.objects.Weapon;

/**
 *
 * @author Camper2012
 */
public class Fists extends Weapon{
    @Override
    public void initVars(){
        super.initVars();
        wpIcon = "obj\\weapon\\fist\\fist";
        handing = "mw";
        shotsound = "";
        automatic = false;
        shotdelay = 5;
        accuracy = 25;
        ammotype = new MeleeAmmo();
    }
}
