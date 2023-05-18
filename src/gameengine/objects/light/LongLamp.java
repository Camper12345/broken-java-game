/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects.light;

/**
 *
 * @author Camper2012
 */
public class LongLamp extends Lamp{
    @Override
    public void initVars(){
        super.initVars();
        DefRange = 200;
        basicIconName = "obj\\light\\longlamp";
    }
}
