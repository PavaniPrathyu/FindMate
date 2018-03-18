package com.example.personal.three_dot;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personal.three_dot.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;

import com.example.personal.three_dot.R;
//import android.widget.AdapterView;
//import android.widget.DatePicker;
//import android.widget.TextView;
//import java.util.Calendar;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;
import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.*;


public class PostActivity3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<ToDoItem> mToDoTable;

    //Offline Sync
    /**
     * Mobile Service Table used to access and Sync data
     */
    //private MobileServiceSyncTable<ToDoItem> mToDoTable;

    /**
     * Adapter to sync the items list with the view
     */
    private ToDoItemAdapter mAdapter;

    /**
     * EditText containing the "New To Do" text
     */
    private EditText mTextNewToDo3;



    Spinner sp1, sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post3);

      sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);

        List<String> categories = new ArrayList<String>();
        categories.add("Education");
        categories.add("Sports");
        categories.add("Cultural");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sp1.setAdapter(dataAdapter);
        sp1.setOnItemSelectedListener(this);
        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key

            mClient = new MobileServiceClient(
                    "https://findmates.azurewebsites.net",
                    this
            );
            mToDoTable = mClient.getTable(ToDoItem.class);
            mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);

            // Load the items from the Mobile Service


            mTextNewToDo3 = (EditText) findViewById(R.id.textNewToDo3);

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void addItem3(View view) {
        if (mClient == null) {
            return;
        }

        // Create a new item
        final ToDoItem item = new ToDoItem();

        item.setText(mTextNewToDo3.getText().toString());
        // Insert the new item
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final ToDoItem entity = addItemInTable(item);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.add(entity);

                        }
                    });
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        runAsyncTask(task);

        mTextNewToDo3.setText("");
        Toast.makeText(this,"Successfully posted!!",Toast.LENGTH_SHORT).show();
      //  Intent i=new Intent(this,FabScreen3.class);
       // startActivity(i);
    }

    public ToDoItem addItemInTable(ToDoItem item) throws ExecutionException, InterruptedException {
        ToDoItem entity = mToDoTable.insert(item).get();
        return entity;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spi1 = String.valueOf(sp1.getSelectedItem());
        Toast.makeText(this, spi1, Toast.LENGTH_SHORT).show();
        if (spi1.contentEquals("Education")) {
            List<String> list = new ArrayList<String>();
            list.add("Engineering");
            list.add("Medical");
            list.add("Agricultural");
            list.add("Manufacturing and Construction");
            list.add("Natural Science");
            list.add("Mathematics and Statistics");
            list.add("Arts and Humanities");
            list.add("Business");
            list.add("Law");
            list.add("Information and Communication Technologies");
            list.add("Agriculture");
            list.add("Food");
            list.add("Journalism");
            list.add("Social Science");
            list.add("Others");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter);
        }
        if (spi1.contentEquals("Sports")) {
            List<String> list = new ArrayList<String>();
            list.add("Athletics");
            list.add("Archery");
            list.add("Badminton");
            list.add("Basketball");
            list.add("Baseball");
            list.add("Boxing");
            list.add("Cricket");
            list.add("Cycling");
            list.add("Diving");
            list.add("Football");
            list.add("Golf");
            list.add("Hockey");
            list.add("Horse Racing");
            list.add("Judo");
            list.add("Motor Sport");
            list.add("Netball");
            list.add("Rugby");
            list.add("Sailing");
            list.add("Shooting");
            list.add("Snooker");
            list.add("Swimming");
            list.add("Table Tennis");
            list.add("Tennis");
            list.add("VolleyBall");
            list.add("Wrestling");
            list.add("Others");

            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter2);
        }
        if (spi1.contentEquals("Cultural")) {
            List<String> list = new ArrayList<String>();
            list.add("Music");
            list.add("Dance");
            list.add("Drama");
            list.add("Drawing");
            list.add("Skit");
            list.add("Mime");
            list.add("Mimicry");
            list.add("Debate");
            list.add("Painting");
            list.add("Rangoli");
            list.add("Poetry");
            list.add("Others");
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter3.notifyDataSetChanged();
            sp2.setAdapter(dataAdapter3);
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
}