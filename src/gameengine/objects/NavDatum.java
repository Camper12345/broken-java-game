/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameengine.objects;

import gameengine.Chunk;
import gameengine.Defines;
import gameengine.GameObject;
import gameengine.Main;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 *
 * @author Camper2012
 */
public class NavDatum {
    public int x;
    public int y;
    public int z;
    public NavDatum parent;
    public float g;
    public float h;
    
    public NavDatum(int cx, int cy, int cz){
        x = cx;
        y = cy;
        z = cz;
    }
    
    public int getDistance(int nx, int ny, int nz){
        if(nz == z){
            return (int)new Point(x,y).distance(nx, ny);
        }else{
            return Math.abs(z-nz);
        }
    }
    
    @Override
    public boolean equals(Object nd){
        return (nd instanceof NavDatum)&&(x==((NavDatum )nd).x && y==((NavDatum )nd).y && z==((NavDatum )nd).z);
    }
    
}
