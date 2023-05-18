/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

import gameengine.Main;
import gameengine.objects.MeleeAmmo;

/**
 *
 * @author Camper2012
 */
public class RatBite extends MeleeAmmo{

    @Override
    public void initVars() {
        super.initVars();
        damage = 2;
    }

    @Override
    public void init(int nx, int ny) {
        super.init(nx, ny);
        hitSound = Main.pickStringVariant("weapon\\rat",1);
    }
    
    
    
}
