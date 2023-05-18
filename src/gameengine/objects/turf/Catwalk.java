/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.turf;

import gameengine.Main;
import gameengine.Turf;

/**
 *
 * @author Camper2012
 */
public class Catwalk extends Turf{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "turf\\floor\\metal\\catwalk";
        stepSound = "player\\step\\metalgrate";
    }
}
