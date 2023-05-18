/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.gui;

import gameengine.*;
import gameengine.objects.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;


/**
 *
 * @author Camper2012
 */
public class GridGUI extends GUI {
    public int gridsize = 1;
    public Color gridcolor = Color.white;
    public boolean notstatic = true;
    @Override
    public void init(int nx, int ny){
        
    }    
    
    @Override
    public boolean update_icon(boolean reload){
        BufferedImage img = new BufferedImage(Defines.WIDTH, Defines.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics gr = img.getGraphics();
        gr.setColor(gridcolor);
        int xoff = 0;
        int yoff = 0;
        if(notstatic){
        xoff = Main.window.master.sX-(Main.window.master.sX/gridsize*gridsize);
        yoff = Main.window.master.sY-(Main.window.master.sY/gridsize*gridsize);
        }
        if(isVisible){
        for(int dx = 0;dx<=Defines.WIDTH+gridsize;dx+=gridsize){
            gr.drawLine(dx+xoff, 0, dx+xoff, Defines.HEIGHT);
        }
        for(int dy = 0;dy<=Defines.HEIGHT+gridsize;dy+=gridsize){
            gr.drawLine(0,dy+yoff,Defines.WIDTH,dy+yoff);
        }
        }
        icon = img;
        return true;
    }
        
    @Override
    public void onUpdate(){
        updateIcon();
    }
}
