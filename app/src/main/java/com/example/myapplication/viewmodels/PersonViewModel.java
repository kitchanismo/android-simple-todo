package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PersonViewModel extends ViewModel {

    private final MutableLiveData<List<Person>> persons = new MutableLiveData<>();

    public LiveData<List<Person>> getPersons() {
        return persons;
    }

    public void addPerson(Person person) {
        List<Person> currentList = persons.getValue();
        if (currentList == null) currentList = new ArrayList<>();
        currentList.add(0, person);
        persons.setValue(currentList);
    }

    public void setPersons(List<Person> names) {
        persons.setValue(names);
    }

    public void deletePerson(String id) {
        List<Person> person = new ArrayList<>(persons.getValue());
        person.removeIf(p -> p.getId().equals(id));
        persons.setValue(person);
    }

    public void updatePerson(Person person) {
        List<Person> current = new ArrayList<>(persons.getValue());
        IntStream.range(0, current.size())
                .filter(i -> current.get(i).getId().equals(person.getId()))
                .findFirst()
                .ifPresent(i -> {
                    Person p = current.get(i);
                    current.set(i, person);
                });
        persons.setValue(current);
    }
}
