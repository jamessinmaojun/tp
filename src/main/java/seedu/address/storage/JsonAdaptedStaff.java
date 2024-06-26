package seedu.address.storage;

import seedu.address.model.person.Person;
import seedu.address.model.person.Staff;

//@@author chiageng
/**
 * Jackson-friendly version of {@link Staff}.
 */
class JsonAdaptedStaff extends JsonAdaptedPerson {
    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedStaff(Person source) {
        super(source);
        Staff staff = (Staff) source;

        setSalary(staff.getSalary().value);
        setEmployment(staff.getEmployment().value);

    }
}
