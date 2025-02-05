package ServerCommands;



public class QuitCommand extends ServerCommand {
    public QuitCommand() {
        super("quit");
    }

    @Override
    public boolean execute() {

        return false;
    }
}
