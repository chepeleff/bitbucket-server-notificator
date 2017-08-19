package app.client.utils;

import app.client.messenger.ChatConnection;

/**
 * Created by ironman on 13.08.2017.
 */
public class ChatDaemon extends Thread {
    private String prefix;
    private ChatCommandHandler commandHandler;
    private ChatConnection listener;

    public ChatDaemon(String prefix, ChatCommandHandler commandHandler, ChatConnection listener) {
        super();
        this.prefix = prefix;
        this.commandHandler = commandHandler;
        this.listener = listener;
    }

    @Override
    public void start() {
        setDaemon(true);

        while (true) {
            String message = listener.readMessage();

        }
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
