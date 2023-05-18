/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Defines;
import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.props.ReinforcedWindow;
import gameengine.objects.props.Window;
import java.awt.Rectangle;

/**
 *
 * @author Camper2012
 */
public class MeleeAmmo extends Ammo{
    public static int REMOVE_TIMER = 0;
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "";
        damage = 5;
        hasLight = false;
        opacityCollide = false;
    }
    
    @Override
    public void init(int nx, int ny){
        setTimer(REMOVE_TIMER,1);
        hitSound = Main.pickStringVariant("weapon\\meleehit", 4);
    }
    
    @Override
    public void onTimer(int ind){
        if(ind == REMOVE_TIMER){
            remove();
        }
    }
    
    @Override
    public void onUpdate(){
        speed = 0;
    }
    
    @Override
    public Rectangle getCollideShape(int nx, int ny){
        Rectangle collsh = new Rectangle(nx+width/4,ny+height/4,(width/2)-1,(height/2)-1);       
        return collsh;
    }
    
    @Override
    public boolean isCollide(GameObject obj){
        boolean pc = Defines.checkFlag(obj.pass_flags, Defines.PASS_PROJECTILE_COLLIDE);
        boolean pp = Defines.checkFlag(obj.pass_flags, Defines.PASS_PROJECTILE_PASS);
        boolean opcspc = ((obj instanceof Window) && !(obj instanceof ReinforcedWindow));
        boolean ret = (((obj instanceof Mob) && (obj.isDensity))||(!(obj instanceof Mob)) && 
          (((obj.isDensity) && !opacityCollide)||((!obj.isOpacity) && 
                opacityCollide)||pc||opcspc) && !pp && (obj.fullHeight || opacityCollide || pc)) && !obj.equals(ignoring);
        
        return ret;
    }
    
    @Override
    public void onCollide(GameObject obj) {
        if(isCollide(obj)){
            obj.onShot(this);
            playSound(hitSound,getSoundVolume()-15);
            remove();
        }
    }
}
