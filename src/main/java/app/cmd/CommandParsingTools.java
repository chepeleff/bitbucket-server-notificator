package app.cmd;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ironman on 15.08.2017.
 */
public class CommandParsingTools<T extends Commands> {

    private Class<T> clazz;
    private Map<String, T> commandsMap = new HashMap<>();

    public CommandParsingTools(Class commandsEnum) {
        // only enums can be used here
        if (!commandsEnum.isEnum()) {
            throw new InvalidParameterException("Only enum classes are available as a constructor parameter");
        }
        this.clazz = commandsEnum;

        Arrays.stream(clazz.getEnumConstants()).forEach(cmd -> commandsMap.put(cmd.toString().toLowerCase(), (T) cmd));

        // there must be a WRONG constant in the passed enum:
        if (commandsMap.get("wrong") == null) {
            throw new InvalidParameterException("Passed enum must have one constant named 'WRONG'");
        }
    }

    /**
     * @param rawCommand
     * @return
     */
    public T parse(String rawCommand) {
        rawCommand = rawCommand.toLowerCase().replaceAll("\\s+", "_");
        T resolved = commandsMap.get(rawCommand);
        T wrongCmd = commandsMap.get("wrong");
        return resolved == null ? wrongCmd : resolved;
    }


}
