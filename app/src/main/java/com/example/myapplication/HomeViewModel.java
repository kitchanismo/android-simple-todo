package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Person>> names = new MutableLiveData<>();

    public LiveData<List<Person>> getPersons() {
        return names;
    }

    public void setNames(List<Person> names) {
        this.names.setValue(names);
    }

    public void addName(Person person) {
        List<Person> currentList = names.getValue();
        if (currentList == null) currentList = new ArrayList<>();
        currentList.add(0, person);
        names.setValue(currentList);
        System.out.println("created");
    }

    public void deleteName(String id) {
        List<Person> current = new ArrayList<>(names.getValue());
        current.removeIf(p -> p.getId().equals(id));
        names.setValue(current);
    }

    public void updateName(String id, Person person) {
        List<Person> current = new ArrayList<>(names.getValue());

        IntStream.range(0, current.size())
                .filter(i -> current.get(i).getId().equals(id))
                .findFirst()
                .ifPresent(i -> current.set(i, person));

        names.setValue(current);
    }


}