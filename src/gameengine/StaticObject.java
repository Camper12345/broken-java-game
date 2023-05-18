/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

/**
 *
 * @author Camper2012
 */
public class StaticObject extends GameObject{
    @Override
    public void initVars(){
        staticPos = true;
        isDensity = false;
        isMovable = false;
        removable = false;
    }

    @Override
    public boolean in_disp_zone(){
        return Main.in_range(x, y, 0,0,Defines.WIDTH,Defines.HEIGHT);
    }
    
    @Override
    public void process(){
        z = Main.window.master.currentZ;
        scrX = x;
        scrY = y;
        super.process();
    }
}
