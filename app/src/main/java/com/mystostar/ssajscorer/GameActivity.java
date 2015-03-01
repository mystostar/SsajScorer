
package com.mystostar.ssajscorer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;


public class GameActivity extends ActionBarActivity {

    static final String DATA0 = "DATA0";
    static final String DATA1 = "DATA1";
    static final String DATA2 = "DATA2";
    static final String DATA3 = "DATA3";

    static final String SCOREP1 = "SCOREP1";
    static final String SCOREP2 = "SCOREP2";
    static final String SCOREP3 = "SCOREP3";
    static final String SCOREP4 = "SCOREP4";
    static final String CURRENTROUND = "CURRENTROUND";
    static final String CURRENTTRUMP = "CURRENTTRUMP";

    static final int[] coeff = {5,10,20,50,1};

    int scoreP1 = 0, scoreP2 = 0, scoreP3 = 0, scoreP4 = 0;
    int currentRound;
    int currentTrump;

    String[] rounds ={"","","","",""};

    String[] data = {"","","","",""};






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.rounds[0] = getString(R.string.folds);
        this.rounds[1] = getString(R.string.hearts);
        this.rounds[2] = getString(R.string.queens);
        this.rounds[3] = getString(R.string.king);
        this.rounds[4] = getString(R.string.all);

        Random rand = new Random();
        this.currentTrump = rand.nextInt(999999) % 4;
        refreshTrumps();

        Intent intent = getIntent();
        this.data = intent.getStringArrayExtra(MainActivity.EXTRA_DATA);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FIRSTLAUNCH", false);
        editor.commit();
        if (this.data[4].equals("R")) {
            this.data[0] = sharedPreferences.getString(DATA0, getString(R.string.player1));
            this.data[1] = sharedPreferences.getString(DATA1, getString(R.string.player2));
            this.data[2] = sharedPreferences.getString(DATA2, getString(R.string.player3));
            this.data[3] = sharedPreferences.getString(DATA3, getString(R.string.player4));

            this.scoreP1 = sharedPreferences.getInt(SCOREP1,0);
            this.scoreP2 = sharedPreferences.getInt(SCOREP2,0);
            this.scoreP3 = sharedPreferences.getInt(SCOREP3,0);
            this.scoreP4 = sharedPreferences.getInt(SCOREP4,0);

            this.currentRound = sharedPreferences.getInt(CURRENTROUND, 0);

            this.currentTrump = sharedPreferences.getInt(CURRENTTRUMP, this.currentTrump);
        }


        refreshTrumps();
        refreshPlayerNames();
        refreshScores();
        refreshRoundName();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        editor.putString(DATA0, this.data[0]);
        editor.putString(DATA1, this.data[1]);
        editor.putString(DATA2, this.data[2]);
        editor.putString(DATA3, this.data[3]);

        editor.putInt(SCOREP1, this.scoreP1);
        editor.putInt(SCOREP2, this.scoreP2);
        editor.putInt(SCOREP3, this.scoreP3);
        editor.putInt(SCOREP4, this.scoreP4);

        editor.putInt(CURRENTROUND, this.currentRound);
        editor.putInt(CURRENTTRUMP, this.currentTrump);

        editor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
            startActivity(myIntent);}

            return super.onOptionsItemSelected(item);
        }

    public void buttonSendData(View view) {

        String[] rounds = {getString(R.string.folds),getString(R.string.hearts), getString(R.string.queens), getString(R.string.king), getString(R.string.all)};
        int[] results = {0,0,0,0};

        EditText result1 = (EditText) findViewById(R.id.addScoreP1),
        result2 = (EditText) findViewById(R.id.addScoreP2),
        result3 = (EditText) findViewById(R.id.addScoreP3),
        result4 = (EditText) findViewById(R.id.addScoreP4);
        try {
            results[0] = Integer.parseInt(result1.getText().toString());
        } catch (NumberFormatException e) {
            results[0] = 0;
        }
        try {
            results[1] = Integer.parseInt(result2.getText().toString());
        } catch (NumberFormatException e) {
            results[1] = 0;
        }
        try {
            results[2] = Integer.parseInt(result3.getText().toString());
        } catch (NumberFormatException e) {
            results[2] = 0;
        }
        try {
            results[3] = Integer.parseInt(result4.getText().toString());
        } catch (NumberFormatException e) {
            results[3] = 0;
        }

        this.scoreP1 = this.scoreP1 + this.coeff[currentRound] * results[0];
        this.scoreP2 = this.scoreP2 + this.coeff[currentRound] * results[1];
        this.scoreP3 = this.scoreP3 + this.coeff[currentRound] * results[2];
        this.scoreP4 = this.scoreP4 + this.coeff[currentRound] * results[3];

        refreshScores();
        this.currentRound = (currentRound + 1) % 5;
        refreshRoundName();

        EditText editTextP1 = (EditText) findViewById(R.id.addScoreP1),
                editTextP2 = (EditText) findViewById(R.id.addScoreP2),
                editTextP3 = (EditText) findViewById(R.id.addScoreP3),
                editTextP4 = (EditText) findViewById(R.id.addScoreP4);

        editTextP1.setText("");
        editTextP2.setText("");
        editTextP3.setText("");
        editTextP4.setText("");

        Random rand = new Random();
        this.currentTrump = rand.nextInt(999999) % 4;
        refreshTrumps();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DATA0, this.data[0]);
        editor.putString(DATA1, this.data[1]);
        editor.putString(DATA2, this.data[2]);
        editor.putString(DATA3, this.data[3]);

        editor.putInt(SCOREP1, this.scoreP1);
        editor.putInt(SCOREP2, this.scoreP2);
        editor.putInt(SCOREP3, this.scoreP3);
        editor.putInt(SCOREP4, this.scoreP4);

        editor.putInt(CURRENTROUND, this.currentRound);
        editor.putInt(CURRENTTRUMP, this.currentTrump);

        editor.commit();
    }

    void refreshScores() {
        TextView textViewPlayerTotScore1 = (TextView) findViewById(R.id.scoreTot1),
                textViewPlayerTotScore2 = (TextView) findViewById(R.id.scoreTot2),
                textViewPlayerTotScore3 = (TextView) findViewById(R.id.scoreTot3),
                textViewPlayerTotScore4 = (TextView) findViewById(R.id.scoreTot4);

        textViewPlayerTotScore1.setText(Integer.toString(scoreP1));
        textViewPlayerTotScore2.setText(Integer.toString(scoreP2));
        textViewPlayerTotScore3.setText(Integer.toString(scoreP3));
        textViewPlayerTotScore4.setText(Integer.toString(scoreP4));
    }

    void refreshRoundName(){
        TextView typePartieText = (TextView) findViewById(R.id.round);
        typePartieText.setText(this.rounds[currentRound]);
    }

    void refreshPlayerNames() {
        TextView textViewPlayerName1 = (TextView) findViewById(R.id.player1Cell);
        TextView textViewPlayerName2 = (TextView) findViewById(R.id.player2Cell);
        TextView textViewPlayerName3 = (TextView) findViewById(R.id.player3Cell);
        TextView textViewPlayerName4 = (TextView) findViewById(R.id.player4Cell);

        textViewPlayerName1.setText(this.data[0]);
        textViewPlayerName2.setText(this.data[1]);
        textViewPlayerName3.setText(this.data[2]);
        textViewPlayerName4.setText(this.data[3]);
    }

    void refreshTrumps() {
        TextView trumpsView = (TextView) findViewById(R.id.trumps);

        switch(this.currentTrump) {
            case(0):
                trumpsView.setText(getString(R.string.heartsTrumps));
                break;
            case(1):
                trumpsView.setText(getString(R.string.clubs));
                break;
            case(2):
                trumpsView.setText(getString(R.string.spades));
                break;
            case(3):
                trumpsView.setText(getString(R.string.diamonds));
                break;
        }

    }

}
