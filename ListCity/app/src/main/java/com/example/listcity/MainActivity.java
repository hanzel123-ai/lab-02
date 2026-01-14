package com.example.listcity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button btnAdd;
    Button btnDelete;

    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        btnAdd = findViewById(R.id.btn_add);
        btnDelete = findViewById(R.id.btn_delete);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this,R.layout.content,dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        btnDelete.setEnabled(false);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
            btnDelete.setEnabled(true);
        });

        btnAdd.setOnClickListener(v -> showAddCityDialog());

        btnDelete.setOnClickListener(v -> deleteSelectedCity());
    }

    private void showAddCityDialog() {
        final EditText input = new EditText(this);
        input.setHint("Enter city name");

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(input)
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String city = input.getText().toString().trim();

                    if (city.isEmpty()) {
                        Toast.makeText(this, "City name can't be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (String c : dataList) {
                        if (c.equalsIgnoreCase(city)) {
                            Toast.makeText(this, "City already exists", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    dataList.add(city);
                    cityAdapter.notifyDataSetChanged();
                })
                .show();
    }

    private void deleteSelectedCity() {
        if (selectedIndex >= 0 && selectedIndex < dataList.size()) {
            dataList.remove(selectedIndex);
            cityAdapter.notifyDataSetChanged();

            selectedIndex = -1;
            cityList.clearChoices();
            btnDelete.setEnabled(false);
        } else {
            Toast.makeText(this, "Select a city first", Toast.LENGTH_SHORT).show();
        }
    }
}
