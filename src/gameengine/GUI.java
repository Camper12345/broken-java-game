/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import gameengine.objects.*;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class GUI extends StaticObject{
    public boolean show = true;
    public ArrayList<StaticObject> elements = new ArrayList();
    
    
    @Override
    public void preInit(int nx, int ny, int nz){
        isVisible = false;
        basicIconName = "";
        layer = Defines.LAYER_BUTTON-0.1f;
        super.preInit(nx, ny, nz);
        Main.window.guis.add(this);
    }
    
    public void toggleShowing(boolean tgl){
        show = false;
    }
    
    public boolean isMouseOnElement(){
        boolean is = false;
        for(Object el : elements.toArray()){
            
            if(el instanceof GUI){
                GUI pgui = (GUI) el;
                if(pgui.isMouseOnElement() && pgui.show && pgui.isVisible){
                    is = true;
                    break;
                }
            }else{
                StaticObject sobj = (StaticObject) el;
                if(sobj.checkMouseCollide() && sobj.isVisible){
                    is = true;
                    break;
                }
            }
        }
        return is;
    }
    
    public StaticObject addElement(StaticObject st, int nx, int ny){
        elements.add(st);
        return (StaticObject) createObject(st,nx,ny,0);
    }
    
    public void delElement(StaticObject st){
        if(elements.contains(st)){
            elements.remove(st);
            st.remove();
        }
    }
    
    @Override
    public void onRemove(){
        for(Object gobj : elements.toArray()){
            StaticObject obj = (StaticObject) gobj;
            obj.remove();
        }
        elements.clear();
        Main.window.guis.remove(this);
    }
    
    public void updateShowing(boolean sh){
        show = sh;
        for(Object gobj : elements.toArray()){
            StaticObject sobj = (StaticObject) gobj;
            if(sobj instanceof GUI){
                GUI gui = (GUI) sobj;
                gui.isVisible = sh;
                gui.updateShowing(sh);
            }else{
                sobj.isVisible = sh;
            }
        }
    }
    @Override
    public void onUpdate(){
    }
    
}
