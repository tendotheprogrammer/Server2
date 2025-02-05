import ServerCommands.ServerCommand;

import java.util.Scanner;


public class ServerConsole implements Runnable {

    private static Scanner scanner = new Scanner(System.in);

    public void run() {
        ServerCommand command;
        boolean shouldContinue = true;
        do {
            String instruction = getInput();
            try {
                command = ServerCommand.create(instruction);
                shouldContinue = command.execute();
            } catch (IllegalArgumentException e) {
                System.out.println("Sorry, I did not understand '" + instruction + "'.");
                System.out.println(e.getMessage());
            }
        } while (shouldContinue);
        System.exit(0);
    }

    private static String getInput() {
        String input = "";
        do{
            input = scanner.nextLine();
        }while (input.isBlank());
        return input.strip().toLowerCase();
    }
}
