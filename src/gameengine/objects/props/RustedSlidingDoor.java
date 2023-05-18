/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

/**
 *
 * @author Camper2012
 */
public class RustedSlidingDoor extends SlidingDoor{
    @Override
    public void initVars(){
        super.initVars();
        opacity = true;
        isOpacity = true;
        thickness = 4;
        basicIconName = "obj\\doors\\rhslidingdoor";
    }
}
