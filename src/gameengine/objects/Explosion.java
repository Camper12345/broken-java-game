/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class Explosion extends DynamicObject {
    public float strength = 2f;
    public LightSource blowlight;
    public Color color = new Color(255,96,0);
    public int range = 100;
    public boolean start = false;
    public int exptime = Defines.TICKLAGDIV*5;
    @Override
    public void initVars(){
        basicIconName = "effects\\explosion";
        isMovable = false;
        animIndex = -1;
        cycleAnim = false;
        flags = 0;
        can_lighting = false;
        staticSize = true;
        layer = Defines.LAYER_PROJECTILE;
        width = 96;
        height = 96;
        /*offX = -width/2;
        offY = -height/2;*/
    }
    
    @Override
    public void init(int nx, int ny){
    }
    
    public void setRange(int r){
        range = r;
    }
    public void setStrength(float s){
        strength = s;
    }
    public void setColor(Color col){
        color = col;
    }
    public void detonate(){
        cycleAnim = true;
        animIndex = 0;
        blowlight = (LightSource) createObject(new LightSource(),x+width/2, y+height/2, z);
        blowlight.setColor(color);
        blowlight.setBrightness(255);
        blowlight.setRange(range*2);
        playSound(Main.pickStringVariant("ambience\\boom",3),getSoundVolume());
        alertNearMobs(this, false,false, range*4,1);
        for(Object cho : Main.window.map.toArray()){
            Chunk ch = (Chunk) cho;
            if(ch.getDistance(x+width/2, y+height/2)<=range+Defines.CHUNK_SIZE){
                for(Object gobj : ch.contents.toArray()){
                    GameObject obj = (GameObject) gobj;
                    int dist = getDistance(obj);
                    float force = (float) ((range-getDistance(obj))/range*strength);
                    if(dist<range && z==obj.z){
                        if(obj.isMovable){
                        //obj.applyForce(Main.get_dir(x+width/2, y+height/2, obj.x+obj.width/2, obj.y+obj.height/2),force*10*obj.mass);
                        }
                        if(obj instanceof DynamicObject){
                            DynamicObject dobj = (DynamicObject) obj;
                            dobj.crash();
                         }
                        if(obj instanceof Mob){
                            Mob m = (Mob) obj;
                            float dmg = ((float)range-(float)dist)/(float)range*(70f);
                            m.damage((int) (dmg*strength));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onAnimationCycle() {
        blowlight.remove();
        remove();
    }
    
    public static int getSize(){
        return 96;
    }
}
