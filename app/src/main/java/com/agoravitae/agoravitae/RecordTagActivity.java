package com.agoravitae.agoravitae;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
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

    private IntentFilter[] intentFiltersArray;
    private String[][] techListsArray;
    // private NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    TextView touchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordtag);

        touchTextView = (TextView) findViewById(R.id.readingTextView);

        //prepareNfc();

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
                touchTextView.setText("La etiqueta se ha guardado correctamente");
            }
        });

    }

    private void readTag() {
        View layout = findViewById(R.id.recordTagLayout);
        layout.setVisibility(View.GONE);
        touchTextView.setVisibility(View.VISIBLE);
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

    private void prepareNfc() {
        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef, };
        techListsArray = new String[][] { new String[] {
                NfcF.class.getName(),
                NfcA.class.getName(),
                NfcB.class.getName(),
                TagTechnology.class.getName(),} };
    }

    @Override
    public void onStart() {
        super.onStart();
       // nfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

//        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray);

        Intent intent = getIntent();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            logNfcData(intent);
        }
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
            logNfcData(intent);
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            logNfcData(intent);
        }
    }
    public void onNewIntent(Intent intent) {
        logNfcData(intent);
    }
    private void logNfcData(Intent intent) {
        byte[] idByteArray = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
        Log.d("nfc", "ID: " + new String(idByteArray));

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d("nfc", "TAG__ID: " + tag.getId() + ", contents: " + tag.describeContents());
    }

    public void onPause() {
        super.onPause();
   //     nfcAdapter.disableForegroundDispatch(this);
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
