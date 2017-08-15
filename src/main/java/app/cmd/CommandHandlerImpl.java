package app.cmd;

/**
 * Created by ironman on 01.08.2017.
 */
public class CommandHandlerImpl implements CommandHandler {

    private String commandNotFoundTip = "No such command exists. Type 'help' to get list of available commands.";
    private boolean quitRequested;
    private CommandParsingTools<ApplicationCommands> commandParser;

    public CommandHandlerImpl() {
        commandParser = new CommandParsingTools<>(ApplicationCommands.class);
    }

    public void handle(String command) {

        ApplicationCommands cmd = commandParser.parse(command);

        switch (cmd) {
            case QUIT: quitRequested = true;
                        break;
            case START: start();
                        break;
            case WRONG: showTip();
        }
    }

    public boolean quitRequested() {
        return quitRequested;
    }

    private void start() {

    }

    private void showTip () {
        System.out.println(commandNotFoundTip);
    }
}
