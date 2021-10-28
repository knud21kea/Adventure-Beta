package kea.adventure;

import java.util.ArrayList;

public class Room {

    // "known" attribute is used to update room description when a direction has been tried and found blocked

    private final String roomName, roomDescription;
    private Room north, east, west, south;
    private boolean knownNorth, knownEast, knownSouth, knownWest;
    private final ArrayList<Item> items = new ArrayList<>(); // List of Item objects that are in the room
    private final ArrayList<Enemy> enemies = new ArrayList<>(); // List of Enemy objects that are in the room

    public Room(String roomName, String roomDescription) {
        this.roomName = roomName;
        this.roomDescription = roomDescription;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public ArrayList<Item> getRoomItems() {
        return this.items;
    }

    public String getItemName(int item) {
        return this.items.get(item).itemName;
    }

    public int getNumberOfRoomObjects() {
        return this.items.size();
    }

    public ArrayList<Enemy> getRoomEnemies() {
        return this.enemies;
    }

    public int getNumberOfEnemies() {
        return this.enemies.size();
    }

    public String getEnemyName() {
        return this.enemies.get(0).getEnemyName();
    }

    public int getEnemyHealth() {
        return this.enemies.get(0).getEnemyHealth();
    }

    public String getEnemyWeaponName() {
        return this.enemies.get(0).getWeaponName();
    }

    // Add item to room
    public void addItemToRoom(Item item) {
        this.items.add(item);
    }

    // Take item from room
    public void takeItemFromRoom(Item item) {
        this.items.remove(item);
    }

    public void putEnemyInRoom(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void removeEnemyFromRoom() {
        this.enemies.clear();
    }

    public void dropEnemyWeapon() {
        addItemToRoom(enemies.get(0).getEnemyWeapon());
    }

    public int getEnemyDamage() {
        return enemies.get(0).getWeaponDamage();
    }

    // connect two rooms South to North

    public void connectSouthNorth(Room south) {
        this.south = south;
        south.north = this;
    }

    // connect two rooms East to West

    public void connectEastWest(Room east) {
        this.east = east;
        east.west = this;
    }

    // Find a room in the given direction and note that the player has now tried to go there

    public Room getRoom(Direction direction) {
        Room room = null;
        switch (direction.name) {
            case "North" -> {
                this.knownNorth = true;
                room = north;
            }
            case "East" -> {
                this.knownEast = true;
                room = east;
            }
            case "South" -> {
                this.knownSouth = true;
                room = south;
            }
            case "West" -> {
                this.knownWest = true;
                room = west;
            }
        }
        return room;
    }

    // Check if the player has previously tried to go the given direction

    public boolean getKnown(Direction direction) {
        boolean isKnown = false;
        switch (direction.name) {
            case "North" -> isKnown = knownNorth;
            case "East" -> isKnown = knownEast;
            case "South" -> isKnown = knownSouth;
            case "West" -> isKnown = knownWest;
        }
        return isKnown;
    }

    @Override
    public String toString() {
        return roomName + ": " + roomDescription;
    }
}
