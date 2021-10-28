// Only the player object knows which room he is in and which items he has

package kea.adventure;

import java.util.ArrayList;

import static kea.adventure.Health.*;

public class Player {

    private Room currentRoom;
    private Room requestedRoom;
    private final ArrayList<Item> itemsPlayer; // Player inventory

    private int strengthPoints = 100;
    private Weapon equippedWeapon;

    public Player(Map map, Room start, Weapon weapon) {
        this.currentRoom = start; //map.getStarterRoom();
        this.itemsPlayer = map.getInitialInventory();
        this.equippedWeapon = weapon;
    }

    // Player moves in the given direction or finds the way blocked

    public boolean changeRoom(Direction direction) {
        requestedRoom = this.currentRoom.getRoom(direction);
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
        return this.itemsPlayer;
    }

    // remove an item from inventory

    public void dropAnItem(Item itemDropped) {
        this.itemsPlayer.remove(itemDropped);
    }

    // add an item to inventory

    public void takeAnItem(Item itemTaken) {
        this.itemsPlayer.add(itemTaken);
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
            this.itemsPlayer.add(equippedWeapon);
        }
        this.equippedWeapon = weapon;
        this.itemsPlayer.remove(weapon);
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
        return itemsPlayer.size();
    }

    public String getItemName(int item) {
        return itemsPlayer.get(item).itemName;
    }

    public String getEquippedWeaponName() {
        return equippedWeapon.getItemName();
    }

    public int getEquippedWeaponDamage() {
        return equippedWeapon.getDamage();
    }

    public int getEquippedWeaponAmmo() {
        return equippedWeapon.getAmmo();
    }

    public boolean getEquippedWeaponCheckIfMelee() {
        return equippedWeapon.checkIfMelee();
    }

    public ArrayList<String> getListOfItemNames() {
        ArrayList<String> itemNames = new ArrayList<>();
        for (Item item : itemsPlayer) {
            itemNames.add(item.getItemName());
        }
        return itemNames;
    }
}


