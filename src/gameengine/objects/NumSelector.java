/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.*;

/**
 *
 * @author Camper2012
 */
public class NumSelector extends Button{
    public int min = 0;
    public int max = 100;
    public int value = 0;
    public int incstep = 1;
    public int decstep = 1;
    public boolean cycle = false;
    public String prefix = "[";
    public String suffix = "]";
    public boolean switched = false;
    
    @Override
    public void onUpdate(){
        setTxt(prefix+value+suffix);
    }
    
    public void setValue(int val){
        if(!cycle){
        if(Main.in_range(val,min,max)){
            value = val;
            setTxt(prefix+value+suffix);
        }
        }else{
         if(Main.in_range(val, min, max)){
            value = val;}
        else if(val>max){
            value = (min+(val%max))-decstep;
        }
        else if(val<min){
            value = ((max)-(val%max))-incstep;
        }
        }
    }
    
    @Override
    public void resetBools(){
        switched = false;
        super.resetBools();
    }
    
    @Override
    public void onOver(){
        super.onOver();
        if(isVisible){
        if(getMWheel()>0){
            setValue(value+incstep);
            switched = true;
        }else if(getMWheel()<0){
            setValue(value-decstep);
            switched = true;
        }
        }
    }
    
}
