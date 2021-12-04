package Game;

import java.util.Arrays;

public class CommandWords {
    private static final String[] validCommands = {
            "move", "pass","quit", "state"
    };
    public void printCommands(){
        System.out.println(Arrays.deepToString(validCommands));
    }

    public CommandWords()
    {
    }

    public boolean isCommand(String aString)
    {
        /**
         * @author      John Afolayan
         *
         * This method checks a string passed from the parser, to see if it is a valid command.
         *
         */
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false;
    }
}
