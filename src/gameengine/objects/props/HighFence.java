/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.Defines;
import gameengine.objects.Fence;

/**
 *
 * @author Camper2012
 */
public class HighFence extends Fence{
    @Override
    public void initVars(){
        super.initVars();
        fullHeight = true;
        flags |= Defines.PASS_PROJECTILE_PASS;
        basicIconName = "obj\\solid\\hfence";
    }  
    
}
