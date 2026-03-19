package com.example.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.ItemLayoutBinding;

import java.util.ArrayList;

public class NamesAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final HomeViewModel viewModel;
    private int editingPosition = -1;

    public NamesAdapter(Context context, HomeViewModel viewModel) {
        super(context, R.layout.item_layout, new ArrayList<>(viewModel.getNames().getValue()));
        this.context = context;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemLayoutBinding binding;

        if (convertView == null) {
            binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemLayoutBinding) convertView.getTag();
        }

        String item = getItem(position);
        boolean isEditing = (position == editingPosition);

        // UI Setup
        binding.txtTitle.setText(item);
        binding.txtTitle.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        binding.txtEdit.setVisibility(isEditing ? View.VISIBLE : View.GONE);

        // Dynamic Icon and Color for Update/Save button
        if (isEditing) {
            if (binding.txtEditText.getText() != null) {
                binding.txtEditText.setSelection(binding.txtEditText.getText().length());
            }
            binding.txtEditText.setText(item);
            binding.txtEditText.requestFocus();
            binding.btnUpdate.setIconResource(R.drawable.ic_save);
            binding.btnUpdate.setIconTint(ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // Green
        } else {
            binding.btnUpdate.setIconResource(R.drawable.ic_edit);
            binding.btnUpdate.setIconTint(ColorStateList.valueOf(Color.parseColor("#FFC107"))); // Yellow/Amber
        }

        // Static Color for Delete button
        binding.btnDelete.setIconTint(ColorStateList.valueOf(Color.parseColor("#F44336"))); // Red

        binding.btnDelete.setOnClickListener(v -> {
            editingPosition = -1;
            viewModel.deleteName(position);
        });

        binding.btnUpdate.setOnClickListener(v -> {
            if (editingPosition != position) {
                editingPosition = position;
                notifyDataSetChanged();
                return;
            }

            String updatedName = binding.txtEditText.getText().toString().trim();
            if (!updatedName.isEmpty()) {
                viewModel.updateName(position, updatedName);
            }

            editingPosition = -1;
            notifyDataSetChanged();
        });

        return convertView;
    }
}
