/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.objects.GameButton;
import gameengine.objects.MobSpawnTrigger;

/**
 *
 * @author Camper2012
 */
public class MobSpawnerButton extends GameButton{

    @Override
    public void initVars() {
        super.initVars();
        trigger = new MobSpawnTrigger();
    }
    
}
