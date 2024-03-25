package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.PinMessages;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Pins a contact on the address book.
 */
public class PinCommand extends Command {
    public static final String COMMAND_WORD = "/pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins a contact to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe other ";

    private final Name name;

    /**
     * @param name of the person in the person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public PinCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToPin = model.findByName(name);
        personToPin.toPin();
        model.updatePinnedPersonList();

        return new CommandResult(String.format(PinMessages.MESSAGE_PIN_PERSON_SUCCESS,
                PinMessages.format(personToPin)));
    }
}
