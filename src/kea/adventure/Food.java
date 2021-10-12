package kea.adventure;

public class Food extends Item {

    private int foodValue;

    public Food(String itemName, int itemWeight, int foodValue) {
        super(itemName, itemWeight);
        this.foodValue = foodValue;
    }

    public int getfoodValue() {
        return foodValue;
    }

    public void setfoodValue(int health) {
        this.foodValue = foodValue;
    }
}