package kea.adventure;

public class Enemy {

    private final Weapon enemyWeapon;
    private final String enemyName;
    private int health;

    public Enemy(String enemyName, int health, Weapon weapon) {
        this.enemyName = enemyName;
        this.health = health;
        this.enemyWeapon = weapon;
    }

    public String getEnemyName() {
        return this.enemyName;
    }

    public Weapon getEnemyWeapon() {
        return this.enemyWeapon;
    }

    public String getWeaponName() {
        return this.enemyWeapon.getItemName();
    }

    public int getWeaponDamage() {
        return this.enemyWeapon.getDamage();
    }

    public int getEnemyHealth() {
        return this.health;
    }

    public void changeHealth(int healthPoint) {
        this.health += healthPoint;
    }

    @Override
    public String toString() {
        return "Other lifeforms on this planet: " + enemyName;
    }
}



