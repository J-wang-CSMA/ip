package aurora.command;

import java.io.IOException;
import java.util.ArrayList;
import aurora.objects.DukeException;
import aurora.parser.Parser;
import aurora.storage.Storage;
import aurora.tasklist.TaskList;
import aurora.ui.Ui;

/**
 * The DeleteCommand class handles the "unmark" command.
 */
public class DeleteCommand extends Command {

    /** TaskList to interact with. */
    private TaskList taskList;

    /** Ui to interact with */
    private Ui ui;

    /** Storage to interact with */
    private Storage storage;

    /** Full command */
    private String[] splitCommands;

    /**
     * Constructor for the UnmarkCommand class.
     *
     * @param taskList TaskList to edit.
     * @param ui Ui to interact with.
     * @param storage Storage to interact with.
     * @param splitCommands Full command input.
     */
    public DeleteCommand(TaskList taskList, Ui ui, Storage storage, String[] splitCommands) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
        this.splitCommands = splitCommands;
    }

    @Override
    public void handle() throws DukeException {
        if (this.splitCommands.length != 2) {
            throw new DukeException("Invalid number of arguments!\n" +
                    "Make sure to enter unmark, then the number of the task you want to delete.");
            // Solution adapted from https://www.baeldung.com/java-check-string-number
        } else if (!this.splitCommands[1].matches("-?\\d+(\\.\\d+)?")) {
            throw new DukeException("Please enter an integer as the second input.");
        } else if (Integer.parseInt(this.splitCommands[1]) <= 0) {
            throw new DukeException("Please enter an integer greater than 0 as the second input.");
        } else if (Integer.parseInt(this.splitCommands[1]) > this.taskList.getTaskList().size()) {
            throw new DukeException("Please enter an integer representing a task within the list.");
        } else {
            int taskIndex = Integer.parseInt(splitCommands[1]);
            this.ui.printALine();
            this.taskList.deleteTask(taskIndex - 1);
            this.ui.printALine();
        }
        try {
            this.storage.saveTasks(this.taskList.getTaskList());
        } catch (IOException exception) {
            System.out.println("Unable to save edits: " + exception.getMessage());
        }
    }

    @Override
    public boolean isBye() {
        return false;
    }

}
