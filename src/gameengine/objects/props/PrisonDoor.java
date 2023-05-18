/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.Defines;
import gameengine.objects.Mob;
import gameengine.objects.mobs.Player;
import gameengine.objects.mobs.PrisonGuard;

/**
 *
 * @author Camper2012
 */
public class PrisonDoor extends RustedSlidingDoor{
    @Override
    public void initVars(){
        super.initVars();
        
    }
    @Override
    public boolean canOpen(Mob m){
        return (m instanceof PrisonGuard) || 
                (m instanceof Player && Defines.checkFlag(((Player)m).scenarioFlags,Player.SFL0_HAS_PRISON_KEY));
    }
}
