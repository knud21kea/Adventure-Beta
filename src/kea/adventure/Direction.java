package kea.adventure;

public enum Direction {

    NORTH("North"), EAST("East"), SOUTH("South"), WEST("West");

    String name;

    Direction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
