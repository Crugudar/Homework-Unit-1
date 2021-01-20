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
    @Override
    public void getStats() {
        System.out.println(" Name: "+ getName()+"\n Hp: "+getHp()+"\n Mana: "+getMana()+"\n Intelligence: "+getIntelligence()+"\n");
    }

    @Override
    public double attack(){
        if(this.mana>=5){
            System.out.println(getName()+" attacks with Fireball and deals "+intelligence+" damage!!");
            this.mana-=5;
            return intelligence;
        }else{
            System.out.println(getName()+" attacks with Staff.. (weak! dealt 2 damage)");
            this.mana++;
            return 2;
        }

    }


    @Override
    public void receiveDamage(double damage) {
        setHp(this.hp-=damage);
        if (getHp()<=0){
            System.out.println(getName()+" is Dead!!");
            setAlive(false);
        }else{
            if(damage>35){
                System.out.println("Wow that was critical!!!!");

            }else if(damage<=35&&damage>15){
                System.out.println("Good attack");
            }else{
                System.out.println("booooooo!!!");
            }
            System.out.println(getName()+" has "+getHp()+" health points left");
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
