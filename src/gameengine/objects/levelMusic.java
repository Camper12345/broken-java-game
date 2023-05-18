/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class levelMusic extends Marker{
    public static final String[][] MUSIC = {{}};
    
    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\levelmusic";
    }

    @Override
    public void init(int nx, int ny) {
        
    }
    
    
    
    @Override
    public BufferedImage specialUpdateIconOver(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        gr.drawString("L "+id, 0, 10);
        //gr.drawString("", 0, 20);
        return nimg;
    }
}
