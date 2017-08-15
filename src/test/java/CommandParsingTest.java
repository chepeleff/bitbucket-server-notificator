import app.client.utils.ChatCommands;
import app.cmd.ApplicationCommands;
import app.cmd.CommandParsingTools;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Created by ironman on 15.08.2017.
 */
public class CommandParsingTest {
    @Test
    public void parseTest() {
        CommandParsingTools<ApplicationCommands> appParser = new CommandParsingTools<>(ApplicationCommands.class);
        CommandParsingTools<ChatCommands> chatParser = new CommandParsingTools<>(ChatCommands.class);

        String cmd1 = "start";
        String cmd2 = "sTaRt";
        String cmd3 = "QUIT";
        String cmd4 = "gospodi";
        String cmd5 = "latest   commit";

        assertEquals(appParser.parse(cmd1), ApplicationCommands.START);
        assertEquals(appParser.parse(cmd2), ApplicationCommands.START);
        assertEquals(appParser.parse(cmd3), ApplicationCommands.QUIT);
        assertEquals(appParser.parse(cmd4), ApplicationCommands.WRONG);
        assertEquals(chatParser.parse(cmd4), ChatCommands.WRONG);
        assertEquals(chatParser.parse(cmd5), ChatCommands.LATEST_COMMIT);
    }
}
