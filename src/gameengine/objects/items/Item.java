/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.items;

import gameengine.Defines;
import gameengine.DynamicObject;

/**
 *
 * @author Camper2012
 */
public class Item extends DynamicObject{
    @Override
    public void initVars(){
        isDensity = false;
        layer = Defines.LAYER_ITEM;
    }    
}
