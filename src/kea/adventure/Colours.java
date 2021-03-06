package kea.adventure;

enum Colours {

    RESET("\033[0m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    CYAN("\033[0;36m"),
    YELLOW_UNDERLINED("\033[4;33m");

    String code;

    Colours(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
