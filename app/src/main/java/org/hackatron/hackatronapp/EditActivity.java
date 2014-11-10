package org.hackatron.hackatronapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditActivity extends Activity
{
    // Expose custom results
    public static final int RESULT_UPDATE = RESULT_FIRST_USER + 1;
    public static final int RESULT_DELETE = RESULT_FIRST_USER + 2;

    private TextView _field;
    private Button _saveButton;
    private Button _deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        this._field = (EditText) findViewById(R.id.edit_todo);
        this._saveButton = (Button) findViewById(R.id.edit_update);
        this._deleteButton = (Button) findViewById(R.id.edit_delete);

        // Set todo
        String todo = getIntent().getStringExtra("todo");
        this._field.setText(todo);

        // Set listeners
        this._saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                _update();
            }
        });

        this._deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                _delete();
            }
        });
    }

    private void _update()
    {
        Intent data = new Intent();
        data.putExtras(getIntent());
        data.putExtra("todo_updated", this._field.getText().toString());

        setResult(RESULT_UPDATE, data); // pass result
        finish(); // close activity
    }

    private void _delete()
    {
        Intent data = new Intent();
        data.putExtras(getIntent());

        setResult(RESULT_DELETE, data); // pass result
        finish(); // close activity
    }
}
