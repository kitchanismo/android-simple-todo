package com.example.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.APIService;
import com.example.myapplication.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PersonViewModel extends AndroidViewModel {
    APIService api;
    private final MutableLiveData<List<Person>> persons = new MutableLiveData<>();

    public LiveData<List<Person>> getPersons() {
        return persons;
    }

    public PersonViewModel(@NonNull Application application) {
        super(application);
        api = new APIService<Person>(application);
        loadPersons();
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

    public void loadPersons() {
        api.getList(json -> {
            // This is where you handle the "id" and "name" properties specifically for Person
            return new Person(
                    json.optString("name"),
                    json.optString("id")
            );
        }, result -> {
            persons.setValue(result);
        });
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
