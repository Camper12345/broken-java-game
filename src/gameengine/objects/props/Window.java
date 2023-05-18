/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.*;
import gameengine.objects.items.GlassShard;

/**
 *
 * @author Camper2012
 */
public class Window extends Fence{
    @Override
    public void initVars(){
        super.initVars();
        fullHeight = true;
        basicIconName = "obj\\solid\\window";
    }    
    
    @Override
    public void onShot(Ammo a){
        crash();
    }
    
    @Override
    public void onCollide(GameObject obj){
        if(obj instanceof Mob){
            Mob m = (Mob) obj;
            if(m.jump && !m.crawl){
                m.damage(10);
                crash();
            }
        }
    }
    
    @Override
    public void crash(){
        playSound(Main.pickStringVariant("object\\glassbreak", 3), getSoundVolume()-5);
        createObject(new GlassShard(), x,y,z);
        remove();
    }
}
