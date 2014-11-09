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
import android.widget.ListView;


public class MainActivity extends Activity
{
    private static final String LOG_TAG = "MainActivity";
    private static final String PREFS_KEY = "TodoAppPrefsKey";

    private static final int REQUEST_CREATE = 0;
    private static final int REQUEST_EDIT = 1;

    private TodoAdapter _adapter = new TodoAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.main_list);
        listView.setAdapter(this._adapter);
        listView.setOnItemClickListener(new TodoOnClickListener());
    }

    protected void onStart()
    {
        super.onStart();

        Log.i(LOG_TAG, "onStart");

        // Restore adapter content
        SharedPreferences preferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        String encodedData = preferences.getString("adapter_content", null);
        Bundle bundle = SerializationUtil.decodeBundle(encodedData);

        this._adapter.restoreInstanceState(bundle);
    }

    protected void onStop()
    {
        Log.i(LOG_TAG, "onStop");

        // Save adapter content
        Bundle bundle = new Bundle();
        this._adapter.saveInstanceState(bundle);

        SharedPreferences.Editor editor = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE).edit();
        editor.putString("adapter_content", SerializationUtil.encodeBundle(bundle));
        editor.commit();

        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_create) {

            // Open create activity
            Log.i(LOG_TAG, "Opening create todo");
            Intent create = new Intent(this, CreateActivity.class);
            startActivityForResult(create, REQUEST_CREATE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CREATE && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "onActivityResult CREATE");

            String text = data.getStringExtra("todo");
            this._adapter.add(text);
            return;
        }

        if (requestCode == REQUEST_EDIT) {
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

    private class TodoOnClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            // Open edit activity

            String todo = (String) _adapter.getItem(i);
            Intent create = new Intent(MainActivity.this, EditActivity.class);
            create.putExtra("todo", todo);
            startActivityForResult(create, REQUEST_EDIT);
        }
    }
}
