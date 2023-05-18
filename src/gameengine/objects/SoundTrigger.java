/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;
import gameengine.Main;
import gameengine.SoundSystem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class SoundTrigger extends Trigger{
    public String sound = "";
    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\sound";
    }
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = Main.window.master.over;
        gr.setColor(Color.green);
        gr.drawString(sound, 0, 10);
        return nimg;
    }

    @Override
    public void onActivate(GameObject obj) {
        SoundSystem.playSound(sound, 100);
    }
    
}
