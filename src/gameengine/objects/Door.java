/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import gameengine.objects.mobs.Player;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 *
 * @author Camper2012
 */
public class Door extends DynamicObject{
    public boolean opened = false;
    public boolean moving = false;
    public boolean opacity = false;
    public int thickness = 4;
    @Override
    public void initVars(){
        super.initVars();
        cycleAnim = false;
        DefStatus = false;
        isDensity = true;
        isOpacity = false;
        SETTING_STATUS = true;
        mouseCollideShape = false;
        StatusSettingName = "ОТКРЫТО";
        basicIconName = "obj\\doors\\slidingdoor";
    }
    
    @Override
    public Rectangle getCollideShape() {
        Rectangle cr = new Rectangle(x,y,Defines.ICON_SIZE,Defines.ICON_SIZE);
            if(direction == 90 || direction == 270){
                cr = new Rectangle(x,y+Defines.ICON_SIZE/2-thickness/2,Defines.ICON_SIZE,thickness);
            }
            if(direction == 0 || direction == 360 || direction == 180){
                cr = new Rectangle(x+Defines.ICON_SIZE/2-thickness/2,y,thickness,Defines.ICON_SIZE);
            }
        return cr;
    }
    
    public boolean canOpen(Mob mob){
        return true;
    }
    
    public void open(){
        if(!opened){
        opened = true;
        moving = true;
        isOpacity = true;  
        playSound(Main.pickStringVariant("object\\doormove",1), getSoundVolume()-10);
        }
    }
    public void close(){
        if(opened){
        opened = false;
        moving = true;
        playSound(Main.pickStringVariant("object\\doormove",1), getSoundVolume()-10);
        }
    }
    public void toggle(){
        if(opened){
            close();
        }else{
            open();
        }
    }
    
    @Override
    public void onOver(){
        Player pl = Main.window.master.playerObj;
        if(checkKeyClick(KeyEvent.VK_E)){
        if(!moving && !Main.isNull(pl) && pl.z == z && getDistance(pl)<=Defines.ICON_SIZE && !getCollideLine(pl,this)){
            if(canOpen(pl)){
                if(!opened){
                    open();
                }else{
                    Rectangle dc = getCollideShape();
                    Rectangle pc = pl.getCollideShape();
                    if(!pc.intersects(dc)){
                        close();
                    }
                }
            }else{
                playSound("object\\doornoaccess", getSoundVolume()-5);
            }
        }
        }
    }
    
    @Override
    public void onUpdate(){
        if(!moving){
            if(opened){
                animIndex = loadImage(basicIconName).getLength()-1;
            }else{
                animIndex = 0;
            }
        }else{
            if(opened){
                animIndex++;
                if(animIndex>=loadImage(basicIconName).getLength()-1){
                    moving = false;
                    isDensity = false;              
                }
            }else{
                animIndex--;
                if(animIndex<=0){
                    moving = false;
                    isDensity = true;
                    isOpacity = opacity;
                }
            }
        }
    }
    
    @Override
    public void setStatusSetting(boolean op){
        opened = op;
        isDensity = !op;
        isOpacity = op?true:opacity;
        DefStatus = op;
    }
    
}
