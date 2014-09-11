package com.example.android.notepad;

/**
 * Created by Misha on 9/10/2014.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {


            public void run()
            {


                boolean oneTime = sharedPreferences.getBoolean("oneTime", false);
                if(oneTime == false) {
                    startActivity(new Intent(SplashActivity.this, SetPassword.class));
                    Editor editor = sharedPreferences.edit();
                    editor.putBoolean("oneTime", true);
                    // Setting Dialog Title
                    editor.commit();
                }

                else
                {
                    startActivity(new Intent(SplashActivity.this, NotePassword.class));
                }

                finish();
            }

        },secondsDelayed*1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.notes_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
       // if (id == R.id.action_settings) {
         //   return true;
        //}
        return super.onOptionsItemSelected(item);
    }
}
