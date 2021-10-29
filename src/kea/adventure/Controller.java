package kea.adventure;

import java.util.ArrayList;

public class Controller {

    private final UserInterface ui;
    private final Inventory inventory;
    private final Map map;
    private final Player player;

    public Controller() {
        ui = new UserInterface(this);
        inventory = new Inventory();
        map = new Map(inventory);
        map.buildMap();
        map.addStarterItems();
        player = new Player(map.getInitialInventory(), map.getStarterRoom(), inventory.knife);
    }

    public void start() {
        ui.loopMenu();
    }

    public Health checkState() {
        return player.checkStrength();
    }

    public int getStrength() {
        return player.getStrengthPoints();
    }

    public Room getCurrentRoom() {
        return player.getCurrentRoom();
    }

    public void updateStrengthPoints(int points) {
        player.updateStrengthPoints(points);
    }

    public boolean weaponIsEquipped() {
        return player.weaponIsEquipped();
    }

    public String getWeaponName() {
        return player.getEquippedWeaponName();
    }

    public int getWeaponDamage() {
        return player.getEquippedWeaponDamage();
    }

    public int getWeaponAmmo() {
        return player.getEquippedWeaponAmmo();
    }

    public boolean checkIfMelee() {
        return player.getEquippedWeaponCheckIfMelee();
    }

    public int getNumberOfPlayerObjects() {
        return player.numberOfObjects();
    }

    public int getNumberOfRoomObjects() {
        return player.getCurrentRoom().getNumberOfRoomObjects();
    }

    public boolean isThereAnEnemy() {
        return player.getCurrentRoomHasEnemies();
    }

    public String getEnemyName() {
        return player.getCurrentRoom().getEnemyName();
    }

    public int getEnemyHealth() {
        return player.getCurrentRoom().getEnemyHealth();
    }

    public String getEnemyWeaponName() {
        return player.getCurrentRoom().getEnemyWeaponName();
    }

    public int getWeightOfPlayerObjects() {
        return player.getTotalWeight();
    }

    public String getPlayerItemName(int item) {
        return player.getItemName(item);
    }

    public String getRoomItemName(int item) {
        return player.getCurrentRoom().getItemName(item);
    }

    public boolean changeRoom(Direction direction) {
        updateStrengthPoints(-3);
        return player.changeRoom(direction);
    }

    public boolean CurrentIsSpecial() {
        return (player.getCurrentRoom() == map.getSpecialRoom());
    }

    public boolean directionKnownBlocked(Direction direction) {
        return (player.getCurrentRoom().getKnown(direction) && player.getCurrentRoom().getRoom(direction) == null);
    }

    public boolean winConditions() {
        return (map.checkForHolyWater() && map.checkForGoldBar());
    }

    public ArrayList<String> tryToDo(String itemToTry, String action) {
        ArrayList<String> foundItemNames = null;
        switch (action) {
            case "TAKE" -> foundItemNames = tryToTake(itemToTry);
            case "DROP" -> foundItemNames = tryToDrop(itemToTry);
            case "EAT" -> foundItemNames = tryToEat(itemToTry);
            case "EQUIP" -> foundItemNames = tryToEquip(itemToTry);
            case "UNEQUIP" -> player.setEquippedWeapon(null);
        }
        return foundItemNames;
    }

    public ArrayList<String> tryToTake(String itemNameToTry) {
        Item foundItem = null;
        ArrayList<Item> foundItems = tryToCommon(player.getCurrentRoom().getRoomItems(), itemNameToTry);
        ArrayList<String> foundItemNames = new ArrayList<>();
        if (foundItems != null) {
            for (Item item : foundItems) {
                foundItem = item; // any valid item
                foundItemNames.add(item.getItemName());
            }
            if (foundItems.size() == 1) {
                boolean canTake = checkCanTakeFoundItem(foundItem);
                if (canTake) {
                    moveItemFromRoomToPlayer(foundItem);
                } else {
                    foundItemNames.add("Too heavy");
                    foundItemNames.remove(foundItem.getItemName());
                }
            }
        }
        return foundItemNames;
    }

    public boolean checkCanTakeFoundItem(Item item) {
        return (item.getItemWeight() + player.getTotalWeight() <= player.getMaxWeight());
    }

    public void moveItemFromRoomToPlayer(Item foundItem) {
        player.takeAnItem(foundItem); // first and only match added to player
        player.getCurrentRoom().takeItemFromRoom(foundItem); // and removed from current room
    }

    public ArrayList<String> tryToDrop(String itemNameToTry) {
        Item foundItem = null;
        ArrayList<Item> foundItems = tryToCommon(player.getPlayerItems(), itemNameToTry);
        ArrayList<String> foundItemNames = new ArrayList<>();
        if (foundItems != null) {
            for (Item item : foundItems) {
                foundItem = item; // any valid item
                foundItemNames.add(item.getItemName());
            }
            if (foundItems.size() == 1) {
                moveItemFromPlayerToRoom(foundItem);
            }
        }
        return foundItemNames;
    }

    public void moveItemFromPlayerToRoom(Item foundItem) {
        player.getCurrentRoom().addItemToRoom(foundItem); // first and only match added to current room
        player.dropAnItem(foundItem); // and removed from player
    }

    public ArrayList<String> tryToEat(String itemNameToTry) {
        Item foundItem = null;
        ArrayList<Item> foundItems = tryToCommon(player.getPlayerItems(), itemNameToTry);
        ArrayList<String> foundItemNames = new ArrayList<>();
        if (foundItems != null) {
            for (Item item : foundItems) {
                foundItem = item; // any valid item
                foundItemNames.add(item.getItemName());
            }
            if (foundItems.size() == 1) {
                boolean canEat = checkCanEatFoundItem(foundItem);
                if (canEat) {
                    eatItem((Food) foundItem); // Cast to food
                    if (foundItem == inventory.aPoisonApple) {
                        foundItemNames.add("A bad apple");
                        foundItemNames.remove(foundItem.getItemName());
                    }
                } else {
                    foundItemNames.add("Not food");
                    foundItemNames.remove(foundItem.getItemName());
                }
            }
        }
        return foundItemNames;
    }

    public boolean checkCanEatFoundItem(Item item) {
        return map.isFood(item);
    }

    public void eatItem(Food foundItem) {
        player.updateStrengthPoints(foundItem.getfoodValue());
        player.dropAnItem(foundItem); // first and only match removed from player;
        map.getRandomRoom(player.getCurrentRoom()).addItemToRoom(foundItem); // Eaten food respawns somewhere else
    }

    public ArrayList<String> tryToEquip(String itemNameToTry) {
        Item foundItem = null;
        ArrayList<Item> foundItems = tryToCommon(player.getPlayerItems(), itemNameToTry);
        ArrayList<String> foundItemNames = new ArrayList<>();
        if (foundItems != null) {
            for (Item item : foundItems) {
                foundItem = item; // any valid item
                foundItemNames.add(item.getItemName());
            }
            if (foundItems.size() == 1) {
                boolean canEquip = checkCanEquipFoundItem(foundItem);
                if (canEquip) {
                    equipItem((Weapon) foundItem); // Cast to weapon
                } else {
                    foundItemNames.add("Not weapon");
                    foundItemNames.remove(foundItem.getItemName());
                }
            }
        }
        return foundItemNames;
    }

    public boolean checkCanEquipFoundItem(Item item) {
        return map.isWeapon(item);
    }

    public void equipItem(Weapon foundItem) {
        player.setEquippedWeapon(foundItem);
    }

    public ArrayList<Item> tryToCommon(ArrayList<Item> givenInventory, String searchFor) {
        ArrayList<Item> foundItems = new ArrayList<>();
        for (Item item : givenInventory) {
            if (item.getItemName().toUpperCase().contains(searchFor)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public String tryToAttackEnemy() {
        String outcome;
        if (!player.weaponIsEquipped()) {
            return "No weapon";
        } else if (!player.equippedWeaponIsUsable()) {
            return "No ammo";
        } else if (player.getCurrentRoom().getNumberOfEnemies() == 0) {
            return "No enemy";
        } else {
            outcome = playerAttacksEnemy();
        }
        return outcome;
    }

    private String playerAttacksEnemy() {
        String enemyName = player.getCurrentRoom().getRoomEnemies().get(0).getEnemyName();
        int firstSpace = enemyName.indexOf(" ");
        int damage = player.getEquippedWeapon().getDamage();
        player.useEquippedWeapon(); // decrement ammo if shooting (override)
        player.getCurrentRoom().getRoomEnemies().get(0).changeHealth(-damage);
        return "You did " + damage + " damage to the " + enemyName.substring(firstSpace + 1) + " :)";
    }

    public String enemyTryToCounterattack() {
        // If enemy strength goes below 0, he dies and is replaced in the room by his dropped weapon
        // Otherwise counterattacks
        String enemyName = player.getCurrentRoom().getEnemyName();
        int firstSpace = enemyName.indexOf(" ");

        if (player.getCurrentRoom().getRoomEnemies().get(0).getEnemyHealth() <= 0) {
            String weaponName = player.getCurrentRoom().getEnemyWeaponName();
            player.getCurrentRoom().dropEnemyWeapon(); // add weapon to room
            player.getCurrentRoom().removeEnemyFromRoom(); // remove dead enemy from room
            return "You killed the " + enemyName.substring(firstSpace + 1) + "\n" + weaponName + " falls to the floor, and can be taken.";
        } else {
            int damage = player.getCurrentRoom().getEnemyDamage();
            player.updateStrengthPoints(-damage);
            return "You took " + damage + " damage from the " + enemyName.substring(firstSpace + 1) + " :(";
        }
    }

    public String getCurrentRoomName() {
        return null;
    }

    public ArrayList<String> getListOfPlayerItemNames() {
        return player.getListOfItemNames();
    }

    public ArrayList<String> getListOfRoomItemNames() {
        return player.getListOfRoomItemNames();
    }
}
