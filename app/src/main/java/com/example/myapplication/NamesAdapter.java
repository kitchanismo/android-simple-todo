package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class NamesAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;

    private HomeViewModel viewModel;

    public NamesAdapter(Context context, HomeViewModel viewModel, List<String> items) {
        super(context, R.layout.item_layout, items);
        this.context = context;
        this.items = items;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.txtTitle);
        TextInputLayout txtEdit = convertView.findViewById(R.id.txtEdit);
        TextInputEditText txtEditText = convertView.findViewById(R.id.txtEditText);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        Button btnUpdate = convertView.findViewById(R.id.btnUpdate);

        String item = items.get(position);
        textView.setText(item);

        btnDelete.setOnClickListener(v -> {
            System.out.println("position:" + position);
            viewModel.deleteName(position);
        });
        btnUpdate.setOnClickListener(v -> {
            if (btnUpdate.getText().equals("UPDATE")) {
                textView.setVisibility(View.INVISIBLE);
                btnUpdate.setText("SAVE");
                textView.setVisibility(View.INVISIBLE);
                txtEdit.setVisibility(View.VISIBLE);
                txtEditText.setVisibility(View.VISIBLE);
                txtEditText.setText(item);
            } else {
                textView.setVisibility(View.VISIBLE);
                txtEdit.setVisibility(View.INVISIBLE);
                txtEditText.setVisibility(View.INVISIBLE);
                btnUpdate.setText("UPDATE");
                String text = txtEditText.getText().toString();
                if (text.isEmpty()) {
                    return;
                }
                viewModel.updateName(position, text);
            }

        });

        return convertView;
    }


}