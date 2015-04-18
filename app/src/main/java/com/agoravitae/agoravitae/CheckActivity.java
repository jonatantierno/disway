package com.agoravitae.agoravitae;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class CheckActivity extends ActionBarActivity {
    View checkLayout;

    private byte[] OK_ID = new byte[]{-64, 118, -55, 41};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        TextView backTextView = (TextView) findViewById(R.id.backTextView);
        checkLayout = findViewById(R.id.checkLayout);

        backTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getSharedPreferences(RecordTagActivity.RECORDING_STORAGE,0).getBoolean(RecordTagActivity.RECORDING, false)){
            Intent intent = new Intent(this, RecordTagActivity.class);
            intent.putExtra(RecordTagActivity.READING_EXTRA,true);
            startActivity(intent);
            finish();
            return;
        }

        Intent intent = getIntent();

        byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

        if (intent.getBooleanExtra(RouteActivity.RESULT_OK_EXTRA, false)){
            setOk();
        }
        if (idToString(id).equals(idToString(OK_ID))){
            setOk();
        }


        Log.d("nfc", "ID: " + idToString(id));

    }

    private void setOk() {
        checkLayout.setBackground(getResources().getDrawable(R.drawable.background_check_ok));
    }

    private String idToString(byte[] id) {
        StringBuffer res = new StringBuffer();
        for(int i=0; i<id.length;i++) {
            res.append(new Integer(id[i]));
            res.append(' ');
        }
        return res.toString();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

        Log.d("nfc", "ID: " + new String(id));

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
