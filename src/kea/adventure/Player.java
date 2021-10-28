// Only the player object knows which room he is in and which items he has

package kea.adventure;

import java.util.ArrayList;

import static kea.adventure.Health.*;

public class Player {

    private Room currentRoom;
    private Room requestedRoom;
    private final ArrayList<Item> ordinaryItemsPlayer; // Player inventory

    private int strengthPoints = 100;
    private Weapon equippedWeapon;

    public Player(Map map, Room start, Weapon weapon) {
        this.currentRoom = start; //map.getStarterRoom();
        this.ordinaryItemsPlayer = map.getInitialInventory();
        this.equippedWeapon = weapon;
    }

    // Player moves in the given direction or finds the way blocked

    public boolean changeRoom(String direction) {
        switch (direction) {
            case "N" -> requestedRoom = this.currentRoom.getRoom("North");
            case "E" -> requestedRoom = this.currentRoom.getRoom("East");
            case "S" -> requestedRoom = this.currentRoom.getRoom("South");
            case "W" -> requestedRoom = this.currentRoom.getRoom("West");
        }
        if (requestedRoom != null) {
            this.currentRoom = requestedRoom; // move to new room
            return true;
        }
        return false;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    // Player inventory list

    public ArrayList<Item> getPlayerItems() {
        return this.ordinaryItemsPlayer;
    }

    // remove an item from inventory

    public void dropAnItem(Item itemDropped) {
        this.ordinaryItemsPlayer.remove(itemDropped);
    }

    // add an item to inventory

    public void takeAnItem(Item itemTaken) {
        this.ordinaryItemsPlayer.add(itemTaken);
    }

    public int getStrengthPoints() {
        return this.strengthPoints;
    }

    public void setStrengthPoints(int strengthPoints) {
        this.strengthPoints = strengthPoints;
    }

    public Health checkStrength() {
        if (strengthPoints < 1) {
            return DEAD;
        } else if (strengthPoints < 20) {
            return EXHAUSTED;
        } else if (strengthPoints < 40) {
            return TIRED;
        } else if (strengthPoints < 70) {
            return WEARY;
        } else if (strengthPoints < 100) {
            return FRESH;
        } else {
            return RESTED;
        }
    }

    public int getMaxWeight() {
        return 25;
    }

    // return total weight of inventory including equipped weapon.

    public int getTotalWeight() {
        ArrayList<Item> objects = getPlayerItems();
        int weight = 0;
        for (Item object : objects) {
            weight += (object.getItemWeight());
        }
        if (getEquippedWeapon() != null) {
            weight += getEquippedWeapon().itemWeight;
        }
        return weight;
    }

    public Weapon getEquippedWeapon() {
        return this.equippedWeapon;
    }

    public void setEquippedWeapon(Weapon weapon) {
        if (this.equippedWeapon != null) {
            this.ordinaryItemsPlayer.add(equippedWeapon);
        }
        this.equippedWeapon = weapon;
        this.ordinaryItemsPlayer.remove(weapon);
    }

    // Actions use strength points, resting gains strength points

    public void updateStrengthPoints(int update) {
        int strength = getStrengthPoints();
        strength += update;
        if (strength > 100) {
            strength = 100;
        } else if (strength < 1) {
            strength = 0;
        }
        setStrengthPoints(strength);
    }

    public boolean weaponIsEquipped() {
        return (equippedWeapon != null);
    }

    public boolean equippedWeaponIsUsable() {
        return (equippedWeapon.getAmmo() > 0);
    }

    public void useEquippedWeapon() {
        equippedWeapon.shootWeapon();
    }

    public int numberOfObjects() {
        return ordinaryItemsPlayer.size();
    }

    public String getItemName(int item) {
        return ordinaryItemsPlayer.get(item).itemName;
    }
}


