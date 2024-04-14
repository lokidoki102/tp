package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean isShowHelp;

    /** The application should exit. */
    private final boolean isExit;

    private final boolean isShowPerson;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean isShowHelp, boolean isExit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = isShowHelp;
        this.isExit = isExit;
        this.isShowPerson = false;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code person}, {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, boolean isShowPerson) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.isShowHelp = false;
        this.isExit = false;
        this.isShowPerson = isShowPerson;
    }
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return isShowHelp;
    }

    public boolean isExit() {
        return isExit;
    }

    public boolean isShow() {
        return isShowPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && isShowHelp == otherCommandResult.isShowHelp
                && isExit == otherCommandResult.isExit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, isShowHelp, isExit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", isShowHelp)
                .add("exit", isExit)
                .toString();
    }

}
