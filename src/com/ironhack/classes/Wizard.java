package com.ironhack.classes;

import com.ironhack.interfaces.Atacker;

public class Wizard extends Character implements Atacker {
    private int mana;
    private int intelligence;

    public Wizard(int id, String name, int hp, boolean isAlive, int mana, int intelligence) {
        super(id, name, hp, isAlive);
        setMana(mana);
        setIntelligence(intelligence);
    }

    public double atack(){
        if(this.mana>=5){
            this.mana-=5;
            return intelligence;
        }else{
            this.mana++;
            return 2;
        }

    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}
