/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class MobPatrolNode extends Marker{
    public int pathIndex = -1;
    public int nodeNumber = 0;
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\pathnode";
        SETTING_RANGE = true;
        RangeSettingName = "ID";
        SETTING_STRENGTH = true;
        StrengthSettingName = "№";
        DefRange = -1;
        DefStrength = 0;
    }
    
    @Override
    public void setRangeSetting(int id){
        pathIndex = id;
        DefRange = id;
    }    
    
    @Override
    public void setStrengthSetting(int num){
        nodeNumber = num;
        DefStrength = num;
    }
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        gr.drawString("ID "+pathIndex, 0, 10);
        gr.drawString("№ "+nodeNumber, 0, 20);
        return nimg;
    }
}
