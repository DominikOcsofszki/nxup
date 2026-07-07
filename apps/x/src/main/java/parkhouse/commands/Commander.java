package parkhouse.commands;

import java.util.ArrayList;
import java.util.List;

public class Commander {

    private final List<ICommand> commandList = new ArrayList<>();
    private int cursor = 0;

    /*
    Author: jstueh2s
     */

    public void queue(ICommand cmd) {
        commandList.add(cmd);
    }

    public void activate() {
        if (cursor < commandList.size()) {
            commandList.get(cursor++).execute();
        }
    }

    public void undo() {
        if (cursor > 0) {
            commandList.get(--cursor).undo();
            commandList.remove(cursor);
        }
    }

}
