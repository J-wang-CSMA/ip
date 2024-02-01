package aurora.command;

import aurora.objects.DukeException;
import aurora.objects.Task;
import aurora.storage.Storage;
import aurora.tasklist.TaskList;
import aurora.ui.Ui;
import java.util.ArrayList;

/**
 * The FindCommand class handles the "find" command.
 */
public class FindCommand extends Command {
    /** TaskList to interact with. */
    private TaskList taskList;

    /** Ui to interact with. */
    private Ui ui;

    /** Storage to interact with. */
    private Storage storage;

    /** Full command. */
    private String[] splitCommands;

    /** List of commands found */
    private ArrayList<Task> foundList;

    /**
     * Constructor for the FindCommand class.
     *
     * @param taskList TaskList to interact with.
     * @param ui Ui to interact with.
     * @param storage Storage to interact with.
     * @param splitCommands Full command from the input.
     */
    public FindCommand(TaskList taskList, Ui ui, Storage storage, String[] splitCommands) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
        this.splitCommands = splitCommands;
        this.foundList = new ArrayList<>();
    }

    @Override
    public void handle() throws DukeException {
        if (this.splitCommands.length != 2) {
            throw new DukeException("Invalid number of arguments!\n" +
                    "Make sure to enter find, then the keyword you wish to search for in the task list.");
        } else {
            String keyword = this.splitCommands[1].toLowerCase();
            ArrayList<Task> tasks = this.taskList.getTaskList();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                String description = task.getDescription().toLowerCase();
                if (description.contains(keyword)) {
                    this.foundList.add(task);
                }
            }
            this.ui.printFoundList(this.foundList);
        }
    }

    @Override
    public boolean isBye() {
        return false;
    }
}
