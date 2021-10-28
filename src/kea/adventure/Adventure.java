package kea.adventure;

public class Adventure {

    private final Controller controller = new Controller();

    public static void main(String[] args) {
        Adventure game = new Adventure();
        game.start();
    }

    public void start() {
        controller.start();
    }
}