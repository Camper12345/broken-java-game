/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.Defines;
import gameengine.objects.Solid;
import java.awt.Rectangle;

/**
 *
 * @author Camper2012
 */
public class PoliceCar extends Solid{
    @Override
    public void initVars() {
        super.initVars(); 
        basicIconName = "obj\\decoration\\policecar";
        pass_flags |= Defines.PASS_PROJECTILE_COLLIDE;
        iconCenter = true;
    }
    
    @Override
    public Rectangle getCollideShape() {
        Rectangle cr = new Rectangle(x,y,Defines.ICON_SIZE,Defines.ICON_SIZE);
        if(direction == 0 || direction == 360 || direction == 180){
            cr = new Rectangle(x,y+height/4,width-1,height/2-1);
        }
        if(direction == 270 || direction == 90){
            cr = new Rectangle(x+width/4,y,width/2-1,height-1);
        }
        return cr;
    }
}
