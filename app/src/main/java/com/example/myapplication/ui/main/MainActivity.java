package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.api.APIService;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.viewmodels.PersonViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    PersonViewModel viewModel;
    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(PersonViewModel.class);

        viewModel.getPersons().observe(this, persons -> {
            adapter = new PersonAdapter(this, viewModel);
            binding.listView.setAdapter(adapter);
        });

        binding.btnAdd.setOnClickListener(v -> {
            String input = binding.txtInput.getText().toString().trim();
            if (input.isEmpty()) return;

            viewModel.addPerson(input);
            binding.txtInput.setText("");
        });

        binding.txtInput.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.btnAdd.performClick();
                return true;
            }
            return false;
        });
        APIService api = new APIService(this);
        api.getPersons(result -> {
            viewModel.setPersons(result);
        });

    }
}
