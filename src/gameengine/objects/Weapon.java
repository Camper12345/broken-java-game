/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import gameengine.SoundSystem;
import gameengine.objects.props.Barrier;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camper2012
 */
public class Weapon extends DynamicObject{
    public boolean canShot = true;
    public int ammo = 50;
    public int maxammo = 50;
    public int holdammo = 500;
    public int maxholdammo = 500;
    public String shotsound = "";
    public String reloadsound = "weapon\\rifle_reload";
    public int accuracy = 3;
    public int shotdelay = 2;
    public int reloaddelay = 10;
    public int shotspeed = 30;
    public int bulletspershot = 1;
    public boolean slience = false;
    public boolean automatic = true;
    public Ammo ammotype = new Ammo();
    public boolean shot = false;

    public long delayTimer = 0;
    public long reloadTimer = 0;
    
    public static int SHOTSHOW_TIMER = 0;
    
    public String wpIcon = "obj\\weapon\\weapon";
    public String handing = "w2h"; // w2h  2wh  wh  mw - melee
            
    @Override
    public void initVars(){
        super.initVars();
        ammotype.initVars();
        basicIconName = "";
        inFullHeight = true;
        staticSize = true;
        isVisible = false;
    }
    
    
    public boolean reload(boolean sound){
        if(holdammo <= 0 || isReloading() || ammo>=maxammo || isMelee()){
            return false;
        }
        holdammo += ammo;
        ammo = holdammo>=maxammo?maxammo:holdammo;
        holdammo -= holdammo>=maxammo?maxammo:holdammo;
        reloadTimer = Main.window.master.time;
        if(!"".equals(reloadsound) && sound){
            SoundSystem.playSound(reloadsound,getSoundVolume()-15);
        }
        return true;
    }
    
    public boolean reload(){
        return reload(true);
    }

    @Override
    public boolean isCollide(GameObject obj) {
        boolean pc = Defines.checkFlag(obj.pass_flags, Defines.PASS_PROJECTILE_COLLIDE);
        boolean pp = Defines.checkFlag(obj.pass_flags, Defines.PASS_PROJECTILE_PASS);
        boolean dens = ((obj.isDensity && obj.fullHeight) && !ammotype.opacityCollide && !pp);
        boolean opac = ((!obj.isOpacity || pc) && ammotype.opacityCollide);
        boolean barr = (!(obj instanceof Barrier) || Main.dir_diff(direction, obj.direction)<=90);
        return (dens || opac) && barr;
    }
    
    public boolean isReloading(){
        return !(Main.window.master.time-reloadTimer>=reloaddelay);
    }
    
    public boolean canShot(){
        return (hasAmmo() && !isReloading());
    }
    
    public boolean readyToShot(){
        if(!isCooling() && Main.window.master.time-reloadTimer>=reloaddelay){
            return true;
        }
        return false;
    }
    
    public boolean isCooling(){
        return !(Main.window.master.time-delayTimer>=shotdelay);
    }
    
    public boolean hasAmmo(){
        return ammo > 0;
    }
    
    public boolean isMelee(){
        return (ammotype instanceof MeleeAmmo);
    }
    
    public int getAttackDist(){
        if(isMelee()){
            return getMeleeAttackDist();
        }else{
            return getRangedAttackDist();
        }    
    }
    
    public int getMeleeAttackDist(){
        return Defines.MELEE_ATTACK_DIST;
    }
    
    public int getAmmoPathLength(){
        int t = (int)Math.floor(Math.sqrt(Math.pow(Math.sqrt((ammotype.mass*ammotype.mass)+1)*(shotspeed/ammotype.mass),2)-shotspeed*shotspeed));
        return (int)(shotspeed*t-(ammotype.mass*t*t)/2);
    }
    
    public int getRangedAttackDist(){
        return (int) (3*Math.sqrt(Math.pow((Defines.ICON_SIZE/2)/Math.sin(Math.toRadians(accuracy)),2)-Math.pow(Defines.ICON_SIZE/2,2)));
    }
    
    public boolean shot(){
        if(!readyToShot()){
            return false;
        }
        delayTimer = (long) (Main.window.master.time+((Math.random()*(shotdelay/4))-(shotdelay/8)));
        
        if((!hasAmmo() && !isMelee()) || !canShot){
            if(!isMelee()){
            playSound("weapon\\empty");
            }
            return false;
        }
        
        for(int i = 1; i<=bulletspershot; i++){
        if(!hasAmmo()){
            continue;
        }
        if(!isMelee()){
        ammo -= 1;
        }
        try {
            Ammo sbl = ammotype.getClass().newInstance();
            float sdir = direction+(int)(Math.random()*(accuracy*2))-accuracy;
            createObject(sbl,x+Main.ld_x(Defines.MELEE_ATTACK_DIST-10, sdir),
                    y+Main.ld_y(Defines.MELEE_ATTACK_DIST-10, sdir),z);
            sbl.direction = sdir;
            if(!isMelee()){
            sbl.applyForce(sdir,shotspeed);
            }
            sbl.ignoring = container;
        } catch (InstantiationException | IllegalAccessException ex) {
            Main.error("Error while shooting "+ex);
            return false;
        }
        }
        shot = true;
        setTimer(SHOTSHOW_TIMER, 1);
        if(!"".equals(shotsound)){
            SoundSystem.playSound(shotsound,getSoundVolume());
            if(!slience && !Main.isNull(container)){
                alertNearMobs(false, true, 300);
            }
        }
        return true;
    }
    
    @Override
    public void onTimer(int ind){
        if(ind == SHOTSHOW_TIMER){
            shot = false;
        }
    }
}
