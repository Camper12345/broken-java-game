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
public class ConcreteLadder extends Ladder{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "turf\\floor\\concrete\\concrete_ladder";
        stepSound = "player\\step\\concrete";
    }
}
