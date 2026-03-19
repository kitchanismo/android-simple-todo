package com.example.myapplication;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    HomeViewModel viewModel;

    NamesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getNames().observe(this, names -> {
            adapter = new NamesAdapter(this, viewModel, names);
            binding.listView.setAdapter(adapter);
        });

        binding.btnAdd.setOnClickListener(v -> {
            if (binding.txtInput.getText().toString().trim().isEmpty()) {
                return;
            }
            viewModel.addName(binding.txtInput.getText().toString());
            binding.txtInput.setText("");
        });

        binding.txtInput.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                // Trigger button click when Enter is pressed
                binding.btnAdd.performClick();
                return true; // consume the event
            }
            return false;
        });


    }
}