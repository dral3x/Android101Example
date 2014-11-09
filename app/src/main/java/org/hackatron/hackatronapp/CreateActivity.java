package org.hackatron.hackatronapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CreateActivity extends Activity
{
    private TextView _field;
    private Button _button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create);

        this._field = (EditText) findViewById(R.id.create_todo);
        this._button = (Button) findViewById(R.id.create_button);

        this._button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                _save();
            }
        });
    }

    private void _save()
    {
        Intent data = new Intent();
        data.putExtra("todo", this._field.getText().toString());

        setResult(RESULT_OK, data); // pass result
        finish(); // close activity
    }
}
