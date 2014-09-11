package com.example.android.notepad;

/**
 * Created by Misha on 9/10/2014.
 */
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Created by rohitramkumar on 9/5/14.
 */
public class SetPassword extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);


        Button clear = (Button) findViewById(R.id.Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((EditText) findViewById(R.id.password)).setText("");
                ((EditText) findViewById(R.id.repassword)).setText("");
            }
        });

        Button ok = (Button) findViewById(R.id.Ok);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String pwd =((EditText)findViewById(R.id.password)).getText().toString();
                String repwd = ((EditText)findViewById(R.id.repassword)).getText().toString();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SetPassword.this);
                if (pwd.equals(repwd)) {
                    // Creating alert Dialog with one Button
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Editor editor = sharedPreferences.edit();
                    editor.putString("password",pwd);
                    // Setting Dialog Title
                    editor.commit();
                    startActivity(new Intent(SetPassword.this,NotesList.class));


                } else {
                    alertDialog.setMessage("Password and Confirm Password does not match")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //startActivity(new Intent(SetPassword.this,SetPassword.class));
                                    ((EditText) findViewById(R.id.password)).setText("");
                                    ((EditText) findViewById(R.id.repassword)).setText("");
                                }
                            });
                    AlertDialog alert = alertDialog.create();
                    alert.show();

                }
            }


        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        ((EditText) findViewById(R.id.password)).setText("");
        ((EditText) findViewById(R.id.repassword)).setText("");
        // Normal case behavior follows
    }



}