/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.light;

import gameengine.Defines;
import gameengine.LightSource;
import gameengine.Main;
import gameengine.objects.Ammo;
import java.awt.Rectangle;

/**
 *
 * @author Camper2012
 */
public class Lamp extends LightSource{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\light\\lamp";
        isInvs = false;
        staticSize = false;
        layer = Defines.LAYER_WALL-0.1f;
        saveable = true;
        DefRange = 100;
        pass_flags |= Defines.PASS_PROJECTILE_COLLIDE;
    }
    
    @Override
    public Rectangle getCollideShape(){
        Rectangle cr = new Rectangle(x+Defines.ICON_SIZE/4,y+Defines.ICON_SIZE/4,Defines.ICON_SIZE/2,Defines.ICON_SIZE/2);
        int fx = 0;
        int fy = 0;
        if(direction == 0 || direction == 360){
            fx = -Defines.ICON_SIZE/4;
        }
        if(direction == 270){
            fy = -Defines.ICON_SIZE/4;
        }
        if(direction == 180){
            fx = Defines.ICON_SIZE/4;
        }
        if(direction == 90){
            fy = Defines.ICON_SIZE/4;
        }
        cr.translate(fx, fy);
        return cr;
    }
    
    @Override
    public void onShot(Ammo ammo){
        crash();
    }
    
    @Override
    public void crash(){
        iconName = basicIconName+"_broken";
        brightness = 0;
        pass_flags &= ~Defines.PASS_PROJECTILE_COLLIDE;
        playSound(Main.pickStringVariant("object\\glassbreak", 3), getSoundVolume()-10);
        alertNearMobs(false, true, range);
    }
}
