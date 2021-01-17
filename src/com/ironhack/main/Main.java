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
//        PREGUNTA: ¿DEBERIAMOS CAMBIAR TODOS LOS SCANNER.NEXTINT POR INTEGER.PARSEINT(SCANNER.NEXTLINE)
//        Y NOS AHORRAMOS LOS SCANNER.NEXTLINE DESPUES DE CADA NEXTINT, O PENSAIS QUE NOS VAN A SALTAR ERRORES?

//        We start creating two empty arrays: one for our team and another for the graveyard:
        ArrayList<Character> userArmy = new ArrayList<>();
        ArrayList<Character> graveyard = new ArrayList<>();

//        creating a Scanner object to ask for data from console
        Scanner scanner = new Scanner(System.in);

//        Along with the welcome message we ask whether the user wish to import the team from a file or create it manually
        System.out.println("Welcome to the game!!! \n Do you wish to import your own party? Y/N");
//        Save the answer in variable csv. If "y", import the text from the file.
//        Otherwise, the user will be asked to write the info from each character
        String csv = scanner.nextLine();

        if (csv.toLowerCase().equals("y")){
//            We ask for the name of the file, and pass it to the importArmy function(line ...) to create our team
            System.out.println("Please, write the name of your .csv file:");
            String fileName = scanner.nextLine();
            userArmy = importArmy(userArmy, fileName);

        }else {
//            Ask for the length of our team and store it in numberOfCharacters. This length should be higher than 0,
//            but lower than 10 so the battle won't be endless
            System.out.println("How many Characters do you want in your army?\n You can choose a number between 1 and 9");
            int numberOfCharacters = Integer.parseInt(scanner.nextLine());

            while(numberOfCharacters<1||numberOfCharacters>9){
                System.out.println("Type a valid number");
                numberOfCharacters = Integer.parseInt(scanner.nextLine());
            }

//            For each index in userArmy(from 0 to numberOfCharacters) we ask for the new character to create,
//            and depending on the chosen option, we will call either createAWarrior(line ...) or createAWizard(line ...)
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

//        Now we can create the enemy team calling createRandomArmy(line ...) and passing our team length to this function
        ArrayList<Character>enemyArmy = createRandomArmy(userArmy.size());

//        The battle starts and will continue until one of the teams has length 0.
//        This is, when all the members of one team have been removed from their array to the graveyard
        while(enemyArmy.size()>0 && userArmy.size()>0){

//            Choosing a random enemy:
            Character enemyCharacter = enemyArmy.get((int) Math.floor(Math.random() * enemyArmy.size()));

//            Print this enemy info on the console calling the getStats method:
            System.out.println("YOUR OPPONENT IS:");
            enemyCharacter.getStats();

//            Asking for the user to choose his character to fight the enemy.
//            We'll ask from a number from 0 to userArmy.size()-1, that will correspond to the id of one character of our team.
//            If the introduced value is not inside this range, the user will be asked again.
//            We store the id of the chosen character in selection
            int selection = userArmy.size();
            System.out.println("Choose one of YOUR characters to fight!!!!!!!");

            while (selection < 0 || selection >= userArmy.size()){
                System.out.println("You have to choose a number between 0 and "+ (userArmy.size()-1));
//                por alguna razon, aquí no me deja hacer lo de Integer.parseInt, así que lo dejo tal cual
                selection = scanner.nextInt();
                scanner.nextLine();
            }

//            Make sure this is the chosen fighter:
            String sure;
            do{
                userArmy.get(selection).getStats();
                System.out.println("Are you sure Y/N");
                sure= scanner.nextLine();

            } while (!sure.toLowerCase().equals("y"));

//            Here we go. The characters will fight until one of them is no longer alive:
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

//            Before the next fight begins, we have the option of going to the graveyard and check:
            String answer;
            do{
                System.out.println("Next action \n 1: Continue with battle \n 2: Check the graveyard");
                answer = scanner.nextLine();
            }while (!answer.equals("2") && !answer.equals("1"));

            if (answer.equals("2")){
                // Show graveyard
                for (Character i: graveyard) {
                    System.out.println("Here lies " + i.getName() + ". Beloved friend. So so father and husband");
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

//    createWarrior receives a Scanner to ask for Warrior data from console, and the character id
    public static Warrior createAWarrior(Scanner scanner, int id){
//        We ask for a name:
        System.out.println("Choose a name for your warrior");
        String name= scanner.nextLine();

//        Asking for the health points and checking if the introduced values are inside of normal ranges with correctStat(line ...)
//        Same with strength and stamina
        System.out.println("Choose his Health Points between 100 and 200");
        int hp = Integer.parseInt(scanner.nextLine());
        hp = correctStat(hp,"hp", "warrior", scanner, name);

        System.out.println("Choose his stamina between 10 and 50");
        int stamina = Integer.parseInt(scanner.nextLine());
        stamina = correctStat(stamina,"stamina", "warrior", scanner, name);

        System.out.println("Choose his strength between 1 and 10");
        int strength = Integer.parseInt(scanner.nextLine());
        strength = correctStat(strength,"strength", "warrior", scanner, name);

        return new Warrior(id, name, hp, true, stamina, strength);
    }

//    createWizard receives a Scanner to ask for Warrior data from console, and the character id. It goes the same as in createWarrior
    public static Wizard createAWizard(Scanner scanner, int id){
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

//    importArmy allows to create an army from a csv file.
//    The data in this csv should be presented as: CLASS,NAME,HP,STAMINA/MANA,STRENGTH/INTELLIGENCE, all separated by "," and the first line being a header.
//    This function will receive an array to store the characters in, and the file name.
//    It will throw an exception if the file is not in the directory
    public static ArrayList<Character> importArmy(ArrayList<Character> userArmy, String fileName) throws FileNotFoundException {
//        Open the file and create an scanner object for reading it and another one to ask for valid values that are not correct in the file
        File file = new File(fileName);
        Scanner fileScan = new Scanner(file);
        Scanner statScan = new Scanner(System.in);
//        Jump the header:
        fileScan.nextLine();

//        counter will be used to provide the character with an id.
//        As long as the file has one more line, the function will continue to add characters into the array
        int counter = 0;
        while (fileScan.hasNextLine()){
//            break the line in the commas, and store the words in the list characterLine.
//            First index will be the class, then name, hp and so on
            String[] characterLine = fileScan.nextLine().split(",");
//            System.out.println(Arrays.toString(characterLine));

//            check the first index to be class warrior or wizard, and create the character from there:
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

//correctStat checks on the statistic of a character to be valid. It receives the current value of the statistic,
// its name, the kind of character, a Scanner to ask for valid statistics if needed, and the name of the character
    public static int correctStat(int value, String stat, String characterClass, Scanner scanner, String name){
//        first check: is it a warrior or a wizard?
        switch (characterClass){
            case "warrior":
//                second check: which of its statistics are we looking at?
                switch (stat){
                    case "hp":
                        while(value<100 || value>200){
                            System.out.println("Type a valid hp for " + name);
                            value = Integer.parseInt(scanner.nextLine());
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
                break;
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
                            System.out.println("Type a valid intelligence for " + name);
                            value = scanner.nextInt();
                            scanner.nextLine();
                        }
                        break;
                }
                break;
        }
        return value;
    }

//createRandomArmy creates the enemy army. It receives the length of our own army so both are the same.
    public static ArrayList<Character> createRandomArmy(int numOfCharacters){
//        counter variable to use as id of the characters
        int counter=0;

//        calling getNames(line ...) to obtain the list of names of the enemies
        String[] names= getNames(numOfCharacters);

        ArrayList<Character> army= new ArrayList<Character>(numOfCharacters);

        while (counter<numOfCharacters){
            int numero = (int) Math.round(Math.random());
            //if 1, create a warrior, and if 0, a wizard

            if(numero==1){
//                randomizing statistics:
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

//    getNames chooses random names from an array of names. It receives the amount of characters we need to name,
//    and gives the list of chosen names
    public static String[] getNames(int numOfNames){
        String[] names={"The Hammer","The Vengeful","The Tempest","The Enigma","The Grim","Strong Grimace","Moltenblood","Gore Sorrow","Gorehead","Firegrim"};
        String[] selectedNames=new String[numOfNames];

        for(int i=0; i<numOfNames; i++){
            int rnd = (int)Math.floor(Math.random()*names.length);

//            Since the names are randomly picked from the array names, it will first check if the chosen name are already in selectedNames,
//            and if so, add Jr. at the end of the name and then place it in selectedNames
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