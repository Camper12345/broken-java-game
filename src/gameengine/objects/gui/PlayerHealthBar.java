/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import gameengine.Defines;
import gameengine.GUI;
import gameengine.Main;
import gameengine.StaticObject;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class PlayerHealthBar extends GUI{
    float hppc = 0;
    
    @Override
    public void initVars(){
        super.initVars();
        staticSize = true;
        width = 216;
        height = 32;
        isVisible = true;
    }
    
    public void setDrawHp(float hpmult){
        hppc = hpmult;
    }
    
    @Override
    public BufferedImage specialUpdateIcon(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
            //HEALTH
            gr.setColor(new Color(16,16,16,128));
            gr.fillRect(0, 0, 216, 32);
            if(hppc>=0 && hppc<=1){
            gr.setColor(new Color((int)(64+(1-hppc)*(255-64)),(int)(hppc*255),(int)((1-hppc)*64)));
            gr.fillRect(8, 32-10, (int) (hppc*200), 6);
            gr.drawRect(8, 32-10, 200, 6);
            }
        return nimg;
    }
}
