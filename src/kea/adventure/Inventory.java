package kea.adventure;

public class Inventory {

    // Create all instances of Item class
    // Some names have duplicates to test the parser - key, gold, silver etc.

    public Item brassLamp = new Item("A brass lamp", 15);
    public Item diamondRing = new Item("A diamond ring", 5);
    public Item emptyBottle = new Item("An empty bottle", 5);
    public Item silverKey = new Item("A silver key", 1);
    public Item silverCoin = new Item("A silver coin", 1);
    public Item goldKey = new Item("A gold key", 1);
    public Item goldBar = new Item("A gold bar", 20);
    public Item holyWater = new Item("Some holy water", 10);
    public Item oldParchment = new Item("An old parchment", 2);
    public Item magneticCompass = new Item("A magnetic compass", 5);
    public Item boxOfMatches = new Item("A box of matches", 3);
    public Item paperClip = new Item("A paper clip", 0);

    public Food anApple = new Food("A green apple", 5, 10);
    public Food aPoisonApple = new Food("A red apple", 5, -10);
    public Food aPear = new Food("A ripe pear", 5, 5);

    public Weapon knife = new MeleeWeapon("A blunt knife", 5, 1, 1);
    public Weapon dagger = new MeleeWeapon("A sharp dagger", 5, 5, 1);
    public Weapon sword = new MeleeWeapon("A sword", 10, 15, 1);
    public Weapon axe = new MeleeWeapon("An axe", 15, 25, 1);
    public Weapon bow = new ShootingWeapon("A bow", 5, 10, 3);

    public Enemy orc = new Enemy("An ugly orc", 50, axe);
    public Enemy elf = new Enemy("A dark elf", 60, bow);
    public Enemy goblin = new Enemy("A small goblin", 40, dagger);

    public Inventory() {
    }
}
