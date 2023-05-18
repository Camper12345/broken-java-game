/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.objects.AdviceTrigger;

/**
 *
 * @author Camper2012
 */
public class AdvFlashlight extends AdviceTrigger{

    @Override
    public void initVars() {
        super.initVars();
        text = "F - фонарик.";
    }
    
}
