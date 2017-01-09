/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author mac
 */
public class CreepControl extends AbstractControl{
   
    GamePlayAppState state;
    
    public CreepControl(GamePlayAppState state){
        this.state = state;
    }//Constructor
    
    public int getIndex(){
        return spatial.getUserData("index");
    }//getIndex
    
    public int getHealth(){
        return spatial.getUserData("health");
    }//getHealth
    
    public void setIndex(int index){
        this.spatial.setUserData("index", index);
    }//setIndex
    
    public void setHealth(int health){
        this.spatial.setUserData("health", health);
    }//setIndex
    
    public void hit(Charges charge){
        this.spatial.setUserData("health", 
                this.getHealth()-charge.getDamage());
        System.out.println("Creep " + this.getIndex() + " hit, "+
                "health " + this.getHealth());
        if(this.getHealth() < 1){
            state.creepNode.detachChild(spatial);
            System.out.println("Creep " + this.getIndex() + " detached" );
        }//if
    }//hit

    @Override
    protected void controlUpdate(float tpf) {
        //if creep is not dead then move creep towards player base (0 on the z axis)
        if(this.getHealth() > 0){
           spatial.setLocalTranslation(spatial.getLocalTranslation().add(new Vector3f(0, 0, tpf)));
            //if creep has reached 0 z coord remove 1 from player health and add 10 to player budget
            if(spatial.getLocalTranslation().getZ() >=  0.0f){
                state.setHealth(state.getHealth()-20);
                state.setBudget(state.getBudget()+10);
                state.creepNode.detachChild(spatial);
            }//if
        }//if 
    }//controlUpdate

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
}//class
