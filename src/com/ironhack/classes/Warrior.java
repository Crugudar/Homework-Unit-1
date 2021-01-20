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

    @Override
    public void getStats() {
        System.out.println(" Name: "+ getName()+"\n Hp: "+getHp()+"\n Stamina: "+getStamina()+"\n Strength: "+getStrength()+"\n");
    }

    @Override
    public double attack(){
        if(this.stamina>=5){
            System.out.println(getName()+" attacks with HEAVY attack!!");
            this.stamina-=5;
            return strength;
        }else{
            System.out.println(getName()+" attacks with weak attack.. (not very effective)");
            this.stamina++;
            return Math.floor(strength/2.0);
        }

    }

    @Override
    public void receiveDamage(double damage) {
        setHp(this.hp-=damage);
        if (getHp()<=0){
            System.out.println(getName()+" is Dead!!");
            setAlive(false);
        }else{
            if(damage>7){
                System.out.println("Wow that was critical!!!!");

            }else if(damage<=7&&damage>4){
                System.out.println("Good attack");
            }else{
                System.out.println("booooooo!!!");
            }
            System.out.println(getName()+" has "+getHp()+" health points left");
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
