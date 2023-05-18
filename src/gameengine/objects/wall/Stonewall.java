/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.wall;

import gameengine.*;

/**
 *
 * @author Camper2012
 */
public class Stonewall extends Wall{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = Main.pickStringVariant("turf\\wall\\stonewall\\stonewall", 2);
    }
}
