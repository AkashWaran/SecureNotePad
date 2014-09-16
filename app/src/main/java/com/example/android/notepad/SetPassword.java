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
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.android.notepad.internal.CryptUtils;
import com.example.android.notepad.internal.ICryptUtils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * Created by rohitramkumar on 9/5/14.
 */
public class SetPassword extends Activity{
    private static ICryptUtils crypto;
    private static byte[] salt=new byte[16];
    private static byte[] hash=new byte[16];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password);

        crypto =new CryptUtils(getApplicationContext());
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

                 EditText password = ((EditText)findViewById(R.id.password));
                 char[] pwd = new char[password.length()];
                 password.getText().getChars(0,password.length(),pwd,0);

                 EditText repassword = ((EditText)findViewById(R.id.repassword));
                 char[] repwd = new char[repassword.length()];
                 repassword.getText().getChars(0,repassword.length(),repwd,0);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SetPassword.this);
                if (Arrays.equals(pwd,repwd)) {
                    salt=crypto.generateRandom(16);
                    byte[] pwdInBytes = toBytes(pwd);
                    hash=crypto.generateHash(pwdInBytes,salt);
                    //n Creating alert Dialog with one Button
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Editor editor = sharedPreferences.edit();
                    editor.putString("passHash", Base64.encodeToString(hash, Base64.DEFAULT));
                    editor.putString("passSalt", Base64.encodeToString(salt, Base64.DEFAULT));
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

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((EditText) findViewById(R.id.password)).setText("");
        ((EditText) findViewById(R.id.repassword)).setText("");
        // Normal case behavior follows
    }



}