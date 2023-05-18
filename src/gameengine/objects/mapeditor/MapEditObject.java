/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mapeditor;

import gameengine.*;

/**
 *
 * @author Camper2012
 */
public class MapEditObject extends GameObject {
    @Override
    public void init(int nx, int ny){
        can_lighting = false;
        isMovable = false;
        layer = Defines.LAYER_BUTTON-0.2f;
    }
    
    @Override
    public void onUpdate(){
        updateIcon(true);
    }
}
