package seedu.address.logic.parser;

import static seedu.address.logic.messages.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HELP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.messages.NoteMessages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;

/**
 * Parses input arguments and creates a new NoteCommand object
 */
public class NoteCommandParser implements Parser<NoteCommand> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the NoteCommand
     * and returns a NoteCommand object for execution. Parameter args cannot be null.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NoteCommand parse(String args) throws ParseException {
        assert (args != null) : "argument to pass for note command is null";
        logger.log(Level.INFO, "Going to start parsing for note command.");

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_NOTE, PREFIX_DEADLINE);
        Name name;
        Note note;

        //check for unknown prefixes
        ParserUtil.verifyNoUnknownPrefix(args, NoteCommand.MESSAGE_USAGE, "note",
                PREFIX_NAME, PREFIX_NOTE, PREFIX_DEADLINE);

        ParserUtil.verifyNoMissingField(argMultimap, NoteCommand.MESSAGE_USAGE, "note",
                PREFIX_NAME, PREFIX_NOTE);

        boolean isContainingDeadlinePrefix = argMultimap.containsPrefix(PREFIX_DEADLINE);
        boolean isContainingNameNotePrefix = arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_NOTE);
        boolean isPreambleEmpty = argMultimap.isPreambleEmpty();

        if (!isContainingNameNotePrefix || !isPreambleEmpty) {
            logger.log(Level.WARNING, "Parsing error while parsing for note command.");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        }

        name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        if (!isContainingDeadlinePrefix) {
            note = ParserUtil.parseNote(argMultimap.getValue(PREFIX_NOTE).get());
        } else {
            note = ParserUtil.parseDeadlineNote(argMultimap.getValue(PREFIX_NOTE).get(),
                    argMultimap.getValue(PREFIX_DEADLINE).get());
        }
        return new NoteCommand(name, note);
    }

    //method repeated, need to abstract somewhere
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
