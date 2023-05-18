/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.light;

import gameengine.Defines;
import java.awt.Rectangle;

/**
 *
 * @author Camper2012
 */
public class InvsLight extends Lamp{
    @Override
    public void initVars(){
        super.initVars();
        isInvs = true;
        basicIconName = "marker\\light";
    }
    @Override
    public void crash(){
    }
    
    @Override
    public Rectangle getCollideShape(){
        Rectangle cr = new Rectangle(x,y,Defines.ICON_SIZE-1,Defines.ICON_SIZE-1);
        return cr;
    }
}
