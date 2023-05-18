/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.turf;

import gameengine.*;

/**
 *
 * @author Camper2012
 */
public class Smoothstone extends Turf{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = Main.pickStringVariant("turf\\floor\\smoothstone\\smoothstone", 2);
        stepSound = "player\\step\\concrete";
    }
}
