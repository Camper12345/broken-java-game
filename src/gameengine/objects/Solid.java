/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.DynamicObject;

/**
 *
 * @author Camper2012
 */
public class Solid extends DynamicObject{
    @Override
    public void initVars(){
        super.initVars();
        isDensity = true;
        isOpacity = true;
        collideType = 0;
    }
}
