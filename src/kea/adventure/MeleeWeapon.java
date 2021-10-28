package kea.adventure;

public class MeleeWeapon extends Weapon {

    public MeleeWeapon(String itemName, int itemWeight, int damage, int ammo) {
        super(itemName, itemWeight, damage, ammo);
    }

    @Override
    public boolean checkIfMelee() {
        return true;
    }

    @Override
    public void shootWeapon() {
    }
}

