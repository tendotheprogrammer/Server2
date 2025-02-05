package ServerCommands;

public abstract class ServerCommand {

    private final String name;

    public ServerCommand(String name){
        this.name = name.toLowerCase().trim();
    }

    public String getName() {
        return this.name;
    }

    public abstract boolean execute();

    public static ServerCommand create(String instruction) {
        switch (instruction) {
            case "quit":
                return new QuitCommand();
            default:
                throw new IllegalArgumentException("Please enter a valid command");
        }
    }
}
