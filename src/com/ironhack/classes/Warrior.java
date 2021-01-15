package com.ironhack.classes;

import com.ironhack.interfaces.Atacker;

public class Warrior extends Character implements Atacker {
    private int stamina;
    private int strength;

    public Warrior(int id, String name, int hp, boolean isAlive, int stamina, int strength) {
        super(id, name, hp, isAlive);
        setStamina(stamina);
        setStrength(strength);
    }

    public double atack(){
        if(this.stamina>=5){
            this.stamina-=5;
            return strength;
        }else{
            this.stamina++;
            return Math.floor(strength/2);
        }

    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
