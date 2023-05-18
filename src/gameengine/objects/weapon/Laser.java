/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

import gameengine.objects.Ammo;

/**
 *
 * @author Camper2012
 */
public class Laser extends Ammo{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\projectile\\laser";
        damage = 10;
        opacityCollide = true;
        
    }
    
}
