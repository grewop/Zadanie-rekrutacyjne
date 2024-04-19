package com.zadanie.service;

import com.zadanie.model.Person;

public interface PersonService {
    Person find(String personId, String firstName, String lastName, String mobile, String email, String pesel);
    void create(Person person, PersonType type);
    boolean remove(String personId, PersonType type);
    void modify(Person person, PersonType type);
}