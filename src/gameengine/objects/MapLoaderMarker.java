/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.GameObject;
import gameengine.Main;
import gameengine.objects.mobs.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Camper2012
 */
public class MapLoaderMarker extends Trigger{
    public int lnum = 0;
    public String map = "level";
    @Override
    public void initVars(){
        super.initVars();
        DefRange = 0;
        RangeSettingName = "level";
        basicIconName = "marker\\mapchange";
    }
    
    @Override
    public void setRangeSetting(int id){
        map = "level"+id;
        lnum = 0;
        DefRange = id;
    }
    
    @Override
    public BufferedImage specialUpdateIconDebug(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = Main.window.master.over;
        gr.setColor(Color.green);
        gr.drawString(map, scrX, scrY+10);
        return nimg;
    }
    
    @Override
    public boolean isActivate(GameObject obj){
        return obj instanceof Player;
    }
    
    @Override
    public void onActivate(GameObject obj){
        File mapf = new File(Main.window.mapDir+map+Main.window.mapFormat);
        if(mapf.exists() && !Main.window.master.loading){
        Main.window.savePlayer(map+".map");
        Main.window.game.loadMap(map,true);
        Main.window.master.loading = true;
        }
    }
}
