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
import gameengine.objects.mobs.PrisonGuard;

/**
 *
 * @author Camper2012
 */
public class lvl0GuardSpawner extends MobSpawner{
    @Override
    public void initVars(){
        super.initVars();
        spawnmob = new PrisonGuard();
    }
    
    @Override
    public Mob spawn(){
        if(spawned<=0){  
            Mob smob = dospawn();
            smob.alert_level = Mob.ATTACK_ALEVEL;
            Player pl = Main.window.master.playerObj;
            smob.aiTarget = pl;
            smob.startMoveTo(pl.x, pl.y, pl.z, pl);
            return smob;
        }
        return null;
    }
}
