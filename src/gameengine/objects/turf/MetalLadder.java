/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.turf;

import gameengine.objects.Ladder;

/**
 *
 * @author Camper2012
 */
public class MetalLadder extends Ladder{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "turf\\floor\\metal\\metal_ladder";
        stepSound = "player\\step\\metal";
    }
}
