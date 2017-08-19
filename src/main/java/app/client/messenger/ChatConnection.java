package app.client.messenger;

/**
 * Created by ironman on 13.08.2017.
 */
public interface ChatConnection {
    String readMessage();
    void authenticate();
}
