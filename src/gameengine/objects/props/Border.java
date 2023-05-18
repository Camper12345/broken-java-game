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
public class Border extends Solid{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\decoration\\border";
        isDensity = false;
        layer = Defines.LAYER_TURF+0.1f;
    }
    
}
