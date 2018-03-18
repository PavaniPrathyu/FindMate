package com.example.personal.three_dot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.personal.three_dot.R;

public class LoginScreen extends AppCompatActivity {
    Button submit;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        submit = (Button) findViewById(R.id.button);
        signup = (Button) findViewById(R.id.button2);
        /*submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(i);
            }
        });*/
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sign_up.class);
                startActivity(i);
            }
        });

    }
}
