package com.example.myapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.api.APIService;
import com.example.myapplication.models.Person;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PersonViewModel extends AndroidViewModel {
    APIService<Person> api;
    private final MutableLiveData<List<Person>> persons = new MutableLiveData<>();

    public LiveData<List<Person>> getPersons() {
        return persons;
    }

    public PersonViewModel(@NonNull Application application) {
        super(application);
        api = new APIService<Person>(application);
        loadPersons();
    }

    public void addPerson(String name) {

        try {
            JSONObject body = new JSONObject();
            body.put("name", name);

            api.postItem("persons", body,
                    json -> new Person(json.optString("name"), json.optString("id")),
                    newPerson -> {
                        List<Person> currentList = persons.getValue();
                        if (currentList == null) currentList = new ArrayList<>();
                        currentList.add(0, newPerson);
                        persons.setValue(currentList);
                    }
            );
        } catch (org.json.JSONException e) {
            e.printStackTrace();
            // Handle the error (e.g., show a message to the user)
        }
    }

    public void setPersons(List<Person> names) {
        persons.setValue(names);
    }

    public void loadPersons() {
        api.getList("persons", json -> {
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
        api.deleteItem("persons/" + id, () -> {
            List<Person> current = persons.getValue();
            if (current == null) return;
            List<Person> newList = new ArrayList<>(current);
            newList.removeIf(p -> p.getId().equals(id));
            persons.setValue(newList);
        });
    }

    public void updatePerson(Person person) {
        try {
            JSONObject body = new JSONObject();
            body.put("name", person.getName());

            api.updateItem("persons/" + person.getId(), body,
                    json -> new Person(json.optString("name"), json.optString("id")),
                    updatedPerson -> {
                        List<Person> current = new ArrayList<>(persons.getValue());
                        IntStream.range(0, current.size())
                                .filter(i -> current.get(i).getId().equals(updatedPerson.getId()))
                                .findFirst()
                                .ifPresent(i -> current.set(i, updatedPerson));
                        persons.setValue(current);
                    }
            );
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }
}
