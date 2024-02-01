package aurora.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deadline is a task with a specified date set as the deadline.
 */
public class Deadline extends Task{
    /** Date at which the deadline expires */
    private LocalDateTime date;

    /**
     * Constructor of deadline
     *
     * @param description: Description of task.
     * @param date: Date at which the deadline expires.
     */
    public Deadline(String description, LocalDateTime date) {
        super(description);
        this.date = date;
    }

    /**
     * Getter for date.
     *
     * @return The deadline.
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Formats the local datetime to string.
     *
     * @return Date in String format
     */
    private String dateToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return date.format(formatter);
    }

    @Override
    public String toFileString() {
        String taskType = "D";
        String isDone = this.getStatus() ? "1" : "0";
        String description = super.toFileString();
        String formattedDate = dateToString();
        return taskType + " | " + isDone + " | " + description + " | " + formattedDate;
    }

    @Override
    public String toString() {
        String taskType = "[D]";
        String formattedDate = dateToString();
        String deadlineString = taskType + super.toString() + " (by: " + formattedDate + ")";
        return deadlineString;
    }
}
