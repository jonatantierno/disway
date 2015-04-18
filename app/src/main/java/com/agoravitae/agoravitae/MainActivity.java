package com.agoravitae.agoravitae;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AutoCompleteTextView originEditText = (AutoCompleteTextView) findViewById(R.id.originEditText);
        final AutoCompleteTextView destinationEditText = (AutoCompleteTextView) findViewById(R.id.destinationEditText);
        String[] countries = getResources().getStringArray(R.array.stations_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, countries);

        originEditText.setAdapter(adapter);
        destinationEditText.setAdapter(adapter);

        TextView menu = (TextView) findViewById(R.id.menuTextView);

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, RecordTagActivity.class));
            }
        });

        Button b = (Button) findViewById(R.id.goButton);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RouteActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(int position) {
    }
}
