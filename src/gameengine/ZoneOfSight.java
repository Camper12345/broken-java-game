/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.Text;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class ZoneOfSight extends StaticObject{
    
    @Override
    public void init(int nx, int ny){
        isMovable = false;
        layer = Defines.LAYER_LIGHT+0.1f;
        staticPos = true;
    }
    
    @Override
    public boolean update_icon(boolean reload){
        boolean succ = false;
        Image img = new BufferedImage(Defines.WIDTH,Defines.HEIGHT,BufferedImage.TYPE_INT_ARGB);
        icon = null;
            Graphics gr = img.getGraphics();
            if(!Main.isNull(Main.window.master.playerObj) && Defines.SIGHT_ENABLED){
                Player pl = Main.window.master.playerObj;
            for(Object po : ViewEngine.getLightPoly(pl.x+pl.width/2,pl.y+pl.height/2,Main.window.master.currentZ,-Main.window.master.objShiftX,-Main.window.master.objShiftY).toArray()){
                Polygon p = (Polygon)po;
                gr.setColor(Color.black);
                gr.fillPolygon(p);
            }
            }
            width = Defines.WIDTH;
            height = Defines.HEIGHT;
            icon = (BufferedImage)img;
            succ = true;
        return succ;
    } 
    
    @Override
    public void onUpdate(){
        scrX = 0;
        scrY = 0;
        updateIcon();
        move(Main.window.master.objShiftX,Main.window.master.objShiftY);
    }
    }
