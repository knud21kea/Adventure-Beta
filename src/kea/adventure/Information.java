package kea.adventure;

public class Information {

    public void setScene() {
        System.out.println(Colours.YELLOW_UNDERLINED + "\nWelcome to this text based Adventure.");
        System.out.println(Colours.YELLOW + """
                You can try to move North, East, South or West
                                
                It is also possible, nae recommended, to explore your current location. You may find blocked directions, or items lying around.
                Only the weak would think of quitting, but that is always an option.
                                
                Most actions cost strength points, but resting gets them back
                You can find food along the way, which can improve your strength, or reduce it.
                                
                Some rooms have NPCs who will react if attacked. Killing them could give access to better weapons.
                                        
                Find the long lost hero or fail trying. How will it end...?
                                
                To see a list of available commands type help. Maybe try that soon.""");
        System.out.println(Colours.RESET);
    }

    public void showSpoiler() {
        System.out.println(Colours.YELLOW + """
                                
                ############################ Spoiler Alert ################################
                     ___________________     __________________     __________________
                     |                  |    |                 |    |                 |
                     |    Courtyard     | == |   Chancellery   | == |    Ballroom     |
                     |__________________|    |_________________|    |_________________|
                              ||                                            ||
                     ___________________     __________________     __________________
                     |                  |    |                 |    |                 |
                     |   Banquet room   |    |    Catacomb     |    | Royal apartment |
                     |__________________|    |_________________|    |_________________|
                              ||                     ||                     ||
                     ___________________     __________________     __________________
                     |                  |    |                 |    |                 |
                     |    Little hall   | == |    Casements    | == |     Chapel      |
                     |__________________|    |_________________|    |_________________|
                     
                The catacomb is a special room.
                If you try to look around there without completing the task, you will lose.
                The task is just to find and move a gold bar and some holy water to the room.
                To win you must first drop these items and then look around.""");

        System.out.println(Colours.YELLOW_UNDERLINED + "## This option reduces your strength points ##");
        System.out.println(Colours.RESET);
    }

    public void getHelp() {
        System.out.println(Colours.YELLOW_UNDERLINED + "\nYou can use these commands, with some variations:");
        System.out.println(Colours.YELLOW + """
                H - Help (this - see what I did there?)
                L - Look around (room description - always worth a try)
                I - List inventory
                T - Take item
                D - Drop item
                F - Eat (food)
                Q - Equip weapon (arm)
                U - Unequip weapon (disarm)
                A - Attack
                N - Go North
                E - Go East
                S - Go South
                W - Go West
                R - Rest
                X - Exit
                M - Toggle music (not implemented)
                B - Background info
                C - Cheat (how to win)""");
        System.out.println(Colours.RESET);
    }

    public void showReadme() {
        System.out.println(Colours.CYAN + """
                ####################################################################################################
                                                   --  Adventure (version 2.0) --
                DAT21 Java project (compulsory group exercise).
                 Developers: Graham Heaven and Lasse Bøgeskov-Jensen, September 2021.
                A simple text-based adventure game inspired by "Colossal Cave Adventure",
                 by William Crowther and Don Woods, from 1976-1977.
                 
                Version 1.1:
                A single player navigates through a small maze of rooms using a simple text parser interface.
                In each room it is possible to get limited information about the room, and move to connected rooms.
                There is an achievable goal using the available commands.
                                
                Change log for version 1.2:
                Added items that can be carried from room to room, dropped or taken.
                Added strength points that are lost by doing actions and gained by resting.
                Added weights to items.
                Added max carrying weight for player, meaning sometimes something must be dropped to take something else.
                Added more commands: Inventory player, Inventory room, Take item, Drop item, Show cheat sheet.
                Added mission that requires successful combination of available commands. Player can also die in 2 different ways.
                Added music class
                Added context colours for text
                                
                Change log for version 1.3:
                Added food as extension to item, with both positive and negative food values
                Added Weapon as an extension to item, with sub-classes meleeWeapon and shootingWeapon
                Added Enemy as new class with ability to equip weapons and use them
                Added combat system
                Added commands: Eat, Attack
                Added documentation
                
                Change log for version 2.0:
                Added Controller
                Added User Interface
                Added enums
                Improved low coupling
                ################################################################################################################""");
        System.out.println(Colours.RESET);
    }

    public void noMove() {
        System.out.println(Colours.RED + "That way is blocked." + Colours.RESET);
    }
}