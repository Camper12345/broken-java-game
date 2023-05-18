/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.Ammo;
import gameengine.objects.Mob;

/**
 *
 * @author Camper2012
 */
public class ReinforcedWindow extends Window{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\solid\\rwindow";
    }  
    
    @Override
    public void onShot(Ammo a){
        if(a.damage>15){
            crash();
        }else{
            playSound("object\\glasshit", getSoundVolume());
        }
    }
    
    @Override
    public void onCollide(GameObject obj){
        if(obj instanceof Mob){
            Mob m = (Mob) obj;
            if(m.jump && !m.crawl){
                playSound("object\\glasshit", getSoundVolume());
                m.jump = false;
            }
        }
    }
}
