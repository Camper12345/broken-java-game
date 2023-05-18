/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Turf;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class ObjectMover extends Marker{
    public int moveSpeed = 0;
    public boolean isMove = false;
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\mover";
        SETTING_STRENGTH = true;
        DefStrength = 0;
        StrengthStep = 1;
        StrengthSettingName = "СКОРОСТЬ";
    }
    
    @Override
    public void setStrengthSetting(int spd){
        moveSpeed = Math.max(spd,0);
    }
    
    @Override
    public BufferedImage specialUpdateIconDebug(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        gr.drawString(""+moveSpeed, scrX, scrY+10);
        gr.drawString(""+isMove, scrX, scrY+20);
        return nimg;
    }
    
    @Override
    public void onUpdate(){
        if(isMove && moveSpeed>0){
            
        }
    }
}
