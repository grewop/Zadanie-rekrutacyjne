package com.zadanie;

import com.zadanie.model.Person;
import com.zadanie.service.PersonService;
import com.zadanie.service.XmlPersonService;

public class Main {
    public static void main(String[] args) {
        // Ustalamy ścieżkę do katalogu z danymi XML
        String directoryPath = "src/main/resources/";
        PersonService personService = new XmlPersonService(directoryPath);

        // Przykładowe dane do dodania nowego pracownika
        Person newPerson = new Person("4", "Jan", "Kowalski", "500100200", "jan.kowalski@example.com", "90050512345");

        // Dodanie nowego pracownika
        personService.create(newPerson);

        // Wyszukiwanie pracownika istniejącego
        Person foundPerson = personService.find("1", null, null, null, null, null);
        if (foundPerson != null) {
            System.out.println("Znaleziono pracownika: " + foundPerson.getFirstName() + " " + foundPerson.getLastName());
        } else {
            System.out.println("Nie znaleziono pracownika.");
        }
        // Wyszukiwanie pracownika dodanego
        Person foundPerson2 = personService.find("4", null, null, null, null, null);
        if (foundPerson2 != null) {
            System.out.println("Znaleziono pracownika: " + foundPerson2.getFirstName() + " " + foundPerson2.getLastName());
        } else {
            System.out.println("Nie znaleziono pracownika.");
        }

        // Usunięcie pracownika
        boolean isRemoved = personService.remove("4");
        if (isRemoved) {
            System.out.println("Pracownik został usunięty.");
        } else {
            System.out.println("Nie udało się usunąć pracownika.");
        }
    }
}
