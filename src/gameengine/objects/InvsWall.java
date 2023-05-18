/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

/**
 *
 * @author Camper2012
 */
public class InvsWall extends Marker{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\invswall";
        isDensity = true;
        collideType = 0;
    }
}
