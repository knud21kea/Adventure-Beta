/*   ___________________     __________________     __________________
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
*/

package kea.adventure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map {

    private Room courtyard, chancellery, ballroom, banquet, catacombs, apartment, hall, casements, chapel;
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Item> startInventory = new ArrayList<>();
    private final Random rand = new Random();

    // Create all instances of Item class
    // Some names have duplicates to test the parser - key, gold, silver etc.

    Item brassLamp = new Item("A brass lamp", 15);
    Item diamondRing = new Item("A diamond ring", 5);
    Item emptyBottle = new Item("An empty bottle", 5);
    Item silverKey = new Item("A silver key", 1);
    Item silverCoin = new Item("A silver coin", 1);
    Item goldKey = new Item("A gold key", 1);
    Item goldBar = new Item("A gold bar", 20);
    Item holyWater = new Item("Some holy water", 10);
    Item oldParchment = new Item("An old parchment", 2);
    Item magneticCompass = new Item("A magnetic compass", 5);
    Item boxOfMatches = new Item("A box of matches", 3);
    Item paperClip = new Item("A paper clip", 0);

    Food anApple = new Food("A green apple", 5, 10);
    Food aPoisonApple = new Food("A red apple", 5, -10);
    Food aPear = new Food("A ripe pear", 5, 5);

    Weapon knife = new MeleeWeapon("A blunt knife", 5, 1, 1);
    Weapon dagger = new MeleeWeapon("A sharp dagger", 5, 5, 1);
    Weapon sword = new MeleeWeapon("A sword", 10, 15, 1);
    Weapon axe = new MeleeWeapon("An axe", 15, 25, 1);
    Weapon bow = new ShootingWeapon("A bow", 5, 10, 3);

    Enemy orc = new Enemy("An ugly orc", 50, axe);
    Enemy elf = new Enemy("A dark elf", 60, bow);
    Enemy goblin = new Enemy("A small goblin", 40, dagger);

    public Map() {
    }

    public void buildMap() {

        // Create all instances of Room class

        courtyard = new Room("a courtyard", "Walls here are clad with sandstone, and the surrounding roofs with copper sheeting.");
        chancellery = new Room("the chancellery", "A luxurious room with a magnificent canopy which is woven in gold and silver.");
        ballroom = new Room("the ballroom", "A great hall where the loft is panelled with elaborate wood carvings, paintings and gilding.");
        banquet = new Room("a banquet room", "Polished silver and sparkling glass. Brass chandeliers fitted with honey-scented candles.");
        catacombs = new Room("the catacombs", "Dark and foreboding with a brooding presence. A special place.");
        apartment = new Room("one of the royal apartments", "A richly decorated room with ceiling paintings, stone portals and fireplaces.");
        hall = new Room("a small hall", "The walls are clad with seven intricately woven tapestries.");
        casements = new Room("the casements", "Gloomy, cold and damp. There are signs that horses and soldiers have been here.");
        chapel = new Room("a chapel", "A small place of worship with well preserved original altar, gallery, and pews.");

        List<Room> roomList = Arrays.asList(courtyard, chancellery, ballroom, banquet, catacombs, apartment, hall, casements, chapel);
        rooms.addAll(roomList);

        // Make connections - auto 2 way

        courtyard.connectSouthNorth(banquet);
        courtyard.connectEastWest(chancellery);
        chancellery.connectEastWest(ballroom);
        ballroom.connectSouthNorth(apartment);
        apartment.connectSouthNorth(chapel);
        casements.connectEastWest(chapel);
        hall.connectEastWest(casements);
        catacombs.connectSouthNorth(casements);
        banquet.connectSouthNorth(hall);
    }

    public void addStarterItems() {

        // Place items in rooms
        chancellery.addItemToRoom(brassLamp);
        ballroom.addItemToRoom(diamondRing);
        ballroom.addItemToRoom(emptyBottle);
        banquet.addItemToRoom(aPoisonApple);
        courtyard.addItemToRoom(anApple);
        apartment.addItemToRoom(aPear);
        banquet.addItemToRoom(silverCoin);
        banquet.addItemToRoom(goldBar);
        apartment.addItemToRoom(goldKey);
        hall.addItemToRoom(oldParchment);
        chapel.addItemToRoom(holyWater);

        courtyard.addItemToRoom(sword);
        chancellery.putEnemyInRoom(orc);
        casements.putEnemyInRoom(elf);
        chapel.putEnemyInRoom(goblin);

        // Items the player has to start with

        startInventory.add(magneticCompass);
        startInventory.add(boxOfMatches);
        startInventory.add(paperClip);
        startInventory.add(silverKey);
    }

    // Default start is in room 1 (now an argument in Player class)

    public Room getStarterRoom() {
        return courtyard;
    }

    // Something special about room 5

    public Room getSpecialRoom() {
        return catacombs;
    }

    public Room getRandomRoom(Room currentRoom) {
        Room randomRoom = catacombs;
        while (randomRoom == catacombs || randomRoom == currentRoom) {
            randomRoom = rooms.get(rand.nextInt(9));
        }
        return randomRoom; // Returns random room that is neither catacombs nor current room
    }

    // Testing inventory. Probably should start without items.

    public ArrayList<Item> getInitialInventory() {
        return startInventory;
    }

    public boolean isWeapon(Item item) {
        return (item instanceof Weapon);
    }

    public boolean isFood(Item item) {
        return (item instanceof Food);
    }

    public boolean checkForHolyWater() {
        ArrayList<Item> objects = getSpecialRoom().getRoomItems();
        return (objects.contains(holyWater));
    }

    public boolean checkForGoldBar() {
        ArrayList<Item> objects = getSpecialRoom().getRoomItems();
        return (objects.contains(goldBar));
    }
}

