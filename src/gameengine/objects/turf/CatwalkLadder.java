/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.turf;

import gameengine.Main;
import gameengine.objects.Ladder;

/**
 *
 * @author Camper2012
 */
public class CatwalkLadder extends Ladder{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "turf\\floor\\metal\\catwalk_ladder";
        stepSound = "player\\step\\metalgrate";
    }
}
