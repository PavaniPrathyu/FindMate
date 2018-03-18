package com.example.personal.three_dot;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personal.three_dot.R;

import java.util.ArrayList;
import java.util.List;

public class options extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FloatingActionButton fb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Engineering");
        categories.add("Medical");
        categories.add("Agricultural");
        categories.add("Manufacturing and Construction");
        categories.add("Natural Science");
        categories.add("Mathematics and Statistics");
        categories.add("Arts and Humanities");
        categories.add("Business");
        categories.add("Law");
        categories.add("Information and Communication Technologies");
        categories.add("Agriculture");
        categories.add("Food");
        categories.add("Journalism");
        categories.add("Social Science");
        categories.add("Others");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        fb1=findViewById(R.id.fb1);


    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
