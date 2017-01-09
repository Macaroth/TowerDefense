/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author mac
 */
public class Charges implements Comparable<Charges>{
    private int damage;
    private int remainingBullets;
    private int price;
    
    public Charges(){
        this.damage = 25;
        this.remainingBullets = 4;
        this.price = 20;
    }//Constructor

    /**
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @param damage the damage to set
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * @return the remainingBullets
     */
    public int getRemainingBullets() {
        return remainingBullets;
    }

    /**
     * @param remainingBullets the remainingBullets to set
     */
    public void setRemainingBullets(int remainingBullets) {
        this.remainingBullets = remainingBullets;
    }

    public int compareTo(Charges that) {
        if(this.remainingBullets > that.remainingBullets) return 1;
        else if(this.remainingBullets < that.remainingBullets) return -1;
        else return 0;
    }//CompareTo
    
    public void fireOne(){
        this.remainingBullets = this.remainingBullets -1;
    }//fire

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

}//class
