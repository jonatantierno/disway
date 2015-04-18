package com.agoravitae.agoravitae;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RouteActivity extends ActionBarActivity {

    public static final String RESULT_OK_EXTRA = "RESULT_OK";
    RecyclerView mRecyclerView;
    final List<String> list = new ArrayList();
    private TTAdapter listAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        mRecyclerView = (RecyclerView) findViewById(R.id.transferList);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        listAdapter = new TTAdapter(list,this);
        mRecyclerView.setAdapter(listAdapter);

        TextView backTextView = (TextView) findViewById(R.id.backTextView);

        backTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        TextView okTextView = (TextView) findViewById(R.id.okTextView);

        okTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RouteActivity.this, CheckActivity.class);
                intent.putExtra(RESULT_OK_EXTRA, true);
                startActivity(intent);
            }
        });


        TextView failTextView = (TextView) findViewById(R.id.failTextView);

        failTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RouteActivity.this, CheckActivity.class);
                intent.putExtra(RESULT_OK_EXTRA, false);
                startActivity(intent);
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
