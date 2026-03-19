package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.ItemLayoutBinding;

import java.util.List;

public class NamesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final HomeViewModel viewModel;
    private int editingPosition = -1;

    public NamesAdapter(Context context, HomeViewModel viewModel, List<String> items) {
        super(context, R.layout.item_layout, items);
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemLayoutBinding binding;

        if (convertView == null) {
            // Use View Binding to inflate the layout
            binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
            convertView = binding.getRoot();
            // Store the binding object in the tag (replaces the ViewHolder)
            convertView.setTag(binding);
        } else {
            // Retrieve the existing binding object
            binding = (ItemLayoutBinding) convertView.getTag();
        }

        String item = getItem(position);
        boolean isEditing = (position == editingPosition);

        // UI Setup
        binding.txtTitle.setText(item);
        binding.txtTitle.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        binding.txtEdit.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        binding.txtEditText.setVisibility(isEditing ? View.VISIBLE : View.GONE);
        binding.btnUpdate.setText(isEditing ? "SAVE" : "UPDATE");

        if (isEditing) {
            binding.txtEditText.setText(item);
            binding.txtEditText.requestFocus();
            if (binding.txtEditText.getText() != null) {
                binding.txtEditText.setSelection(binding.txtEditText.getText().length());
            }
        }

        binding.btnDelete.setOnClickListener(v -> {
            editingPosition = -1;
            viewModel.deleteName(position);
        });

        binding.btnUpdate.setOnClickListener(v -> {
            if (editingPosition != position) {
                // Enter Edit Mode
                editingPosition = position;
            } else {
                // Save logic
                String updatedName = binding.txtEditText.getText().toString().trim();
                if (!updatedName.isEmpty()) {
                    viewModel.updateName(position, updatedName);
                }
                editingPosition = -1;
            }
            // Refresh the UI regardless of which branch was taken
            notifyDataSetChanged();
        });
        return convertView;
    }
}
