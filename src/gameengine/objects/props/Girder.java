/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.Defines;
import gameengine.objects.Solid;

/**
 *
 * @author Camper2012
 */
public class Girder extends Solid{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "obj\\solid\\girder";  
        layer = Defines.LAYER_OBJECT-0.1f;
    }
}
