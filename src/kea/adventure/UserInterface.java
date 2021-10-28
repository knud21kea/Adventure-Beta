package kea.adventure;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {

    private final Scanner input = new Scanner(System.in);
    private final Controller controller;
    private final Information info = new Information();
    private String menuOption;
    private boolean canMove;

    public UserInterface(Controller controller) {
        this.controller = controller;
    }

    public void loopMenu() {
        info.setScene();
        menuOption = startYesNo();

        // Get inputs until user types exit/x or game is won/lost

        while (!menuOption.equals("X") && !menuOption.equals("EXIT") && !menuOption.equals("DEAD")) {

            Health state = controller.checkState();
            System.out.println(state.getStateMessage());
            menuOption = state.getStateHealth();

            if (!menuOption.equals("DEAD")) {
                outputBasicDescription();
                canMove = true; //used to only print blocked if user tries a blocked route
                System.out.print("What do you want to do? ");
                menuOption = input.nextLine().toUpperCase();

                // Player choice with multiple command forms - had to drop the case switch

                if (menuOption.equals("EXIT") || menuOption.equals("X") || menuOption.equals("QUIT")) {
                    menuOption = checkEndMessage();
                } else if (menuOption.equals("HELP") || menuOption.equals("H") || menuOption.equals("INFO")) {
                    info.getHelp();
                    controller.updateStrengthPoints(-1);
                } else if (menuOption.equals("CHEAT") || menuOption.equals("C") || menuOption.equals("SPOILER")) {
                    info.showSpoiler();
                    controller.updateStrengthPoints(-10);
                } else if (menuOption.equals("REST") || menuOption.equals("R") || menuOption.equals("SLEEP")) {
                    controller.updateStrengthPoints(20);
                } else if (menuOption.equals("INVENTORY") || menuOption.equals("INV") || menuOption.equals("I")) {
                    outputInventory();
                } else if (menuOption.equals("EXPLORE") || menuOption.equals("LOOK") || menuOption.equals("L")) {
                    menuOption = lookAround();
                } else if (menuOption.equals("DROP") || menuOption.equals("D")) {
                    dropSomething();
                } else if (menuOption.startsWith("DROP ") || menuOption.startsWith("D ")) {
                    dropItem(menuOption);
                } else if (menuOption.equals("TAKE") || menuOption.equals("T")) {
                    takeSomething();
                } else if (menuOption.startsWith("TAKE ") || menuOption.startsWith("T ")) {
                    takeItem(menuOption);
                } else if (menuOption.equals("EAT") || menuOption.equals("F")) {
                    eatSomething();
                } else if (menuOption.startsWith("EAT ") || menuOption.startsWith("F ")) {
                    eatFood(menuOption);
                } else if (menuOption.equals("EQUIP") || menuOption.equals("Q")) {
                    equipSomething();
                } else if (menuOption.startsWith("EQUIP ") || menuOption.startsWith("Q ")) {
                    equipWeapon(menuOption);
                } else if (menuOption.equals("UNEQUIP") || menuOption.equals("U")) {
                    unequipWeapon();
                } else if (menuOption.equals("ATTACK") || menuOption.equals("A")) {
                    attackMiniGame();
                } else if (menuOption.equals("GO") || menuOption.equals("G")) {
                    canMove = goSomewhere();
                } else if (menuOption.equals("GO NORTH") || menuOption.equals("NORTH") || menuOption.equals("N") || menuOption.equals("GO N")) {
                    canMove = controller.changeRoom("N");
                } else if (menuOption.equals("GO EAST") || menuOption.equals("EAST") || menuOption.equals("E") || menuOption.equals("GO E")) {
                    canMove = controller.changeRoom("E");
                } else if (menuOption.equals("GO SOUTH") || menuOption.equals("SOUTH") || menuOption.equals("S") || menuOption.equals("GO S")) {
                    canMove = controller.changeRoom("S");
                } else if (menuOption.equals("GO WEST") || menuOption.equals("WEST") || menuOption.equals("W") || menuOption.equals("GO W")) {
                    canMove = controller.changeRoom("W");
                } else {
                    unknownCommand(menuOption);
                }
            }
            if (!canMove) {
                info.noMove();
            }
        }
    }

    private String startYesNo() {
        System.out.print("Are you ready to start the adventure? (Y/N) ");
        menuOption = input.nextLine().toUpperCase();
        if (!menuOption.startsWith("Y")) {
            menuOption = "EXIT";
        } else {
            menuOption = "START";
        }
        return menuOption;
    }

    private String checkEndMessage() {
        System.out.print(Colours.YELLOW + "Are you sure you want to quit? (y/n) " + Colours.RESET);
        String menuOption = input.nextLine().toUpperCase();
        if (menuOption.startsWith("Y")) {
            System.out.println(Colours.YELLOW + "Really? Hope to see you again soon. Bye." + Colours.RESET);
            return "EXIT";
        } else {
            controller.updateStrengthPoints(-1);
            return "CONTINUE";
        }
    }

    public void unknownCommand(String menuOption) {
        System.out.println(Colours.YELLOW + "I do not understand \"" + menuOption + "\"." + Colours.RESET);
    }

    private void outputBasicDescription() {
        System.out.print("\nStrength " + controller.getStrength() + "% ");
        System.out.print(":You are in " + controller.getCurrentRoom().getRoomName() + ": ");
        System.out.println(controller.getCurrentRoom().getRoomDescription());
    }

    public void outputInventory() {
        System.out.print(Colours.BLUE + "Your strength is at " + controller.getStrength() + "% ");
        if (!controller.weaponIsEquipped()) {
            System.out.print("and you are unarmed.");
        } else {
            System.out.print("and you are equipped with " + makeFirstLetterLowerCase(controller.getWeaponName()));
            System.out.print(" which does " + controller.getWeaponDamage() + " damage");
            if (!controller.checkIfMelee()) {
                System.out.print(" and has " + controller.getWeaponAmmo() + " shots remaining");
            }
            System.out.print(".");
        }
        int objects = controller.getNumberOfPlayerObjects();
        int weight = controller.getWeightOfPlayerObjects();
        System.out.print("\nYou are carrying ");
        if (objects == 0) {
            System.out.println("no items of total weight " + weight + " including any equipped weapons.");
        } else if (objects == 1) {
            System.out.println("1 item of total weight " + weight + " including any equipped weapons:\n" + controller.getPlayerItemName(0) + ".");
        } else {
            System.out.print(objects + " items of total weight " + weight + " including any equipped weapons:\n" + controller.getPlayerItemName(0));
            for (int i = 1; i < objects - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(controller.getPlayerItemName(i)));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(controller.getPlayerItemName(objects - 1)) + ".");
        }
        System.out.print(Colours.RESET);
    }

    private String makeFirstLetterLowerCase(String itemName) {
        return itemName.substring(0, 1).toLowerCase() + itemName.substring(1);
    }

    // Overload if user only types "go" or "g"

    public boolean goSomewhere() {
        boolean canMove = false;
        System.out.print("Hmmm. Which direction do you want to go? ");
        String direction = input.nextLine().toUpperCase();
        if (direction.startsWith("N")) {
            canMove = controller.changeRoom("N");
        } else if (direction.startsWith("E")) {
            canMove = controller.changeRoom("E");
        } else if (direction.startsWith("S")) {
            canMove = controller.changeRoom("S");
        } else if (direction.startsWith("W")) {
            canMove = controller.changeRoom("W");
        }
        return canMove;
    }

    public String lookAround() {
        controller.updateStrengthPoints(-1);
        if (!controller.CurrentIsSpecial()) {
            System.out.println("\nLooking around.");
            String[] directions = {"North", "East", "South", "West"};
            for (int i = 0; i < 4; i++) {
                String direction = directions[i];
                if (controller.directionKnownBlocked(direction)) {
                    System.out.println(Colours.BLUE + "The way " + direction + " is blocked." + Colours.RESET);
                }
            }
            outputDescription();
            return "CONTINUE";
        } else {
            if (!controller.winConditions()) {
                System.out.println(Colours.RED + "\nYou woke Holger Danske without fullfilling his needs . He is not happy.");
            } else {
                System.out.println(Colours.GREEN + "\nYou woke Holger Danske and honoured his needs. Congratulations.");
            }
            System.out.print(Colours.RESET);
            return "EXIT";
        }
    }// Room inventory - with formatted output

    public void outputDescription() {
        System.out.print(Colours.BLUE);
        int objects = controller.getNumberOfRoomObjects();
        System.out.print("You can see ");
        if (objects == 0) {
            System.out.println("no items.");
        } else if (objects == 1) {
            System.out.println("1 item:\n" + controller.getRoomItemName(0) + ".");
        } else {
            System.out.print(objects + " items:\n" + controller.getRoomItemName(0));
            for (int i = 1; i < objects - 1; i++) {
                System.out.print(", " + makeFirstLetterLowerCase(controller.getRoomItemName(i)));
            }
            System.out.println(" and " + makeFirstLetterLowerCase(controller.getRoomItemName(objects - 1)) + ".");
        }
        int enemies = controller.getNumberOfEnemies();
        if (enemies > 0) {
            System.out.print("There is an enemy here, " + makeFirstLetterLowerCase(controller.getEnemyName()));
            System.out.print(", with " + makeFirstLetterLowerCase(controller.getEnemyWeaponName()));
            System.out.println(", and health " + controller.getEnemyHealth() + ".");
        } else {
            System.out.println("There are no enemies here.");
        }
        System.out.print(Colours.RESET);
    }

    // Overload take item when user types only "take"

    public void takeSomething() {
        System.out.print("Hmmm. Which item do you want to pick up? ");
        String itemToTake = input.nextLine().toUpperCase();
        takeCommon(itemToTake);
    }

    public void takeItem(String menuItem) {
        if (menuItem.startsWith("TAKE ")) {
            menuItem = menuItem.substring(5); // command was "take string"
        } else {
            menuItem = menuItem.substring(2); // command was "t string"
        }
        takeCommon(menuItem);
    }

    public void takeCommon(String itemToTake) {
        ArrayList<String> foundNames = controller.tryToDo(itemToTake, "TAKE");
        int countItems = foundNames.size();
        if (countItems == 0) {
            System.out.println(Colours.RED + "Hmm. I don't seem to see \"" + itemToTake + "\"." + Colours.RESET);
        } else if (foundNames.contains("Too heavy")) {
            System.out.println(Colours.RED + "That item is too heavy to take without dropping something else." + Colours.RESET);
        } else if (countItems == 1) {
            int firstSpace = foundNames.get(0).indexOf(" ");
            System.out.println(Colours.BLUE + "Taking the " + foundNames.get(0).substring(firstSpace + 1) + ".");
        } else {
            listMultipleNames(countItems, foundNames);
        }
        controller.updateStrengthPoints(-3);
    }

    // Overload drop item when user types only "drop"

    public void dropSomething() {
        System.out.print("Hmmm. Which item do you want to drop? ");
        String itemToDrop = input.nextLine().toUpperCase();
        dropCommon(itemToDrop);
    }


    public void dropItem(String menuItem) {
        if (menuItem.startsWith("DROP ")) {
            menuItem = menuItem.substring(5); // command was "drop string"
        } else {
            menuItem = menuItem.substring(2); // command was "d string"
        }
        dropCommon(menuItem);
    }

    public void dropCommon(String itemToDrop) {
        ArrayList<String> foundNames = controller.tryToDo(itemToDrop, "DROP");
        int countItems = foundNames.size();
        if (countItems == 0) {
            System.out.println(Colours.RED + "Hmm. I don't seem to see \"" + itemToDrop + "\"." + Colours.RESET);
        } else if (countItems == 1) {
            int firstSpace = foundNames.get(0).indexOf(" ");
            System.out.println(Colours.BLUE + "Dropping the " + foundNames.get(0).substring(firstSpace + 1) + ".");
        } else {
            listMultipleNames(countItems, foundNames);
        }
        controller.updateStrengthPoints(-2);
    }


    // Overload eatFood when user types only "eat"

    public void eatSomething() {
        System.out.print("Hmmm. Which item do you want to eat? ");
        String itemToEat = input.nextLine().toUpperCase();
        eatCommon(itemToEat);
    }

    public void eatFood(String menuItem) {
        if (menuItem.startsWith("EAT ")) {
            menuItem = menuItem.substring(4); // command was "eat string"
        } else {
            menuItem = menuItem.substring(2); // command was "f string" (food)
        }
        eatCommon(menuItem);
    }

    public void eatCommon(String itemToEat) {
        ArrayList<String> foundNames = controller.tryToDo(itemToEat, "EAT");
        int countItems = foundNames.size();
        if (countItems == 0) {
            System.out.println(Colours.RED + "Hmm. I don't seem to see \"" + itemToEat + "\"." + Colours.RESET);
        } else if (foundNames.contains("Not food")) {
            System.out.println(Colours.RED + "That item is not food." + Colours.RESET);
        } else if (countItems == 1) {
            int firstSpace = foundNames.get(0).indexOf(" ");
            System.out.println(Colours.BLUE + "Eating the " + foundNames.get(0).substring(firstSpace + 1) + ".");
        } else {
            listMultipleNames(countItems, foundNames);
        }
    }

    // Overload equipWeapon when user types only "equip" or "q"

    public void equipSomething() {
        System.out.print("Hmmm. Which item do you want to equip? ");
        String itemToEquip = input.nextLine().toUpperCase();
        equipCommon(itemToEquip);
    }

    public void equipWeapon(String menuItem) {
        if (menuItem.startsWith("EQUIP ")) {
            menuItem = menuItem.substring(6); // command was "equip string"
        } else {
            menuItem = menuItem.substring(2); // command was "q string" (equip)
        }
        equipCommon(menuItem);
    }

    public void equipCommon(String itemToEquip) {
        ArrayList<String> foundNames = controller.tryToDo(itemToEquip, "EQUIP");
        int countItems = foundNames.size();
        if (countItems == 0) {
            System.out.println(Colours.RED + "Hmm. I don't seem to see \"" + itemToEquip + "\"." + Colours.RESET);
        } else if (foundNames.contains("Not weapon")) {
            System.out.println(Colours.RED + "That item is not a weapon." + Colours.RESET);
        } else if (countItems == 1) {
            int firstSpace = foundNames.get(0).indexOf(" ");
            System.out.println(Colours.BLUE + "Equipping the " + foundNames.get(0).substring(firstSpace + 1) + ".");
        } else {
            listMultipleNames(countItems, foundNames);
        }
        controller.updateStrengthPoints(-1);
    }

    public void unequipWeapon() {
        controller.tryToDo(null, "UNEQUIP");
        System.out.println(Colours.BLUE + "Nothing equipped." + Colours.RESET);
    }

    public void listMultipleNames(int countItems, ArrayList<String> foundNames) {
        System.out.print(Colours.BLUE + "I found " + countItems + " items: " + foundNames.get(0));
        for (int i = 1; i < countItems - 1; i++) {
            System.out.print(", " + makeFirstLetterLowerCase(foundNames.get(i)));
        }
        System.out.println(" and " + makeFirstLetterLowerCase(foundNames.get(countItems - 1)) + "?");
        System.out.print("Could you give me more of a clue?\n" + Colours.RESET);
    }

    public void attackMiniGame() {
        attackEnemy();
        enemyCounterAttack();
    }

    public void attackEnemy() {
        String outcome = controller.tryToAttackEnemy();
        if (outcome.contains("No weapon")) {
            System.out.println(Colours.RED + "You cannot attack without a weapon." + Colours.RESET);
        } else if (outcome.contains("No ammo")) {
            System.out.println(Colours.RED + "You cannot shoot without ammunition." + Colours.RESET);
        } else if (outcome.contains("No enemy")) {
            System.out.println(Colours.RED + "There is no enemy to attack." + Colours.RESET);
        } else {
            System.out.println(Colours.BLUE + "Attacking...!");
            System.out.println(outcome + Colours.RESET);
        }
    }

    public void enemyCounterAttack() {
        String outcome = controller.enemyTryToCounterattack();
        if (outcome.contains("killed")) {
            System.out.println(Colours.BLUE + outcome + Colours.RESET);
        } else {
            System.out.println(Colours.BLUE + "Counterattack...!");
            System.out.println(outcome + Colours.RESET);
        }
    }
}

