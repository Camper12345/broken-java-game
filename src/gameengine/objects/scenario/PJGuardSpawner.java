/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.LightSource;
import gameengine.Main;
import gameengine.objects.Mob;
import gameengine.objects.MobSpawner;
import gameengine.objects.mobs.Player;
import gameengine.objects.mobs.Police;
import gameengine.objects.mobs.PrisonGuard;

/**
 *
 * @author Camper2012
 */
public class PJGuardSpawner extends MobSpawner{
    @Override
    public void initVars(){
        super.initVars();
        spawnmob = new Police();
    }
    
    @Override
    public Mob spawn(){
        if(spawned<=0){
            boolean br = false;
            for(LightSource ls : Main.window.lights){
                if(ls instanceof PJLampTrigger){
                    PJLampTrigger lt = (PJLampTrigger) ls;
                    if(lt.isBroken){
                        br = true;
                        break;
                    }
                }
            }
            if(br){
            Mob smob = dospawn();
            smob.patrolPathIndex = 5;
            return smob;
            }
        }
        return null;
    }
}
