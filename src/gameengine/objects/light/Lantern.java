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
public class Lantern extends Lamp{
    @Override
    public void initVars(){
        super.initVars();
        DefColor = new Color(230,240,255);
        basicIconName = "obj\\light\\lantern";
        DefRange = 220;
    }
    
    @Override
    public void crash(){
        
    }
}
