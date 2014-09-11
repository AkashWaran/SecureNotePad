package com.example.android.notepad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Misha on 9/10/2014.
 */
public class NotePassword extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NotePassword.this);
        alertDialog.setTitle("PASSWORD");
        // Setting Dialog Message
        alertDialog.setMessage("Enter Password");
        final EditText input = new EditText(NotePassword.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        alertDialog.setView(input); // uncomment this line


        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.key);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String pass = sharedPreferences.getString("password", null);
                        String password = input.getText().toString();
                        if (password.equals(pass)) {
                            // Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NotePassword.this,NotesList.class));
                        } else {
                            //  Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NotePassword.this,NotePassword.class));

                        }


                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // closed

        // Showing Alert Message
        alertDialog.show();


    }
}
