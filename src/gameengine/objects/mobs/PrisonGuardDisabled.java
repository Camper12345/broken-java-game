/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.mobs;

/**
 *
 * @author Camper2012
 */
public class PrisonGuardDisabled extends PrisonGuard{

    @Override
    public void initVars() {
        super.initVars();
        thinking = false;
    }
    
}
