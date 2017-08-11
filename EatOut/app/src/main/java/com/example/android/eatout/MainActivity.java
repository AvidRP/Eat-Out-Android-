package com.example.android.eatout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
