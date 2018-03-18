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

public class options2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FloatingActionButton fb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options2);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Athletics");
        categories.add("Archery");
        categories.add("Badminton");
        categories.add("Basketball");
        categories.add("Baseball");
        categories.add("Boxing");
        categories.add("Cricket");
        categories.add("Cycling");
        categories.add("Diving");
        categories.add("Football");
        categories.add("Golf");
        categories.add("Hockey");
        categories.add("Horse Racing");
        categories.add("Judo");
        categories.add("Motor Sport");
        categories.add("Netball");
        categories.add("Rugby");
        categories.add("Sailing");
        categories.add("Shooting");
        categories.add("Snooker");
        categories.add("Swimming");
        categories.add("Table Tennis");
        categories.add("Tennis");
        categories.add("VolleyBall");
        categories.add("Wrestling");
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
