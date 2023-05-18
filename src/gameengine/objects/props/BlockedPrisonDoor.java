/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.props;

import gameengine.objects.Mob;

/**
 *
 * @author Camper2012
 */
public class BlockedPrisonDoor extends PrisonDoor{
    @Override
    public boolean canOpen(Mob m){
        return false;
    }
}
