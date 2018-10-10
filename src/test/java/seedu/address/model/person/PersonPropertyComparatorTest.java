package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PersonPropertyComparatorTest {
    private PersonPropertyComparator nameComparator = PersonPropertyComparator.NAME;
    private PersonPropertyComparator phoneComparator = PersonPropertyComparator.PHONE;
    private PersonPropertyComparator emailComparator = PersonPropertyComparator.EMAIL;
    private PersonPropertyComparator addressComparator = PersonPropertyComparator.ADDRESS;

    @Test
    public void execute_getComparator() {
        // lower case
        assertEquals(nameComparator, PersonPropertyComparator.getPersonPropertyComparator("name"));
        assertEquals(phoneComparator, PersonPropertyComparator.getPersonPropertyComparator("phone"));
        assertEquals(emailComparator, PersonPropertyComparator.getPersonPropertyComparator("email"));
        assertEquals(addressComparator, PersonPropertyComparator.getPersonPropertyComparator("address"));

        // upper case
        assertEquals(nameComparator, PersonPropertyComparator.getPersonPropertyComparator("NAME"));
        assertEquals(phoneComparator, PersonPropertyComparator.getPersonPropertyComparator("PHONE"));
        assertEquals(emailComparator, PersonPropertyComparator.getPersonPropertyComparator("EMAIL"));
        assertEquals(addressComparator, PersonPropertyComparator.getPersonPropertyComparator("ADDRESS"));

        // case insensitive
        assertEquals(nameComparator, PersonPropertyComparator.getPersonPropertyComparator("NamE"));
        assertEquals(phoneComparator, PersonPropertyComparator.getPersonPropertyComparator("pHoNe"));
        assertEquals(emailComparator, PersonPropertyComparator.getPersonPropertyComparator("EmAil"));
        assertEquals(addressComparator, PersonPropertyComparator.getPersonPropertyComparator("ADdReSS"));

        // misspellings
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("ame"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("pone"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("emai"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("addess"));

        // multiple arguments
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("name phone"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("phone email"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("email address"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("address name"));

        // empty arguments
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator(""));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator(" "));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("\n"));
        assertThrows(IllegalArgumentException.class, () ->
            PersonPropertyComparator.getPersonPropertyComparator("\t"));
    }

    @Test
    public void execute_toString() {
        assertEquals(nameComparator.toString(), "name");
        assertEquals(phoneComparator.toString(), "phone");
        assertEquals(emailComparator.toString(), "email");
        assertEquals(addressComparator.toString(), "address");
    }
}
