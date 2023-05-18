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
public class AllowedZone extends Marker{
    @Override
    public void initVars(){
        super.initVars();
        basicIconName = "marker\\allowedzone";
    }
    
    @Override
    public boolean equals(Object obj){
        return obj instanceof AllowedZone;
    }
}
