/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.Explosion;
import gameengine.objects.Mob;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author Camper2012
 */
public class DynamicObject extends GameObject {
  
    @Override
    public void initVars(){
        collideType = 1;
        flags = Defines.OBJECT_IS_CONTAINER;
    }
    
    @Override
    public void onOver(){
        Player pl = Main.window.master.playerObj;
        if(checkKeyClick(KeyEvent.VK_E)){
            if(getDistance(pl)<=Defines.ICON_SIZE){
                if(true || !getCollideLine(pl.weapon, this)){
                    onPlayerUse();
                }
            }
        }
    }
    
    public void alertNearMobs(DynamicObject obj, boolean sight, boolean rsight, int range, int zrange){
        for(Mob mob : Main.window.mobs){
            if((Main.isNull(mob.aiTarget) || !(mob.aiTarget instanceof Mob)) && mob.alert_level<Mob.ATTACK_ALEVEL){
                if(mob.reactOn(obj) && getDistance(mob)<=range && Math.abs(z-mob.z)<=zrange && 
                        (!sight || mob.isInSight(this)) && (!rsight || !mob.isSightBlocked(obj))){
                    mob.primeAlertTimer();
                    mob.aiTarget = obj;
                    mob.setTimer(Mob.CHASE_DELAY_TIMER, 5);
                }
            }
        }
    }
    
    public void alertNearMobs(boolean sight, boolean rsight, int range, int zrange){
        alertNearMobs(this, sight, rsight, range, zrange);
    }
    public void alertNearMobs(boolean sight, boolean rsight, int range){
        alertNearMobs(sight, rsight, range, 0);
    }
    
    public void crash(){
        
    }
    
    public void onPlayerUse(){
    }
    
    public Turf getUnderTurf(){
        Turf t = null;
        int maxd = Integer.MAX_VALUE;
        for(GameObject gobj : collidesND){
            if(gobj instanceof Turf && !(gobj instanceof Wall)){
                Turf tr = (Turf) gobj;
                if(tr.getDistance(this)<maxd){
                    t = tr;
                    maxd = tr.getDistance(this);
                }
            }
        }
        return t;
    }
    
    public void explode(){
        explode(100);
    }
   
    public void explode(int range){
        explode(range,2);
    }
    
    public void explode(int range, float str){
        explode(range,str,new Color(255,96,0));
    }
    
    public void explode(int range, float strength, Color color){
        Explosion ex = (Explosion) createObject(new Explosion(), x-Explosion.getSize()/2+width/2, y-Explosion.getSize()/2+height/2, z);
        
        ex.setRange(range);
        ex.setStrength(strength);
        ex.setColor(color);

        ex.detonate();
    }
}
