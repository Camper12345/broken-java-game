/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.objects.Fence;

/**
 *
 * @author Camper2012
 */
public class FlippedTable extends Barrier{
    @Override
    public void initVars() {
        super.initVars();
        fullHeight = true;
        basicIconName = "obj\\decoration\\table\\table_flipped";
    }
}
