package Main;

public class Command {
    private final String command;
    private final String argument;

    public Command(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    //Getter
    public String getCommand() {
        return command;
    }
    public String getArgument() {
        return argument;
    }
}