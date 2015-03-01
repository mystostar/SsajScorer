package com.mystostar.ssajscorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_DATA = "com.mycompany.myfirstapp.PLAYERS";


    String[] dataToSend = {"","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onResume() {
        super.onResume();

        Button buttonResume = (Button) findViewById(R.id.buttonResume);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean firstLaunch = sharedPreferences.getBoolean("FIRSTLAUNCH", true);
        if(firstLaunch) {
            buttonResume.setClickable(false);
        }
        else{
           buttonResume.setClickable(true);
        }
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
        if (id == R.id.rules) {
            Intent myIntent = new Intent(this, RulesActivity.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonNewGame(View view) {
        EditText[] et = {(EditText) findViewById(R.id.editP1),(EditText) findViewById(R.id.editP2),(EditText) findViewById(R.id.editP3), (EditText) findViewById(R.id.editP4)};

        dataToSend[0] = et[0].getText().toString();
        dataToSend[1] = et[1].getText().toString();
        dataToSend[2] = et[2].getText().toString();
        dataToSend[3] = et[3].getText().toString();
        dataToSend[4] = "N";

        if(dataToSend[0].length() == 0)
            dataToSend[0] = getString(R.string.player1);
        if(dataToSend[1].length() == 0)
            dataToSend[1] = getString(R.string.player2);
        if(dataToSend[2].length() == 0)
            dataToSend[2] = getString(R.string.player3);
        if(dataToSend[3].length() == 0)
            dataToSend[3] = getString(R.string.player4);

        Intent intentNewGame = new Intent(this, GameActivity.class);

        intentNewGame.putExtra(EXTRA_DATA, dataToSend);

        startActivity(intentNewGame);
    }

    public void buttonResume(View view) {
        Intent intentResume = new Intent(this, GameActivity.class);
        dataToSend[4] = "R";
        intentResume.putExtra(EXTRA_DATA, dataToSend);

        startActivity(intentResume);
    }

}
