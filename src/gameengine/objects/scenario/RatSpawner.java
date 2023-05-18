/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.Main;
import gameengine.objects.Mob;
import gameengine.objects.MobSpawner;
import gameengine.objects.mobs.Player;
import gameengine.objects.mobs.Rat;

/**
 *
 * @author Camper2012
 */
public class RatSpawner extends MobSpawner{

    @Override
    public void initVars() {
        super.initVars();
        spawnmob = new Rat();
    }
    
    @Override
    public Mob spawn(){
        if(spawned<=0){  
            Mob smob = dospawn();
            return smob;
        }
        return null;
    }
}
