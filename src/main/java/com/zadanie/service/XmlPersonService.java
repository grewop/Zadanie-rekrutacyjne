package com.zadanie.service;

import com.zadanie.model.Person;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlPersonService implements PersonService {
    private final String internalPath;
    private final String externalPath;

    public XmlPersonService(String basePath) {
        this.internalPath = Paths.get(basePath, "Internal").toString();
        this.externalPath = Paths.get(basePath, "External").toString();
    }

    @Override
    public Person find(String personId, String firstName, String lastName, String mobile, String email, String pesel) {
        List<Person> allPersons = new ArrayList<>();
        allPersons.addAll(loadPersonsFromDirectory(internalPath));
        allPersons.addAll(loadPersonsFromDirectory(externalPath));

        return allPersons.stream()
                .filter(p -> (personId == null || p.getPersonId().equals(personId)) &&
                        (firstName == null || p.getFirstName().equals(firstName)) &&
                        (lastName == null || p.getLastName().equals(lastName)) &&
                        (mobile == null || p.getMobile().equals(mobile)) &&
                        (email == null || p.getEmail().equals(email)) &&
                        (pesel == null || p.getPesel().equals(pesel)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void create(Person person, PersonType type) {
        try {
            String path = (type == PersonType.INTERNAL) ? internalPath : externalPath;
            File file = new File(path, person.getPersonId() + ".xml");
            JAXBContext context = JAXBContext.newInstance(Person.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(person, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(String personId, PersonType type) {
        String path = (type == PersonType.INTERNAL) ? internalPath : externalPath;
        File file = new File(path, personId + ".xml");
        if (file.exists()) {
            return file.delete();
        }
        file = new File(externalPath, personId + ".xml");
        return file.exists() && file.delete();
    }

    @Override
    public void modify(Person person, PersonType type) {
        remove(person.getPersonId(), type);
        create(person, type);
    }

    public List<Person> loadPersonsFromDirectory(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        List<Person> persons = new ArrayList<>();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    try {
                        persons.add(JAXB.unmarshal(file, Person.class));
                    } catch (Exception e) {
                        System.err.println("Error unmarshalling file " + file.getName() + ": " + e.getMessage());
                    }
                }
            }
        } else {
            System.err.println("Directory " + directoryPath + " not found or is empty.");
        }
        return persons;
    }
}