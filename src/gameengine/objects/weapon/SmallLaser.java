/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

import gameengine.objects.Ammo;
import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class SmallLaser extends Ammo{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\projectile\\smalllaser";
        damage = 7;
        lightColor = new Color(0,50,255);
    }
    
}
