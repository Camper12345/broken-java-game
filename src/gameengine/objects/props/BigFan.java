/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.Defines;
import gameengine.DynamicObject;

/**
 *
 * @author Camper2012
 */
public class BigFan extends DynamicObject{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "obj\\decoration\\big_fan";
        staticSize = true;
        iconCenter = true;
        width = 64;
        height = 64;
        layer = Defines.LAYER_TURF+0.1f;
    }
}
