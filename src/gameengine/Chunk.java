/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Camper2012
 */
public class Chunk {
    public ArrayList<GameObject> contents = new ArrayList();
    public int x = 0;
    public int y = 0;
    
    public boolean isOnScreen(){
        boolean is = false;
        Rectangle r1 = new Rectangle(x,y,Defines.CHUNK_SIZE,Defines.CHUNK_SIZE);
        Rectangle r2 = new Rectangle(-Main.window.master.objShiftX,-Main.window.master.objShiftY,Defines.WIDTH,Defines.HEIGHT);
        if(r1.intersects(r2)){
            is = true;
        }
        return is;
    }
    
    public int getDistance(int nx, int ny){
        return (int)new Point(x+Defines.CHUNK_SIZE/2,y+Defines.CHUNK_SIZE/2).distance(nx, ny);
    }
    
    public int getSquareDistance(int nx, int ny){
        return Math.max(Math.abs((x+Defines.CHUNK_SIZE/2)-nx),Math.abs((y+Defines.CHUNK_SIZE/2)-ny));
    }
}
