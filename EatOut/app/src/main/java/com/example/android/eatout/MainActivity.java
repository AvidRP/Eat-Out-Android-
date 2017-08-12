package com.example.android.eatout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    Button searchButton;

    TextView searchResultsTextView;

    TextView errorMessageDisplay;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editTextSearchQuery);

        searchButton = (Button) findViewById(R.id.searchButton);

        searchResultsTextView = (TextView) findViewById(R.id.textViewDisplayResults);

        errorMessageDisplay = (TextView) findViewById(R.id.errorMessageDisplayTextView);

        progressBar = (ProgressBar) findViewById(R.id.pbDisplay);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSearchQuery();
            }
        });
    }

    public void makeSearchQuery() {
        //need make the url, so call buildUrl
        String searchQuery = editText.getText().toString();
        URL newSearchUrl = NetworkUtils.buildUrl(searchQuery);
        new searchTask().execute(newSearchUrl);
    }

    //to display the navbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //when user selects one the menu buttons
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //for now setting up toast to see if there is response
        switch(item.getItemId()) {
            case (R.id.menuLoginButton):
                Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.menuAboutButton):
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.menuRegisterButton):
                Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //better error handling
    private void displayErrorMessage() {
        errorMessageDisplay.setVisibility(View.VISIBLE);
        searchResultsTextView.setVisibility(View.INVISIBLE);
    }

    private void displaySearchResults () {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        searchResultsTextView.setVisibility(View.VISIBLE);
    }

    //have to do all connection stuff in another thread otherwise the app crashes.
    public class searchTask extends AsyncTask <URL, Void, String> {

        //progress bar till the background task is done
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        //so get the http response in the background
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e){
                e.printStackTrace();
            }

            return searchResults;
        }

        //after the background task is complete
        @Override
        protected void onPostExecute(String s) {
            //gotta hide the progress bar
            progressBar.setVisibility(View.INVISIBLE);
            //have to see if its still null
            if(s != null && !s.equals("")) {
                searchResultsTextView.setText(s);
                //no error means display content
                displaySearchResults();
            } else {
                displayErrorMessage();
            }
            super.onPostExecute(s);
        }
    }


}
