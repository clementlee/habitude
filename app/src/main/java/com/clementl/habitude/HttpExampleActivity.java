package com.clementl.habitude;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class HttpExampleActivity extends ActionBarActivity implements View.OnClickListener{

    TextView tvResponse;
    TextView tvIsConnected;
    EditText etValue;
    Button getBtn, postBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_example);

        //get reference to the views
        tvResponse = (TextView) findViewById(R.id.tvResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        etValue = (EditText) findViewById(R.id.etValue);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(this);
        postBtn = (Button) findViewById(R.id.postBtn);
        postBtn.setOnClickListener(this);

        //check connection
        if(isConnected()) {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("Connected to Internet");
            tvResponse.setMovementMethod(new ScrollingMovementMethod());
        }
        else {
            tvIsConnected.setText("NOT Connected to Internet");
        }
    }

    public void onClick(View v) {
        if(etValue.getText().toString().length() < 1) {
            Toast.makeText(this, "Enter something", Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.getBtn:
                new GetAsyncTask().execute(etValue.getText().toString());
                //call AsyncTask to perform network operation on separate thread
                break;

            case R.id.postBtn:
                new PostAsyncTask().execute(etValue.getText().toString());
                break;
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    //Async task for get
    private class GetAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GetData.GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            tvResponse.setText(result);
        }
    }

    //Async task for post
    private class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GetData.POST(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Sent!", Toast.LENGTH_LONG).show();
            tvResponse.setText(result);
        }
    }
}
