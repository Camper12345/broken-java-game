/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.items;

import gameengine.Main;

/**
 *
 * @author Camper2012
 */
public class GlassShard extends Item{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = Main.pickStringVariant("obj\\decoration\\derbis\\glassshard", 2);
    }    
    
    @Override
    public void init(int nx, int ny){
        direction = (float) (Math.random()*360);
    }
}
