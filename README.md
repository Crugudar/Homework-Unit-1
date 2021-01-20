# Homework-Unit-1

## First steps:

When you start the program, you will be asked whether you prefer to import your own party from an external .csv file or manually create your characters. 

### Import from .csv
The user must write "y" to choose this option. If that is the case, the user will be asked for the file name. The file must include a header with the following fields:

`CLASS,NAME,HP,STAMINA/MANA,STRENGTH/INTELLIGENCE`

- The `CLASS` field can only be "warrior" or "wizard" (it's not case sensitive)
- The `NAME` field can be anything you want (now you have the chance to become a number!!!)
- `HP`: between 100-200 to warriors, 50-100 for wizards
- `STAMINA` (for warriors) and `MANA` (for wizards): between 10-50
- `STRENGTH` (for warriors): between 1-10 / `INTELLIGENCE` (for wizards): between 1-50

If you want an example, we have included the files *wrongParty.csv* (which contains two characters with invalid stats) and *party.csv*.

### Introduce your characters manually
If you don't want to import a file, you will be asked how many members will be in your party (between 1 and 9). Next, you will be able to choose the class (warrior or wizard), and the rest of the stats. Again, the limits will be the same as previously explained.

When your party is complete, the enemy party will be generated. The size of the enemy party will be the same as yours, and their stats will be randomly generated inside the stablished limits explained before. Also, you will be asked if you want to export your party to a .csv file. Write "y" to do so.

## The battle begins
First, a random opponent will be chosen, and its stats will be shown in the console. Then, you will be asked to choose between:

1. Choosing your fighter
2. Automatic fight

### Automatic fight
If you choose this option, it cannot be reversed. During the automatic fight you won't be able to choose your fighter. Both your fighter and the enemy's will be randomly chosen until any of the parties is DESTROYED.

(Good luck)

### Choosing your fighter
Here you can choose your fighter by id. Then, its stats will be shown, but you can change your decision before starting the battle! Every time a combat ends, you will be asked to choose your fighter again.

Also, regardless of the mode you choose, when a single combat ends, you will be asked if you want to see the graveyard. Don't forget to pay your respects.

