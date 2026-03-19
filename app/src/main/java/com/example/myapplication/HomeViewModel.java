package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<String>> names = new MutableLiveData<>();

    public LiveData<List<String>> getNames() {
        return names;
    }

    public void addName(String newName) {
        List<String> currentList = names.getValue();
        if (currentList == null) currentList = new ArrayList<>();
        currentList.add(0, newName);
        names.setValue(currentList);
        System.out.println("added");
    }

    public void deleteName(Integer index) {
        List<String> current = new ArrayList<>(names.getValue());
        current.remove((int) index); //add forcing to int to avoid not deleting
        names.setValue(current);
    }

    public void updateName(Integer index, String updatedName) {
        List<String> current = new ArrayList<>(names.getValue());
        current.set((int) index, updatedName);
        names.setValue(current);
    }


}