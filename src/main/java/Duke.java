import command.ByeCommand;
import command.DeadlineCommand;
import command.DeleteCommand;
import command.EventCommand;
import command.InvalidCommand;
import command.ListCommand;
import command.MarkCommand;
import command.TodoCommand;
import command.UnmarkCommand;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import objects.Deadline;
import objects.DukeException;
import objects.Event;
import objects.Task;
import objects.Todo;
import parser.Parser;
import storage.Storage;
import tasklist.TaskList;
import ui.Ui;

/**
 * Duke is the main class for the application that runs according to the commands given to it by the user.
 */
public class Duke {
    /**
     * Ui for the application.
     */
    private Ui ui;

    /**
     * TaskList for the application.
     */
    private TaskList taskList;

    /**
     * Storage for the application.
     */
    private Storage storage;

    /**
     * Constructor for the Duke class
     */
    private Duke() {
        this.ui = new Ui();
        this.storage = new Storage("./data/duke.txt");
        try {
            ArrayList<Task> taskList = storage.loadTasks();
            this.taskList = new TaskList(taskList);
        } catch (IOException exception) {
            System.out.println("Error loading tasks from file: " + exception.getMessage());
            ArrayList<Task> emptyTaskList = new ArrayList<>();
            this.taskList = new TaskList(emptyTaskList);
        } catch (DukeException exception) {
            String exceptionMessage = exception.getExceptionMessage();
            this.ui.printALine();
            System.out.println(exceptionMessage);
            this.ui.printALine();
            ArrayList<Task> emptyTaskList = new ArrayList<>();
            this.taskList = new TaskList(emptyTaskList);
        }
    }
    public static void main(String[] args) {
        Duke aurora1 = new Duke();
        aurora1.exeAurora();
    }

    /**
     * Method for the execution of the application.
     */
    public void exeAurora() {
        this.ui.printOpeningMessage();
        boolean exit = false;
        while(!exit) {
            String command = this.ui.nextCommand();
            String[] splitCommands = Parser.splitAtAllBlanks(command);
            String mainC = splitCommands[0];
            try {
                if (mainC.equalsIgnoreCase("bye")) {
                    ByeCommand byeCommand = new ByeCommand(this.taskList, this.ui, this.storage);
                    exit = byeCommand.isBye();
                    byeCommand.handle();
                } else if (mainC.equalsIgnoreCase("list")) {
                    ListCommand listCommand = new ListCommand(this.taskList, this.ui, this.storage);
                    listCommand.handle();
                } else if (mainC.equalsIgnoreCase("mark")) {
                    MarkCommand markCommand = new MarkCommand(this.taskList, this.ui, this.storage, splitCommands);
                    markCommand.handle();
                } else if (mainC.equalsIgnoreCase("unmark")) {
                    UnmarkCommand unmarkCommand = new UnmarkCommand(this.taskList, this.ui, this.storage,
                            splitCommands);
                    unmarkCommand.handle();
                } else if (mainC.equalsIgnoreCase("todo")) {
                    TodoCommand todoCommand = new TodoCommand(this.taskList, this.ui, this.storage, command);
                    todoCommand.handle();
                } else if (mainC.equalsIgnoreCase("deadline")) {
                    DeadlineCommand deadlineCommand = new DeadlineCommand(this.taskList, this.ui, this.storage,
                            command);
                    deadlineCommand.handle();
                } else if (mainC.equalsIgnoreCase("event")) {
                    EventCommand eventCommand = new EventCommand(this.taskList, this.ui, this.storage, command);
                    eventCommand.handle();
                } else if (mainC.equalsIgnoreCase("delete")) {
                    DeleteCommand deleteCommand = new DeleteCommand(this.taskList, this.ui, this.storage,
                            splitCommands);
                    deleteCommand.handle();
                } else {
                    InvalidCommand invalidCommand = new InvalidCommand();
                    invalidCommand.handle();
                }
            } catch (DukeException exception) {
                String exceptionMessage = exception.getExceptionMessage();
                this.ui.printALine();
                System.out.println(exceptionMessage);
                this.ui.printALine();
            }
        }
    }
}
