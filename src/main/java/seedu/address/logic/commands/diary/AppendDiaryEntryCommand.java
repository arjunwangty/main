package seedu.address.logic.commands.diary;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.EditDiaryEntryDescriptor;

/**
 * {@link Command} that adds the new text specified by the user, and commits the data in the
 * current {@link EditDiaryEntryDescriptor} (if any) to the user diary entry.
 */
public class AppendDiaryEntryCommand extends Command {
    public static final String COMMAND_WORD = "append";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Appends and commits text to the current diary entry\n"
            + "Parameters: Text line to append\n"
            + "OR Text line with '<images (positive integer of a photo according to the gallery order)>'\n"
            + "OR <images (positive integers of photos according to the gallery order separated by spaces)>";

    private static final String MESSAGE_NO_DIARY_ENTRY = "You are not currently viewing any entry!\n";

    private static final String MESSAGE_APPEND_SUCCESS = "Appended your new line! %1$s";

    private final String textToAppend;

    public AppendDiaryEntryCommand(String textToAppend) {
        requireNonNull(textToAppend);
        this.textToAppend = textToAppend;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DiaryEntry diaryEntry = model.getPageStatus().getDiaryEntry();
        EditDiaryEntryDescriptor currentEditEntryDescriptor = model.getPageStatus().getEditDiaryEntryDescriptor();

        if (diaryEntry == null) {
            throw new CommandException(MESSAGE_NO_DIARY_ENTRY);
        }

        EditDiaryEntryDescriptor editDescriptor = currentEditEntryDescriptor == null
                ? new EditDiaryEntryDescriptor(diaryEntry)
                : currentEditEntryDescriptor;

        editDescriptor.addNewTextLine(this.textToAppend);
        DiaryEntry newDiaryEntry = editDescriptor.buildDiaryEntry();

        model.getPageStatus().getTrip().getDiary().setDiaryEntry(diaryEntry, newDiaryEntry);
        model.setPageStatus(model.getPageStatus()
                .withNewEditDiaryEntryDescriptor(null)
                .withNewDiaryEntry(newDiaryEntry));

        return new CommandResult(String.format(MESSAGE_APPEND_SUCCESS, this.textToAppend));
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj
                || (obj instanceof AppendDiaryEntryCommand
                        && ((AppendDiaryEntryCommand) obj).textToAppend.equals(textToAppend));
    }
}
