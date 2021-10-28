package kea.adventure;

public enum Health {

    DEAD("DEAD", Colours.RED + "You died. You really should have rested a while!!" + Colours.RESET),
    EXHAUSTED("ALIVE", Colours.RED + "You are exhausted, you really should rest a while!" + Colours.RESET),
    TIRED("ALIVE", Colours.YELLOW + "You are getting tired, maybe you should rest a while." + Colours.RESET),
    WEARY("ALIVE", Colours.YELLOW + "You are a bit weary." + Colours.RESET),
    FRESH("ALIVE", Colours.GREEN + "You are reasonably fresh." + Colours.RESET),
    RESTED("ALIVE", Colours.GREEN + "You are fully rested." + Colours.RESET);

    private final String health;
    private final String message;

    Health(String health, String message) {
        this.health = health;
        this.message = message;
    }

    String getStateHealth() {
        return this.health;
    }

    public String getStateMessage() {
        return this.message;
    }
}
