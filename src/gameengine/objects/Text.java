/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;
import gameengine.Defines;
import gameengine.Main;
import gameengine.StaticObject;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class Text extends StaticObject {
    public String text = "TEXT";
    public Color color = Color.black;
    public Font font;
    int size = 24;
    
    @Override
    public void init(int nx, int ny){
        layer = Defines.LAYER_TEXT;
        font = new Font("Arial Black",Font.BOLD,size);
        icon = new BufferedImage(text.length()*font.getSize()+1,font.getSize()*6+1,BufferedImage.TYPE_INT_ARGB);
    }   
    
    @Override
    public boolean update_icon(boolean reload){
        boolean succ = false;
        BufferedImage img = new BufferedImage((int) font.getStringBounds(text, new FontRenderContext(null,false,false)).getWidth(),
                (int) font.getStringBounds(text, new FontRenderContext(null,true,true)).getHeight(),BufferedImage.TYPE_INT_ARGB);
        if(!Main.isNull(img)){
        Graphics gr = img.getGraphics();
        gr.setColor(color);
        gr.setFont(font);
        gr.drawString(text, 0, font.getSize());
        width = img.getWidth(null);
        height = img.getHeight(null);
        icon = img;
        succ = true;}
        return succ;
    }
    
    public void updateColor(Color col){
        color = col;
        this.updateIcon();
    }
    public void updateSize(int sz){
        size = sz;
        font = new Font("Arial Black",Font.BOLD,size);
        this.updateIcon();
    }
    public void updateText(String txt){
        text = txt;
        this.updateIcon();
    }
    
    @Override
    public void onUpdate(){
        updateIcon();
    }
}
