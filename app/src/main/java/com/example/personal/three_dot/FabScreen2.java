package com.example.personal.three_dot;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

/* package com.example.personal.three_dot;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;

public class FabScreen3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_screen3);

    }
    public void next(View view) {
        Intent i=new Intent(this,PostActivity.class);
        startActivity(i);
    }

}
*/


        import android.app.AlertDialog;
        import android.os.AsyncTask;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.ProgressBar;

        import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
        import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
        import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
        import com.microsoft.windowsazure.mobileservices.table.query.Query;
        import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
        import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
        import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
        import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
        import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
        import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
        import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
        import com.squareup.okhttp.OkHttpClient;

        import java.net.MalformedURLException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.concurrent.ExecutionException;
        import java.util.concurrent.TimeUnit;

        import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.query;
        import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class FabScreen2 extends AppCompatActivity {
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

    /**
     * Adapter to sync the items list with the view
     //private MobileServiceSyncTable<ToDoItem> mToDoTable;
     */
    private ToDoItemAdapter mAdapter;
    public List<ToDoItem> toDoItemList = new ArrayList<ToDoItem>();


    /**
     * EditText containing the "New To Do" text
     */
    private EditText mTextNewToDo2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_screen2);

        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key

            mClient = new MobileServiceClient(
                    "https://findmates.azurewebsites.net",
                    this
            );
            mToDoTable = mClient.getTable(ToDoItem.class);
            mAdapter = new ToDoItemAdapter(this, R.layout.row_list_to_do);
            ListView listViewToDo2 = (ListView) findViewById(R.id.listViewToDo2);
            listViewToDo2.setAdapter(mAdapter);
            initLocalStore().get();
            refreshItemsFromTable();
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


    public void next2(View view) {
        Intent i = new Intent(this, PostActivity2.class);
        startActivityForResult(i, 1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        refreshItemsFromTable();



    }
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    final List<ToDoItem> results = refreshItemsFromMobileServiceTable();

                    //Offline Sync
                    //final List<ToDoItem> results = refreshItemsFromMobileServiceTableSyncTable();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();

                            for (ToDoItem item : results) {
                                mAdapter.add(item);
                            }
                        }
                    });
                } catch (final Exception e){
                    e.printStackTrace();
                }

                return null;
            }
        };

        runAsyncTask(task);
    }
    private List<ToDoItem> refreshItemsFromMobileServiceTable() throws ExecutionException, InterruptedException {
        return mToDoTable.where().field("complete").
                eq(val(false)).execute().get();
    }
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("text", ColumnDataType.String);
                    tableDefinition.put("complete", ColumnDataType.Boolean);

                    localStore.defineTable("ToDoItem", tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        return runAsyncTask(task);


    }
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

}
