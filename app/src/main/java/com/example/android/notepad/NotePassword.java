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
import android.util.Base64;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.notepad.internal.CryptUtils;
import com.example.android.notepad.internal.ICryptUtils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by Misha on 9/10/2014.
 */
public class NotePassword extends Activity {
    private static ICryptUtils crypto;
    private static byte[] actualHash = new byte[16];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crypto = new CryptUtils(getApplicationContext());
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(NotePassword.this);
        alertDialog.setTitle("PASSWORD");
        // Setting Dialog Message
        alertDialog.setMessage("Enter Password");
        final EditText input = new EditText(NotePassword.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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

                        byte[] passHash = Base64.decode(sharedPreferences.getString("passHash", null), Base64.DEFAULT);
                        byte[] passSalt = Base64.decode(sharedPreferences.getString("passSalt", null), Base64.DEFAULT);

                        char[] password = new char[input.length()];
                        input.getText().getChars(0, input.length(), password, 0);
                        byte[] pwdInBytes = toBytes(password);
                        actualHash = crypto.generateHash(pwdInBytes, passSalt);
                        String finalHash = "";
                        if(actualHash.length != 0)
                        finalHash = new String(actualHash);
                        if (finalHash!=null && finalHash.equals(new String(passHash))) {
                            // Toast.makeText(getApplicationContext(), "Password Matched", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NotePassword.this, NotesList.class));
                        } else {
                            //  Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();


                            startActivity(new Intent(NotePassword.this, NotePassword.class));

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

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }
}