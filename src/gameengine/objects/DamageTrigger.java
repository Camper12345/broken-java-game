/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class DamageTrigger extends Trigger{
    public int damage = 0;
    public boolean jump = false;
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\damage";
        SETTING_STRENGTH = true;
        StrengthSettingName = "УРОН";
        StrengthStep = 10;
        SETTING_STATUS = true;
        StatusSettingName = "ПРОЖОК";
        DefStatus = false;
    }

    @Override
    public boolean isActivate(GameObject obj) {
        return obj instanceof Mob;
    }

    @Override
    public void onActivate(GameObject obj){
        if(obj instanceof Mob){
            Mob m = (Mob) obj;
            if(!isTimer(0)){
                if(!m.jump || !jump){
                    if(damage>=0){
                        m.damage(damage);
                    }else{
                        m.damage(m.maxhp);
                    }
                    setTimer(0,10);
                }
            }
        }
    }
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        gr.drawString("D "+damage, 0, 10);
        gr.drawString("J "+jump, 0, 20);
        return nimg;
    }
    
    @Override
    public void setStrengthSetting(int str) {
        damage = str;
        DefStrength = str;
    }

    @Override
    public void setStatusSetting(boolean st) {
        jump = st;
        DefStatus = st;
    }
    
    
}
