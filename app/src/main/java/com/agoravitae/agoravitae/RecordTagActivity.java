package com.agoravitae.agoravitae;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RecordTagActivity extends ActionBarActivity {

    public static final String RECORDING = "RECORDING";
    public static final String RECORDING_STORAGE = "RECORDING_STORAGE";
    public static final String READING_EXTRA = "READING_EXTRA";

    TextView touchTextView;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordtag);

        touchTextView = (TextView) findViewById(R.id.readingTextView);
        layout = findViewById(R.id.recordTagLayout);

        initAutocomplete();

        TextView backTextView = (TextView) findViewById(R.id.backTextView);

        backTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Button b = (Button) findViewById(R.id.touchButton);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                readTag();
            }
        });

        TextView readTextView = (TextView) findViewById(R.id.okTextView);

        readTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onTagRead();
            }
        });

    }

    private void onTagRead() {
        layout.setVisibility(View.GONE);
        touchTextView.setVisibility(View.VISIBLE);
        touchTextView.setText("La etiqueta se ha guardado correctamente");
        disableRecording(this);
    }

    private void readTag() {
        layout.setVisibility(View.GONE);
        touchTextView.setVisibility(View.VISIBLE);
        getSharedPreferences(RECORDING_STORAGE, 0).edit().putBoolean(RECORDING,true).commit();

    }

    private void initAutocomplete() {
        final AutoCompleteTextView stationEditText = (AutoCompleteTextView) findViewById(R.id.stationEditText);
        final EditText lineEditText = (EditText) findViewById(R.id.lineEditText);
        final AutoCompleteTextView directionEditText = (AutoCompleteTextView) findViewById(R.id.directionEditText);
        final EditText detailsEditText = (EditText) findViewById(R.id.detailsEditText);

        String[] countries = getResources().getStringArray(R.array.stations_array);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1, countries);

        stationEditText.setAdapter(adapter);
        directionEditText.setAdapter(adapter);

        TextView.OnEditorActionListener textViewListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (v == stationEditText) {
                    lineEditText.requestFocus();
                    return true;
                }
                if (v == lineEditText) {
                    directionEditText.requestFocus();
                    return true;
                }

                if (v == directionEditText) {
                    detailsEditText.requestFocus();
                    return true;
                }
                if (v == detailsEditText) {
                    readTag();
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getBooleanExtra(READING_EXTRA, false)){
            onTagRead();
        }

    }

    public void onPause() {
        super.onPause();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        disableRecording(this);
    }

    public static void disableRecording(Activity activity) {
        activity.getSharedPreferences(RECORDING_STORAGE, 0).edit().putBoolean(RECORDING,false).commit();

    }
}
