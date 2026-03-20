package com.example.myapplication.ui.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemLayoutBinding;
import com.example.myapplication.models.Person;
import com.example.myapplication.viewmodels.PersonViewModel;

import java.util.ArrayList;

public class PersonAdapter extends ArrayAdapter<Person> {
    private final Context context;
    private final PersonViewModel viewModel;
    private int editingPosition = -1;

    public PersonAdapter(Context context, PersonViewModel viewModel) {
        super(context, R.layout.item_layout,
                viewModel.getPersons().getValue() != null ? new ArrayList<>(viewModel.getPersons().getValue()) : new ArrayList<>());
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

        Person item = getItem(position);
        boolean isEditing = (position == editingPosition);

        binding.txtTitle.setText(item.getName());
        binding.txtTitle.setVisibility(isEditing ? View.GONE : View.VISIBLE);
        binding.txtEdit.setVisibility(isEditing ? View.VISIBLE : View.GONE);

        if (isEditing) {
            binding.txtEditText.setText(item.getName());
            binding.txtEditText.requestFocus();
            if (binding.txtEditText.getText() != null) {
                binding.txtEditText.setSelection(binding.txtEditText.getText().length());
            }
            binding.btnUpdate.setIconResource(R.drawable.ic_save);
            binding.btnUpdate.setIconTint(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        } else {
            binding.btnUpdate.setIconResource(R.drawable.ic_edit);
            binding.btnUpdate.setIconTint(ColorStateList.valueOf(Color.parseColor("#FFC107")));
        }

        binding.btnDelete.setIconTint(ColorStateList.valueOf(Color.parseColor("#F44336")));

        binding.btnDelete.setOnClickListener(v -> {
            editingPosition = -1;
            viewModel.deletePerson(position);
        });

        binding.btnUpdate.setOnClickListener(v -> {
            if (editingPosition != position) {
                editingPosition = position;
                notifyDataSetChanged();
                return;
            }

            String updatedName = binding.txtEditText.getText().toString().trim();
            if (!updatedName.isEmpty()) {
                viewModel.updatePerson(position, updatedName);
            }

            editingPosition = -1;
            notifyDataSetChanged();
        });

        return convertView;
    }
}
