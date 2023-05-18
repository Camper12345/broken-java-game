/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.objects.props.PrisonDoor;

/**
 *
 * @author Camper2012
 */
public class PrisonMainternanceDoor extends PrisonDoor{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\doors\\rmainternancedoor";
        isOpacity = false;
        opacity = false;
    }
}
