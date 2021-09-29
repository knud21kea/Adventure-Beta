package kea.adventure;

public class Item {

    private String itemName;
    private int itemWeight;

    public Item(String itemName, int itemWeight) {
        this.itemName = itemName;
        this.itemWeight = itemWeight;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemWeight(int itemWeight) {
        this.itemWeight = itemWeight;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemWeight() {
        return itemWeight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemWeight=" + itemWeight +
                '}';
    }
}