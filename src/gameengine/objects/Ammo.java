/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import gameengine.objects.props.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class Ammo extends DynamicObject{
    public int damage = 20; //damage with damage*speed
    public boolean opacityCollide = false;
    public DynamicObject ignoring = null;
    public LightSource light;
    public boolean hasLight = true;
    public Color lightColor = new Color(255,0,0);
    public String hitSound;
    @Override
    public void initVars(){
        super.initVars();
        layer = Defines.LAYER_PROJECTILE;
        opacityCollide = true;
        mass = 0.5f;
        can_lighting = false;
        basicIconName = "obj\\projectile\\laser";
    }
    
    @Override
    public void init(int nx, int ny){
        if(hasLight){
            light = new LightSource();
            light.initVars();
            light.color = lightColor;
            light.brightness = 64;
            light.range = 32;
            createObject(light, x,y,z);
            light.addToContainer(this);
        }
    }
    
    @Override
    public boolean isCollide(GameObject obj){
        boolean pc = Defines.checkFlag(obj.pass_flags, Defines.PASS_PROJECTILE_COLLIDE);
        boolean pp = Defines.checkFlag(obj.pass_flags, Defines.PASS_PROJECTILE_PASS);
        boolean opcspc = ((obj instanceof Window) && !(obj instanceof ReinforcedWindow)) || 
                (obj instanceof Barrier && Main.dir_diff(direction, obj.direction)<=90);
        boolean ret = (((obj instanceof Mob) && (obj.isDensity || pc))||(!(obj instanceof Mob)) && 
          (((obj.isDensity) && !opacityCollide)||((!obj.isOpacity) && 
                opacityCollide)||pc||opcspc) && !pp && (obj.fullHeight || opacityCollide || pc)) && !obj.equals(ignoring);
        
        return ret;
    }

    @Override
    public void onCollide(GameObject obj) {
        /*if(isCollide(obj)){
            obj.onShot(this);
            playSound(hitSound,getSoundVolume()-15);
            remove();
        }*/
    }
    
    @Override
    public void onUpdate(){
        if(speed<=0){
            remove();
        }
        ArrayList<GameObject> ls = getCollideLineList(x+width/2+Main.ld_x(speed,Main.bound_dir(Main.bound_dir(direction+180f))), 
                y+height/2+Main.ld_y(speed,Main.bound_dir(Main.bound_dir(direction+180f))), z,null,ignoring);
        if(!ls.isEmpty()){
            for(GameObject obj : ls){
                if(obj instanceof Barrier && Main.prob(90)){
                playSound(hitSound,getSoundVolume()-20);
                remove();
                return;
                }
            }
            for(GameObject obj : ls){
                obj.onShot(this);
            }
            playSound(hitSound,getSoundVolume()-20);
            remove();
        }
    }
    
    @Override
    public Rectangle getCollideShape(int nx, int ny){
        Rectangle collsh = new Rectangle(nx+width/8*3,ny+height/8*3,(width/4)-1,(height/4)-1);       
        return collsh;
    }
}
