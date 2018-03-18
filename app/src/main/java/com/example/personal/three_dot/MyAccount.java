package com.example.personal.three_dot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.personal.three_dot.R;

public class MyAccount extends AppCompatActivity {
//TextView Name_at_account;
Button editing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        editing = (Button) findViewById(R.id.EditDetails);
        editing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EditDetails.class);
                startActivity(i);
            }
        });
    }
}
