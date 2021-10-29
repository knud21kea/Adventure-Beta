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
    private Inventory inventory;
    private final ArrayList<Item> startInventory = new ArrayList<>();
    private final Random rand = new Random();

    public Map(Inventory inventory) {
        this.inventory = inventory;
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
        chancellery.addItemToRoom(inventory.brassLamp);
        ballroom.addItemToRoom(inventory.diamondRing);
        ballroom.addItemToRoom(inventory.emptyBottle);
        banquet.addItemToRoom(inventory.aPoisonApple);
        courtyard.addItemToRoom(inventory.anApple);
        apartment.addItemToRoom(inventory.aPear);
        banquet.addItemToRoom(inventory.silverCoin);
        banquet.addItemToRoom(inventory.goldBar);
        apartment.addItemToRoom(inventory.goldKey);
        hall.addItemToRoom(inventory.oldParchment);
        chapel.addItemToRoom(inventory.holyWater);

        courtyard.addItemToRoom(inventory.sword);
        chancellery.putEnemyInRoom(inventory.orc);
        casements.putEnemyInRoom(inventory.elf);
        chapel.putEnemyInRoom(inventory.goblin);

        // Items the player has to start with

        startInventory.add(inventory.magneticCompass);
        startInventory.add(inventory.boxOfMatches);
        startInventory.add(inventory.paperClip);
        startInventory.add(inventory.silverKey);
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
        return (objects.contains(inventory.holyWater));
    }

    public boolean checkForGoldBar() {
        ArrayList<Item> objects = getSpecialRoom().getRoomItems();
        return (objects.contains(inventory.goldBar));
    }
}

