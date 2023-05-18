/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.objects.light.Lamp;

/**
 *
 * @author Camper2012
 */
public class PJLampTrigger extends Lamp{
    public boolean isBroken = false;

    @Override
    public void crash() {
        super.crash();
        isBroken = true;
    }
    
    
}
