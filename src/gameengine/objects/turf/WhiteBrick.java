/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.turf;

import gameengine.Wall;

/**
 *
 * @author Camper2012
 */
public class WhiteBrick extends Wall{

    @Override
    public void initVars() {
        super.initVars();
        basicIconName = "turf\\wall\\brick\\wbrick";
    }
    
}
