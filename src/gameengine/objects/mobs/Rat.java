/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mobs;

import gameengine.Defines;
import gameengine.DynamicObject;
import gameengine.objects.Mob;
import gameengine.objects.weapon.Fists;
import gameengine.objects.weapon.RatBiter;
import java.awt.Rectangle;

/**
 *
 * @author Camper2012
 */
public class Rat extends Mob{
    @Override
    public void initVars(){
        super.initVars();
        weapon = new RatBiter();
        iconsDir = "mob\\rat";
        maxhp = 10;
        hp = 10;
        ALERT_ALEVEL = 50;
        ATTACK_ALEVEL = 100;
        basicIconName = iconsDir+"\\body";
        hasFlashLight = false;
        hasStandPoint = false;
        hasStepSound = false;
        fullHeight = false;
        stMovespeed = 7;
        sight_dist = 200;
        sight_angle = 150;
        sight_back_dist = 180;
        sight_dark_dist = 200;
    }

    @Override
    public Rectangle getCollideShape(int nx, int ny){
        Rectangle collsh = new Rectangle(nx+width/8*3,ny+height/8*3,(width/4)-1,(height/4)-1);       
        return collsh;
    }
    
    @Override
    public boolean isAllied(Mob mob) {
        return mob instanceof Rat;
    }

    @Override
    public boolean isAttack(Mob mob) {
        return !isAllied(mob);
    }

    @Override
    public boolean reactOn(DynamicObject obj) {
        return obj instanceof Mob;
    }
    
}
