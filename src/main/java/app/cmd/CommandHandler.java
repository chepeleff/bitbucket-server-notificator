package app.cmd;

/**
 * Created by ironman on 01.08.2017.
 */
public interface CommandHandler {
    void handle(String command);
    boolean quitRequested();
}
