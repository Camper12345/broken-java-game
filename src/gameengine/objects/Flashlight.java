/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.LightSource;
import java.awt.Color;

/**
 *
 * @author Camper2012
 */
public class Flashlight extends LightSource{
    @Override
    public void initVars(){
        super.initVars();
        setRange(180);
        size = 50;
        setColor(new Color(120,200,255));
        basicIconName = "";
    }
    
}
