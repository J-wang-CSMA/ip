package aurora.command;

import java.io.IOException;
import java.util.ArrayList;
import aurora.objects.DukeException;
import aurora.parser.Parser;
import aurora.storage.Storage;
import aurora.tasklist.TaskList;
import aurora.ui.Ui;

/**
 * The TodoCommand class handles the "todo" command.
 */
public class TodoCommand extends Command {

    /** TaskList to interact with. */
    private TaskList taskList;

    /** Ui to interact with. */
    private Ui ui;

    /** Storage to interact with. */
    private Storage storage;

    /** Full command. */
    private String command;

    /**
     * Constructor for the TodoCommand class.
     *
     * @param taskList TaskList to edit.
     * @param ui Ui to interact with.
     * @param storage Storage to interact with.
     * @param command Full command input.
     */
    public TodoCommand(TaskList taskList, Ui ui, Storage storage, String command) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
        this.command = command;
    }

    @Override
    public void handle() throws DukeException {
        String[] descriptionSplit = Parser.splitAtFirstBlank(this.command);
        if (descriptionSplit.length < 2) {
            throw new DukeException("Invalid number of arguments!\n" +
                    "Make sure to enter todo, then specify the task.");
        } else {
            this.taskList.addTodo(descriptionSplit[1]);
            this.ui.echoAddTask(this.taskList);
        }
        try {
            this.storage.saveTasks(this.taskList.getTaskList());
        } catch (IOException exception) {
            System.out.println("Unable to save todo to file: " + exception.getMessage());
        }
    }

    @Override
    public boolean isBye() {
        return false;
    }

}
