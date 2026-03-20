package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonViewModel extends ViewModel {

    private MutableLiveData<List<Person>> persons = new MutableLiveData<>();

    public LiveData<List<Person>> getPersons() {
        return persons;
    }

    public void addPerson(String name) {
        List<Person> currentList = persons.getValue();
        if (currentList == null) currentList = new ArrayList<>();
        currentList.add(0, new Person(name, String.valueOf(System.currentTimeMillis())));
        persons.setValue(currentList);
    }

    public void setPersons(List<Person> names) {
        persons.setValue(names);
    }

    public void deletePerson(int index) {
        List<Person> current = new ArrayList<>(persons.getValue());
        current.remove(index);
        persons.setValue(current);
    }

    public void updatePerson(int index, String updatedName) {
        List<Person> current = new ArrayList<>(persons.getValue());
        Person p = current.get(index);
        current.set(index, new Person(updatedName, p.getId()));
        persons.setValue(current);
    }
}
