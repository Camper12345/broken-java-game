/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.weapon;

/**
 *
 * @author Camper2012
 */
public class RatBiter extends Fists{

    @Override
    public void initVars() {
        super.initVars();
        ammotype = new RatBite();
        shotdelay = 5;
        accuracy = 15;
    }
    
}
