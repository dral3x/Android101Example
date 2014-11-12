package org.hackatron.hackatronapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends Activity
{
    private static final String LOG_TAG = "MainActivity";
    private static final String PREFS_KEY = "TodoAppPrefsKey";

    private static final int REQUEST_CREATE = 0;
    private static final int REQUEST_EDIT = 1;

    private TodoAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Setup adapter
        //this._adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] { "say something nice", "eat something", "drink less" });
        this._adapter = new TodoAdapter();

        // Restore adapter content
        this._restoreAdapterContent();

        // Setup list view and add button
        ListView listView = (ListView) findViewById(R.id.main_list);
        listView.setAdapter(this._adapter);
        listView.setOnItemClickListener(new TodoOnClickListener());

        ImageButton button = (ImageButton) findViewById(R.id.main_add);
        button.setOnClickListener(new CreateOnClickListener());
    }

    protected void onStart()
    {
        super.onStart();

        Log.i(LOG_TAG, "onStart");

        // NOTE: you cannot restore adapter content at this moment because
        // onActivityResult() will be called before onStart().
    }

    protected void onStop()
    {
        Log.i(LOG_TAG, "onStop");

        // Save adapter content
        this._saveAdapterContent();

        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Note: this callback will arrive BEFORE onStart()...

        if (requestCode == REQUEST_CREATE && resultCode == RESULT_OK && data != null) {
            Log.i(LOG_TAG, "onActivityResult CREATE");

            String text = data.getStringExtra("todo");
            this._adapter.add(text);
            return;
        }

        if (requestCode == REQUEST_EDIT && data != null) {
            String original = data.getStringExtra("todo");
            String updated = data.getStringExtra("todo_updated");

            if (resultCode == EditActivity.RESULT_UPDATE) {
                Log.i(LOG_TAG, "onActivityResult UPDATE");

                this._adapter.update(original, updated);
            } else if (resultCode == EditActivity.RESULT_DELETE) {
                Log.i(LOG_TAG, "onActivityResult DELETE");

                this._adapter.remove(original);
            }
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void _openCreateActivity()
    {
        Log.i(LOG_TAG, "Opening create todo");
        Intent create = new Intent(this, CreateActivity.class);
        startActivityForResult(create, REQUEST_CREATE);
    }

    private void _saveAdapterContent()
    {
        Bundle bundle = new Bundle();
        this._adapter.saveInstanceState(bundle);

        SharedPreferences.Editor editor = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE).edit();
        editor.putString("adapter_content", SerializationUtil.encodeBundle(bundle));
        editor.commit();
    }

    private void _restoreAdapterContent()
    {
        SharedPreferences preferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        String encodedData = preferences.getString("adapter_content", null);
        Bundle bundle = SerializationUtil.decodeBundle(encodedData);
        this._adapter.restoreInstanceState(bundle);
    }

    private class CreateOnClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            // Open create activity
            _openCreateActivity();
        }
    }

    private class TodoOnClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            Log.i(LOG_TAG, "Selected " + _adapter.getItem(i));

            // Open edit activity
            String todo = (String) _adapter.getItem(i);
            Intent create = new Intent(MainActivity.this, EditActivity.class);
            create.putExtra("todo", todo);
            startActivityForResult(create, REQUEST_EDIT);
        }
    }
}
