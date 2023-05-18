/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

/**
 *
 * @author Camper2012
 */
public class HeavyLaser extends Laser{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\projectile\\heavylaser";
        damage = 80;
        mass = 0.1f;
    }
    
}
