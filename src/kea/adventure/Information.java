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
                C - Cheat (how to win)""");
        System.out.println(Colours.RESET);
    }

    public void noMove() {
        System.out.println(Colours.RED + "That way is blocked." + Colours.RESET);
    }
}