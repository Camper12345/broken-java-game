/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.light;

import gameengine.LightSource;

/**
 *
 * @author Camper2012
 */
public class SunLight extends LightSource{
    @Override
    public void initVars(){
        super.initVars();
        brightness = 255;
        range = 100;
        SETTING_RANGE = false;
        isInvs = true;
        basicIconName = "marker\\light";
        saveable = true;
        staticSize = false;
    }
    
}
