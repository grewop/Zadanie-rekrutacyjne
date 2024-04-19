package com.zadanie;

import com.zadanie.model.Person;
import com.zadanie.model.PersonType;
import com.zadanie.service.XmlPersonService;

public class Main {
    public static void main(String[] args) {
        // Ustalamy ścieżkę do katalogu z danymi XML
        String directoryPath = "src/main/resources/";
        XmlPersonService personService = new XmlPersonService(directoryPath);

        // Przykładowe dane do dodania nowego pracownika wewnętrznego
        Person newInternalPerson = new Person("4", "Jan", "Kowalski", "500100200", "jan.kowalski@example.com", "90050512345");

        // Dodanie nowego pracownika wewnętrznego
        personService.create(newInternalPerson, PersonType.INTERNAL);

        // Wyszukiwanie istniejącego pracownika zewnętrznego
        Person foundInternalPerson = personService.find("2", null, null, null, null, null);
        if (foundInternalPerson != null) {
            System.out.println("Znaleziono pracownika zewnętrznego: " + foundInternalPerson.getFirstName() + " " + foundInternalPerson.getLastName());
        } else {
            System.out.println("Nie znaleziono pracownika zewnętrznego.");
        }

        // Wyszukiwanie dodanego pracownika wewnętrznego
        Person foundAddedInternalPerson = personService.find("4", null, null, null, null, null);
        if (foundAddedInternalPerson != null) {
            System.out.println("Znaleziono dodanego pracownika wewnętrznego: " + foundAddedInternalPerson.getFirstName() + " " + foundAddedInternalPerson.getLastName());
        } else {
            System.out.println("Nie znaleziono dodanego pracownika wewnętrznego.");
        }

        // Usunięcie pracownika wewnętrznego
        boolean isInternalRemoved = personService.remove("4", PersonType.INTERNAL);
        if (isInternalRemoved) {
            System.out.println("Pracownik wewnętrzny został usunięty.");
        } else {
            System.out.println("Nie udało się usunąć pracownika wewnętrznego.");
        }
    }
}
