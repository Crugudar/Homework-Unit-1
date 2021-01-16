package com.ironhack.main;


import com.ironhack.classes.Character;
import com.ironhack.classes.Warrior;
import com.ironhack.classes.Wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Character> userArmy = new ArrayList<>();
        ArrayList<Character> graveyard = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the game!!! \n Do you wish to import your own party? Y/N");
        String csv = scanner.nextLine();

        if (csv.toLowerCase().equals("y")){

            System.out.println("Please, write the name of your .csv file:");
            String fileName = scanner.nextLine();
            userArmy = importArmy(userArmy, fileName);

        }else {
            System.out.println("How many Characters do you want in your army?\n You can choose a number between 1 and 9");
            int numberOfCharacters = scanner.nextInt();
            while(numberOfCharacters<1||numberOfCharacters>9){
                System.out.println("Type a valid number");
                numberOfCharacters = scanner.nextInt();
            }
            scanner.nextLine();

            for(int i=0; i<numberOfCharacters; i++){

                System.out.println("What do you want to create?\n 1. Warrior\n 2. Wizard");
                String warriorOrWizard = scanner.nextLine();
                while(!warriorOrWizard.equals("1") && !warriorOrWizard.equals("2")){
                    System.out.println("Type a valid number");
                    warriorOrWizard = scanner.nextLine();
                }

                if(warriorOrWizard.equals("1")){
                    System.out.println("Let's create a warrior");
                    userArmy.add( createAWarrior(scanner, i));
                }else if(warriorOrWizard.equals("2")){
                    System.out.println("Let's create a wizard");
                    userArmy.add( createAWizard(scanner, i));
                }
            }
        }

        ArrayList<Character>enemyArmy = createRandomArmy(userArmy.size());

        while(enemyArmy.size()>0 && userArmy.size()>0){

            Character enemyCharacter = enemyArmy.get((int) Math.floor(Math.random() * enemyArmy.size()));

            System.out.println("YOUR OPPONENT IS:");
            enemyCharacter.getStats();

            int selection = userArmy.size();
            System.out.println("Choose one of YOUR characters to fight!!!!!!!");

            while (selection < 0 || selection >= userArmy.size()){
                System.out.println("You have to choose a number between 0 and "+ (userArmy.size()-1));
                selection = scanner.nextInt();
                scanner.nextLine();
            }

            String sure;
            do{
                userArmy.get(selection).getStats();
                System.out.println("Are you sure Y/N");
                sure= scanner.nextLine();

            } while (!sure.toLowerCase().equals("y"));

            System.out.println("THE BATTLE BEGINS!!!!!!");
            Character character =userArmy.get(selection);
            while (character.isAlive() && enemyCharacter.isAlive()){

                // Calculate the damage dealt
                double enemyDamage=enemyCharacter.attack();
                double ourDamage=character.attack();
                // Each character receives damage
                character.receiveDamage(enemyDamage);
                enemyCharacter.receiveDamage(ourDamage);

                System.out.println("Press ENTER to continue the battle");
                scanner.nextLine();
            }

            // When a character dies it's moved to the graveyard
            // Three possible outcomes:
            // -- Draw: both die
            if(!character.isAlive() && !enemyCharacter.isAlive()){
                System.out.println(character.getName() + " has been moved to graveyard");
                System.out.println(enemyCharacter.getName() + " has been moved to graveyard");
                graveyard.add(character);
                graveyard.add(enemyCharacter);
                // Remove characters from the ArrayLists
                userArmy.remove(character);
                enemyArmy.remove(enemyCharacter);
            // -- The user's character dies
            }else if(!character.isAlive()){
                System.out.println(character.getName() + " has been moved to graveyard");
                graveyard.add(character);
                // Remove character from the ArrayList
                userArmy.remove(character);
            // -- The enemy dies
            }else{
                System.out.println(enemyCharacter.getName() + " has been moved to graveyard");
                graveyard.add(enemyCharacter);
                // Remove character from the ArrayList
                enemyArmy.remove(enemyCharacter);
            }

            String answer;
            do{
                System.out.println("Next action \n 1: Continue with battle \n 2: Check the graveyard");
                answer = scanner.nextLine();
            }while (!answer.equals("2") && !answer.equals("1"));

            if (answer.equals("2")){
                // Show graveyard
                for (Character i: graveyard) {
                    System.out.println("Here lies " + i.getName());
                }
                System.out.println("Press ENTER to go back to battle");
                scanner.nextLine();
            }
        }

        System.out.println("BATTLE FINISHED!!");

        // Check which party has won
        // -- Draw: all characters have died
        if(enemyArmy.size() == 0 && userArmy.size() == 0){
            System.out.println("WOW!! Both armies have been DESTROYED!!!!!");
        // -- User's party dead
        }else if(userArmy.size() == 0){
            System.out.println("YOU HAVE PERISHED!!!! SHAME TO YOUR FAMILY!");
        // -- Enemy's party dead
        }else{
            System.out.println("YOU HAVE WON!!!! HONOR TO YOUR FAMILY!");
        }

    }

    public static Warrior createAWarrior(Scanner scanner, int id){
        scanner.nextLine();

        System.out.println("Choose a name for your warrior");

        String name= scanner.nextLine();

        System.out.println("Choose his Health Points between 100 and 200");
        int hp = scanner.nextInt();
        hp = correctStat(hp,"hp", "warrior", scanner, name);

        System.out.println("Choose his stamina between 10 and 50");
        int stamina = scanner.nextInt();
        stamina = correctStat(stamina,"stamina", "warrior", scanner, name);

        System.out.println("Choose his strength between 1 and 10");
        int strength = scanner.nextInt();
        strength = correctStat(strength,"strength", "warrior", scanner, name);

        return  new Warrior(id, name, hp, true, stamina, strength);


    }

    public static Wizard createAWizard(Scanner scanner, int id){
        scanner.nextLine();
        System.out.println("Choose a name for your wizard");
        String name= scanner.nextLine();

        System.out.println("Choose his Health points between 50 and 100");
        int hp = scanner.nextInt();
        hp = correctStat(hp,"hp", "wizard", scanner, name);

        System.out.println("Choose his mana between 10 and 50");
        int mana = scanner.nextInt();
        mana = correctStat(mana,"mana", "wizard", scanner, name);

        System.out.println("Choose his intelligence between 1 and 50");
        int intelligence = scanner.nextInt();
        intelligence = correctStat(intelligence,"intelligence", "wizard", scanner, name);

        return  new Wizard(id, name, hp, true, mana, intelligence);


    }

    public static ArrayList<Character> importArmy(ArrayList<Character> userArmy, String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner fileScan = new Scanner(file);
        Scanner statScan = new Scanner(System.in);
        fileScan.nextLine();

        int counter = 0;
        while (fileScan.hasNextLine()){
            String[] characterLine = fileScan.nextLine().split(",");
            System.out.println(Arrays.toString(characterLine));

            if (characterLine[0].toLowerCase().equals("warrior")){
                String name = characterLine[1];
                int hp = Integer.parseInt(characterLine[2]);
                hp = correctStat(hp,"hp", "warrior", statScan, name);
                int stamina = Integer.parseInt(characterLine[3]);
                stamina = correctStat(stamina,"stamina", "warrior", statScan, name);
                int strength =Integer.parseInt(characterLine[4]);
                strength = correctStat(strength,"strength", "warrior", statScan, name);
                userArmy.add(new Warrior(counter, name, hp, true, stamina, strength));
                System.out.println("Warrior created from csv");

            } else if (characterLine[0].toLowerCase().equals("wizard")) {
                String name = characterLine[1];
                int hp = Integer.parseInt(characterLine[2]);
                hp = correctStat(hp,"hp", "wizard", statScan, name);
                int mana = Integer.parseInt(characterLine[3]);
                mana = correctStat(mana,"mana", "wizard", statScan, name);
                int intelligence = Integer.parseInt(characterLine[4]);
                intelligence = correctStat(intelligence,"intelligence", "wizard", statScan, name);
                userArmy.add(new Wizard(counter, name, hp, true, mana, intelligence));
                System.out.println("Wizard created from csv");
            }
            counter++;
        }
        return userArmy;
    }


    public static int correctStat(int value, String stat, String characterClass, Scanner scanner, String name){
        switch (characterClass){
            case "warrior":
                switch (stat){
                    case "hp":
                        while(value<100||value>200){
                            System.out.println("Type a valid hp for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                    case "stamina":
                        while(value<10||value>50){
                            System.out.println("Type a valid stamina for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                    case "strength":
                        while(value<1||value>10){
                            System.out.println("Type a valid strength for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                }
            case "wizard":
                switch (stat){
                    case "hp":
                        while(value<50||value>100){
                            System.out.println("Type a valid hp for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                    case "mana":
                        while(value<10||value>50){
                            System.out.println("Type a valid mana for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                    case "intelligence":
                        while(value<1||value>50){
                            System.out.println("Type a valid stamina for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                }

        }
        return value;
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
