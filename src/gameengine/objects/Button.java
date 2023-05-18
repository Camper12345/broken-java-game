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
public class Button extends StaticObject{
    public String buttonDir;
    public Text textObj;
    public byte status = 0;
    public int xoff = 4;
    public int yoff = 0;
    public boolean over = false;
    public boolean pressed = false;
    public boolean clicked = false;
    public boolean staticWidth = false;
    public boolean clickable = true;
    
    @Override
    public void initVars(){
        super.initVars();
        layer = Defines.LAYER_BUTTON;
        basicIconName = "buttons\\button\\";
    }
    @Override
    public void init(int nx,int ny){
        textObj = (Text)createObject(new Text(),nx,ny,0);
        textObj.updateColor(Color.white);
    }
    
    @Override
    public void onRemove(){
        textObj.remove();
        updateIcon();
    }
    
    public void setTxt(String txt){
        textObj.updateText(txt); 
        updateIcon();
    }
    
    public void setTxtColor(Color col){
        textObj.updateColor(col);
        updateIcon();
    }
    
    public void setTxtSize(int sz){
        textObj.updateSize(sz);
        updateIcon();
    }
    
    @Override
    public void onPress(){
        if(clickable){
        status = 2;
        pressed = true;
        }
        updateIcon(true);
    }
    @Override
    public void onClick(){
         if(clickable){
        clicked = true;}
        /*Explosion exp = (Explosion) createObject(new Explosion(),400-48,300-48);
        exp.detonate();*/
    }
    @Override
    public void onOver(){
         if(clickable){
        status = 1;
        over = true;
         }
        updateIcon(true);
    }
    @Override
    public void onExit(){
        status = 0;
        updateIcon(true);
    }   
    
    @Override
    public boolean update_icon(boolean reload){
        boolean succ = true;
        int bwidth = 248;
        int l_width = loadImage(iconName+"_l").getImage(status).getWidth(null);
        int r_width = loadImage(iconName+"_r").getImage(status).getWidth(null);
        int c_width = loadImage(iconName+"_c").getImage(status).getWidth(null);
        int hgth = loadImage(iconName+"_c").getImage(status).getHeight(null);
        if(!Main.isNull(textObj)){
            bwidth = textObj.icon.getWidth()+c_width;
        }
        BufferedImage img = new BufferedImage(bwidth+l_width, hgth, BufferedImage.TYPE_INT_ARGB);
        Graphics gr = img.getGraphics();
        gr.drawImage(copyImage((BufferedImage) loadImage(iconName+"_l").getImage(status)), 0, 0, null);
        for(int i = l_width;i<bwidth;i+=c_width){
            gr.drawImage(copyImage((BufferedImage) loadImage(iconName+"_c").getImage(status)), i, 0, null);
        }
        gr.drawImage(copyImage((BufferedImage) loadImage(iconName+"_r").getImage(status)), bwidth, 0, null);
        width = img.getWidth();
        height = img.getHeight();
        xoff = loadImage(iconName+"_l").getImage(status).getWidth(null);
        yoff = (height-textObj.icon.getHeight())/2;
        icon = img;
        return succ;
    }
    
    @Override
    public void process(){
        textObj.isVisible = isVisible;
        textObj.move(x+xoff, y+yoff);
        resetBools();
        super.process();
    }
    public void resetBools(){
        over = false;
        pressed = false;
        clicked = false;
    }
}