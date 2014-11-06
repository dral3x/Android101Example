package org.hackatron.hackatronapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CreateActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create);

        Button submitButton = (Button) findViewById(R.id.create_submit);
        submitButton.setOnClickListener(new SubmitClickListener());
    }


    private class SubmitClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            // TODO
        }
    }
}
