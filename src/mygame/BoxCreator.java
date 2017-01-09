/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author mac
 */
public abstract class BoxCreator {
    public static Geometry create(AssetManager am,float x, float y, float z, 
                String geomName, int colorNum){
        Box b = new Box(x, y, z);
        Geometry geom = new Geometry(geomName, b);
        Material mat = new Material(am, "Common/MatDefs/Misc/Unshaded.j3md");
        switch(colorNum){
            case 0: mat.setColor("Color", ColorRGBA.Black);
                break;
            case 1: mat.setColor("Color", ColorRGBA.Red);
                break;
            case 2: mat.setColor("Color", ColorRGBA.Orange);
                break;
            case 3: mat.setColor("Color", ColorRGBA.Yellow);
                break;
            case 4: mat.setColor("Color", ColorRGBA.Green);
                break;
            case 5: mat.setColor("Color", ColorRGBA.Blue);
                break;
            default: mat.setColor("Color", ColorRGBA.White);
        }//switch
        geom.setMaterial(mat);
        return geom;
    }//create
}//class
