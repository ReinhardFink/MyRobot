package net.myrobot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Main_Robot extends AppCompatActivity {

    private TextView tvRoom;

    private Room room;
    private ArrayList<Robot> robotList;
    private volatile boolean isThreadRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__robot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createGameLogic();
        createLayout();
        init();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.isThreadRunning = false;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void createGameLogic() {
        isThreadRunning = false;

        // Without setting default values, app will crash, if charsForRoom.toCharArray() is called.
        // Variant 1:
        //              preferences.getString("pref_key_charsForRoom",""); "" <- default value here
        // Variant 2:   PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
        // here we get former set preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        // to test & understand
        //System.out.println("settings-all: " + preferences.getAll());
        //System.out.println("pref_key_charsForRobot: " + preferences.getString("pref_key_charsForRobot",""));

        String charsForRoom = preferences.getString("pref_key_charsForRoom",getResources().getString(R.string.pref_default_charsForRoom));
        room = new Room(20, 35, charsForRoom.toCharArray());

        String charsForRobot = preferences.getString("pref_key_charsForRobot",getResources().getString(R.string.pref_default_charsForRobot));
        robotList = new ArrayList<>();
        robotList.add(new Robot(5, 5, Direction.getUP(), room, charsForRobot.toCharArray()));
        robotList.add(new Robot(10, 10, Direction.getRIGHT(), room, charsForRobot.toCharArray()));
    }

    private void createLayout() {
        Button bnInit = findViewById(R.id.bnInit);
        bnInit.setOnClickListener(clickEvent -> init());

        Button bnAct = findViewById(R.id.bnAct);
        bnAct.setOnClickListener(clickEvent -> act());

        Button bnRun = findViewById(R.id.bnRun);
        bnRun.setOnClickListener(clickEvent -> run());

        Button bnStop = findViewById(R.id.bnStop);
        bnStop.setOnClickListener(clickEvent -> stop());
        tvRoom = findViewById(R.id.textView);
    }

    private void init() {
        createGameLogic();
        tvRoom.setText(room.toString());
    }

    private void act() {
        createTread(true);
    }

    private void run() {
        createTread(false);
    }

    private void createTread(boolean runOnce) {
        // check for already running thread
        if (isThreadRunning) return;
        isThreadRunning = true;
        new Thread() {
            @Override
            public void run() {
                while (isThreadRunning) {
                    for (Robot robot : robotList) robot.act();
                    tvRoom.post(() -> tvRoom.setText(room.toString()));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (runOnce) isThreadRunning = false;
                }
            }
        }.start();
    }

    private void stop() {
        isThreadRunning = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main__robot, menu);
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
            startActivity(new Intent(this, Settings_Activity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
