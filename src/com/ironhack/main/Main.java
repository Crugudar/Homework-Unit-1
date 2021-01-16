package com.ironhack.main;


import com.ironhack.classes.Character;
import com.ironhack.classes.Warrior;
import com.ironhack.classes.Wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Object Character;

    public static void main(String[] args) {

        Scanner scanner= new Scanner(System.in);
        System.out.println("Welcome to the game!!! \n Hom many Characters do you want in your army?\n You can choose a number between 3 and 9");

        int numberOfCharacters = scanner.nextInt();

        while(numberOfCharacters<1||numberOfCharacters>9){
            System.out.println("Type a valid number");
            numberOfCharacters = scanner.nextInt();
        }

        ArrayList<Character> userArmy=new ArrayList<Character>(numberOfCharacters);


        for(int i=0; i<numberOfCharacters; i++){

            System.out.println("What do you want to create?\n Warrior=1 & Wizard=2");

            int warriorOrWizard = scanner.nextInt();


            while(warriorOrWizard!=1&&warriorOrWizard!=2){
                System.out.println("Type a valid number");
                warriorOrWizard = scanner.nextInt();

            }

            if(warriorOrWizard==1){
                System.out.println("Lets create a warrior");
                userArmy.add( createAWarrior(scanner, i));
            }else if(warriorOrWizard==2){
                System.out.println("Lets create a wizard");
                userArmy.add( createAWizard(scanner, i));
            }
        }
        scanner.nextLine();

        ArrayList<Character>enemyArmy=createRandomArmy(numberOfCharacters);

        while(enemyArmy.size()>0 && userArmy.size()>0){

            Character enemyCharacter = enemyArmy.get((int) Math.floor(Math.random() * enemyArmy.size())); // Molaría imprimir estadísticas

            System.out.println("YOUR OPPONENT IS :");
            enemyCharacter.getStats();

            String sure="n";

            int selection;

            do{
                System.out.println("Choose one of YOUR characters to fight!!!!!!!\n You have to choose a number between 0 and "+ (userArmy.size()-1));
                selection = scanner.nextInt();
                scanner.nextLine();
                userArmy.get(selection).getStats();
                System.out.println("Are you sure Y/N");
                sure= scanner.nextLine();

            } while (!sure.equals("y"));

            System.out.println("THE BATTLE BEGINS!!!!!!");
            Character character =userArmy.get(selection);
            while (character.isAlive() && enemyCharacter.isAlive()){

                double enemyDamage=enemyCharacter.attack();
                double ourDamage=character.attack();

                character.receiveDamage(enemyDamage);
                enemyCharacter.receiveDamage(ourDamage);
                System.out.println("Press ENTER to continue the battle");
                scanner.nextLine();
            }

            if(!character.isAlive() && !enemyCharacter.isAlive()){
                userArmy.remove(character);
                enemyArmy.remove(enemyCharacter);
            }else if(!character.isAlive()){
                userArmy.remove(character);
            }else{
                enemyArmy.remove(enemyCharacter);
            }
        }


        System.out.println("BATTLE FINISHED!!");

    }

    public static Warrior createAWarrior(Scanner scanner, int id){
        scanner.nextLine();

        System.out.println("Choose a name for your warrior");

        String name= scanner.nextLine();

        System.out.println("Choose his Health points between 100 and 200");

        int hp = scanner.nextInt();
        while(hp<100||hp>200){
            System.out.println("Type a valid hp");
            hp = scanner.nextInt();
        }

        System.out.println("Choose his stamina between 10 and 50");

        int stamina = scanner.nextInt();
        while(stamina<10||stamina>50){
            System.out.println("Type a valid stamina");
            stamina = scanner.nextInt();
        }

        System.out.println("Choose his strength between 1 and 10");

        int strength = scanner.nextInt();
        while(strength<1||strength>10){
            System.out.println("Type a valid stamina");
            strength = scanner.nextInt();
        }


        return  new Warrior(id, name, hp, true, stamina, strength);


    }

    public static Wizard createAWizard(Scanner scanner, int id){
        scanner.nextLine();
        System.out.println("Choose a name for your wizard");
        String name= scanner.nextLine();

        System.out.println("Choose his Health points between 50 and 100");

        int hp = scanner.nextInt();
        while(hp<50||hp>100){
            System.out.println("Type a valid hp");
            hp = scanner.nextInt();
        }

        System.out.println("Choose his mana between 10 and 50");

        int mana = scanner.nextInt();
        while(mana<10||mana>50){
            System.out.println("Type a valid stamina");
            mana = scanner.nextInt();
        }

        System.out.println("Choose his intelligence between 1 and 50");

        int intelligence = scanner.nextInt();
        while(intelligence<1||intelligence>50){
            System.out.println("Type a valid stamina");
            intelligence = scanner.nextInt();
        }


        return  new Wizard(id, name, hp, true, mana, intelligence);


    }

    public static ArrayList<Character> createRandomArmy(int numOfCharacters){
        int counter=0; // que coja la length del array indicado por el usuario;

        String[] names= getNames(numOfCharacters);

        ArrayList<Character> army= new ArrayList<Character>(numOfCharacters);

        while (counter<numOfCharacters){
            int numero = (int) Math.round(Math.random());

            if(numero==1){
                //Character warrior=createRandomWarrior(counter);

                int hp= (int) Math.round(Math.random()*100)+100;
                int stamina= (int) Math.round(Math.random()*40)+10;
                int strength= (int) Math.round(Math.random()*9)+1;
                int id=counter;
                String name=names[counter];

                Warrior warrior = new Warrior(id, name,hp,true,stamina,strength);
                army.add(warrior);
                //System.out.println("Warrior created "+ warrior.getId()+" "+warrior.getName()+" "+warrior.getHp()+" "+warrior.isAlive()+" "+warrior.getStamina()+" "+ warrior.getStrength());
            }else{

                int hp= (int) Math.round(Math.random()*50)+50;
                int mana= (int) Math.round(Math.random()*40)+10;
                int intelligence= (int) Math.round(Math.random()*49)+1;
                int id=counter;
                String name=names[counter];

                Wizard wizard = new Wizard(id, name,hp,true,mana,intelligence);
                army.add(wizard);
                //System.out.println("wizard created "+ wizard.getId()+" "+wizard.getName()+" "+wizard.getHp()+" "+wizard.isAlive()+" "+wizard.getMana()+" "+ wizard.getIntelligence());
            }

            counter++;
        }
        return army;
    }

    public static String[] getNames(int numOfNames){
        String[] names={"The Hammer","The Vengeful","The Tempest","The Enigma","The Grim","Strong Grimace","Moltenblood","Gore Sorrow","Gorehead","Firegrim"};
        String[] selectedNames=new String[numOfNames];

        for(int i=0; i<numOfNames; i++){
            int rnd = (int)Math.floor(Math.random()*names.length);
            if( Arrays.asList(selectedNames).contains(names[rnd])){
                selectedNames[i]=names[rnd]+" Jr.";
                names[rnd]=selectedNames[i];

            }else{
                selectedNames[i]=names[rnd];
            }

        }


        return selectedNames;
    }


}
