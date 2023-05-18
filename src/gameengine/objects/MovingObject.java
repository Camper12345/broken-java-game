/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.DynamicObject;
import gameengine.GameObject;
import gameengine.Main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Camper2012
 */
public class MovingObject extends Marker{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "marker\\mover";
    }
    
    
    @Override
    public BufferedImage specialUpdateIconDebug(BufferedImage img){
        BufferedImage nimg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics gr = nimg.getGraphics();
        gr.setColor(Color.green);
        gr.drawString("ID "+id, scrX, scrY+10);
        return nimg;
    }
    
    public void move_objs(int nx, int ny){
        for(Object o : getCollideList(x, y, z, true, false, false)){
            GameObject go = (GameObject) o;
            if(go instanceof DynamicObject && go.x == x && go.y == y && go.z == z){
                go.move(nx,ny);
            }
        }
        move(nx,ny);
    }
}
