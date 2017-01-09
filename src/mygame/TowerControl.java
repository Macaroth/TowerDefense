/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Line;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author mac
 */
public class TowerControl extends AbstractControl {

    GamePlayAppState state;
    private ArrayList<Charges> myCharges = new ArrayList<Charges>();
    private Node beamNode;
    private AssetManager assetManager;
    private long firedLast = System.currentTimeMillis();
    
    public TowerControl(GamePlayAppState state){
        this.state = state;
        beamNode = state.beamNode;
        assetManager = state.getAssetManager();
        Charges charge = new Charges();
        myCharges.add(charge);               
    }//Constructor
    
    @Override
    protected void controlUpdate(float tpf) {
        manageCharges();
        //if tower has ammo
        if(!myCharges.isEmpty()){
            //Checks each creeps distance from tower. If < 10 WU then
            //adds creeps control to reachable
            ArrayList<CreepControl> reachable = new ArrayList<CreepControl>();
            List<Spatial> creeps = state.creepNode.getChildren();
            for(int i = 0; i < creeps.size(); i++){
                if(creeps.get(i).getLocalTranslation().distance(spatial.getLocalTranslation()) < 10){
                    reachable.add(creeps.get(i).getControl(CreepControl.class));
                }//if
            }//for
            //Checks to see if creeps are in range and has ammo in the charge 
            //and fires on them, managing charges as well.
            //also checks to see if its fired recently
            if(System.currentTimeMillis() - firedLast >= 500){
                if(reachable.size() > 0){
                    for(int i = 0; i < reachable.size(); i++){
                        if(myCharges.get(0).getRemainingBullets() > 0){
                            fire(reachable.get(i));
                            firedLast = System.currentTimeMillis();
                        }//if
                    }//foreach
                }//if
            }//if
        }//if        
    }//controlUpdate

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    public int  getIndex(){
        return spatial.getUserData("index");
    }//getIndex
    
    public void setIndex(int index){
        spatial.setUserData("index", index);
    }//setIndex
    
    public float getHeight(){
        return spatial.getUserData("height");
    }//getHeight
    
    public void setHeight(float height){
        spatial.setUserData("height", height);
    }//setHeight
    
    public int getChargesNum(){
        return spatial.getUserData("chargesNum");
    }//getChargesNum
    
    public void setChargesNum(int chargesNum){
        spatial.setUserData("chargesNum", chargesNum);
    }//setChargesNum
    
    public void addCharge(){
        myCharges.add(new Charges());
        state.setBudget(state.getBudget() - getRefillPrice());
    }//addCharge
    
    public void manageCharges(){
        if(myCharges.size() > 0 && myCharges.get(0).getRemainingBullets() < 1){
            myCharges.remove(0);
            Collections.sort(myCharges);
        }//if
    }//manageCharges
    
    public void createFiringLine(CreepControl creepControl){
        Line firingLine = new Line(this.getSpatial().getLocalTranslation().add(
                    new Vector3f(0, this.getHeight(), 0))
                , creepControl.getSpatial().getLocalTranslation());
        Geometry lineGeom = new Geometry("Bullet", firingLine);
        Material orange = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        orange.setColor("Color", ColorRGBA.Blue);
        lineGeom.setMaterial(orange);
        beamNode.attachChild(lineGeom);
    }//createFiringLine
    
    public void fire(CreepControl target){
        myCharges.get(0).fireOne();
         createFiringLine(target);
        System.out.println(
                "Tower " + this.getIndex() + ": Firing, "+ 
                 "Ammo: " + myCharges.get(0).getRemainingBullets());
         target.hit(myCharges.get(0));
    }//fire
    
    public int getRefillPrice(){
        Charges charge = new Charges();
        return charge.getPrice();
    }//getRefillPrice
}//class
