/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Main;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class ItemSelector extends NumSelector{
    public ArrayList<Object> items = new ArrayList();
    public ArrayList<String> names = new ArrayList();
   
    @Override
    public void init(int nx, int ny){
        super.init(nx, ny);
        cycle = true;
    }
    
    @Override
    public void onUpdate(){
        min = 0;
        max = items.size()-1;
        if(names.size()==items.size()){
            setTxt(prefix+getItemName()+suffix);
        }else{
            setTxt(prefix+getItem().toString()+suffix);
        }
    }
    
    @Override
     public void setValue(int val){
        if(items.size()>1){
        if(!cycle){
        if(Main.in_range(val,min,max)){
            value = val;
            setTxt(prefix+value+suffix);
        }
        }else{
         if(Main.in_range(val, min, max)){
            value = val;}
        else if(val>max){
            value = Math.max(min+val%max-decstep,0);
        }
        else if(val<min){
            value = Math.min((max)-val%max+incstep,max);
        }
        }
        }
    }
    
    public Object getItem(){
        Object obj = new Object();
        if(items.size()-1>=value){
            obj = items.get(value);
        }
        return obj;
    }
    public String getItemName(){
        String nm = "";
        if(names.size()-1>=value){
            nm = names.get(value);
        }
        return nm;
    }
    public void addItem(Object item, String name){
        items.add(item);
        names.add(name);
    }
}
