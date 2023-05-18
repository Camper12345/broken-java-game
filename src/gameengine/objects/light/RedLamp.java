/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.light;

import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class RedLamp extends Lamp{
    @Override
    public void initVars(){
        super.initVars();
        DefColor = new Color(255,5,5);
        basicIconName = "obj\\light\\redlamp";
        DefStrength = 128;
    }
    
    @Override
    public void crash(){
        
    }
}
