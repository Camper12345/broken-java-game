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
public class Wall extends Turf{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "turf\\wall";
        stepSound = "";
        layer = Defines.LAYER_WALL;
        isDensity = true;
        isMovable = false;
        isOpacity = false;
    }
    
    @Override
    public void onUpdate(){
        //updateIcon();
    }
}
