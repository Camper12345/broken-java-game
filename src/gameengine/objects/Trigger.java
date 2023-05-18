/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class Trigger extends Marker{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\trigger";
        
    }
    @Override
    public void onCollide(GameObject obj){
        if(isActivate(obj)){
            onActivate(obj);
        }
    }
    
    public boolean isActivate(GameObject obj){
        return obj instanceof Player;
    }
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        gr.drawString("ID "+id, 0, 10);
        //gr.drawString("", 0, 20);
        return nimg;
    }
    
    public void onActivate(GameObject obj){
        
    }
}
