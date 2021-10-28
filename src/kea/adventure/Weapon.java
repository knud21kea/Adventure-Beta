package kea.adventure;

public abstract class Weapon extends Item {

    protected int damage;
    protected int ammo;

    public Weapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight);
        this.damage = damage;
        this.ammo = ammo;
    }

    public int getDamage() {
        return damage;
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean checkIfMelee() {
        return true;
    }

    public void shootWeapon() {
    }

    @Override
    public String toString() {
        return itemName;

    }
}