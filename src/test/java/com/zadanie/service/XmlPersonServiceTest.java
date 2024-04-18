package com.zadanie.service;

import com.zadanie.model.Person;
import com.zadanie.utils.XmlPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class XmlPersonServiceTest {
    private XmlPersonService service;
    private final String basePath = "src/test/resources/";

    @BeforeEach
    void setUp() {
        service = new XmlPersonService(basePath);
    }

    @Test
    void testCreate() {
        Person person = new Person("1", "Jan", "Kowalski", "500100200", "jan.kowalski@example.com", "90050512345");
        service.create(person);
        File file = new File(basePath + "Internal1.xml");
        assertTrue(file.exists());
        // Cleanup for the next test
        assertTrue(file.delete());
    }

    @Test
    void testFind() {
        // Prepare a test person
        Person person = new Person("2", "Anna", "Nowak", "600200300", "anna.nowak@example.com", "95060554321");
        service.create(person);

        Person foundPerson = service.find("2", null, null, null, null, null);
        assertNotNull(foundPerson);
        assertEquals("Anna", foundPerson.getFirstName());

        // Cleanup
        assertTrue(new File(basePath + "Internal2.xml").delete());
    }

    @Test
    void testRemove() {
        // Prepare a test person
        Person person = new Person("3", "Tomasz", "Zieliński", "700300400", "tomasz.zielinski@example.com", "92040554321");
        service.create(person);

        assertTrue(service.remove("3"));
        assertFalse(new File(basePath + "Internal3.xml").exists());
    }

    @Test
    void testModify() {
        // Prepare a test person
        Person person = new Person("4", "Katarzyna", "Maj", "800400500", "k.maj@example.com", "91020512345");
        service.create(person);

        person.setFirstName("Kasia");
        service.modify(person);

        Person modifiedPerson = service.find("4", null, null, null, null, null);
        assertNotNull(modifiedPerson);
        assertEquals("Kasia", modifiedPerson.getFirstName());

        // Cleanup
        assertTrue(new File(basePath + "Internal4.xml").delete());
    }

}