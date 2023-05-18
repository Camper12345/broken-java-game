/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.objects.Door;

/**
 *
 * @author Camper2012
 */
public class SlidingDoor extends Door{
    @Override
    public void initVars(){
        super.initVars();
        thickness = 6;
        basicIconName = "obj\\doors\\hslidingdoor";
    }
}
