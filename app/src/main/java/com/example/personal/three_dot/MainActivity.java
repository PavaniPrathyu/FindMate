package com.example.personal.three_dot;
import com.microsoft.windowsazure.mobileservices.*;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.personal.three_dot.R;

public class MainActivity extends AppCompatActivity {
    ImageButton education;
    ImageButton sports;
    ImageButton cultural;
//    Context context;for menu navigation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        education = (ImageButton) findViewById(R.id.imageButton2);
        sports = (ImageButton) findViewById(R.id.imageButton3);
        cultural = (ImageButton) findViewById(R.id.imageButton4);
        education.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FabScreen.class);
                startActivity(i);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FabScreen2.class);
                startActivity(i);
            }
        });
        cultural.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FabScreen3.class);
                startActivity(i);
            }
        });
    }

    @Override
    //creating menus 3 dot indicator
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;

    }

    @Override
    //for adding action to the menus in 3 dot indicator
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.login:
                Toast.makeText(this,"Login clicked",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                startActivity(intent);
                break;
            case R.id.notification:
                Toast.makeText(this,"My Profile clicked",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(getApplicationContext(),MyAccount.class);
                startActivity(intent2);
                break;
            case R.id.myProfile:
                Toast.makeText(this,"My Posts clicked",Toast.LENGTH_SHORT).show();
                break;

        }


        return true;
    }
}
