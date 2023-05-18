/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.scenario;

import gameengine.objects.mobs.PrisonGuard;

/**
 *
 * @author Camper2012
 */
public class lvl0KeyGuard extends PrisonGuard{

    @Override
    public void initVars() {
        super.initVars();
        sight_back_dist = 0;
    }
    
    @Override
    public void specialDrop(){
        drop(new lvl0PrisonKey());
    }
}
