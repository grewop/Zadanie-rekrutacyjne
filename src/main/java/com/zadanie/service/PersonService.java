package com.zadanie.service;

import com.zadanie.model.Person;

public interface PersonService {
    Person find(String personId, String firstName, String lastName, String mobile, String email, String pesel);
    void create(Person person);
    boolean remove(String personId);
    void modify(Person person);
}