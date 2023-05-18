/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Defines;
import gameengine.DynamicObject;
import gameengine.Main;
import java.awt.Rectangle;

/**
 *
 * @author Camper2012
 */
public class Fence extends DynamicObject{
    @Override
    public void initVars(){
        super.initVars();
        fullHeight = false;
        basicIconName = "obj\\solid\\fence";
        isDensity = true;
        collideType = 0;
        layer = Defines.LAYER_WALL;
    }

    @Override
    public Rectangle getCollideShape() {
        Rectangle cr = new Rectangle(x,y,Defines.ICON_SIZE,Defines.ICON_SIZE);
        if(direction == 0 || direction == 360){
            cr = new Rectangle(x,y,width-1,height-1);
        }
        if(direction == 270){
            cr = new Rectangle(x,y+(Defines.ICON_SIZE-height),height-1,width-1);
        }
        if(direction == 180){
            cr = new Rectangle(x+(Defines.ICON_SIZE-width),y,width-1,height-1);
        }
        if(direction == 90){
            cr = new Rectangle(x,y+height-width,height-1,width-1);
        }
        return cr;
    }
    
    
    
}
