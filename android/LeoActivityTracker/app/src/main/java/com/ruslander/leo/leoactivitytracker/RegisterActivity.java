package com.ruslander.leo.leoactivitytracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ruslander.leo.leoactivitytracker.utils.PasswordHasher;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        final String email = ((TextView)findViewById(R.id.txt_email)).getText().toString();
        final String password = ((TextView)findViewById(R.id.txt_password)).getText().toString();
        final String verifyPassword = ((TextView)findViewById(R.id.txt_verify)).getText().toString();
        final String firstName = ((TextView)findViewById(R.id.txt_firstname)).getText().toString();
        final String lastName = ((TextView)findViewById(R.id.txt_lastname)).getText().toString();

        if (password.equals(verifyPassword)) {
            String salt = PasswordHasher.getSalt();
            String hashedPassword = PasswordHasher.hashPassword(password, salt);

            String url = "http://192.168.1.145/leo/activity/put.php?object=user&email={0}&password={1}&salt={2}&firstname={3}&lastname={4}";

            Object[] args = {email, hashedPassword, salt, firstName, lastName};
            MessageFormat msgFormat = new MessageFormat(url);
            String formattedUrl = msgFormat.format(args);

            StringRequest stringRequest = new StringRequest
                (Request.Method.POST, formattedUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(getString(R.string.REGISTER_EMAIL), email);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage(error.getMessage());
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("An error occurred!");
                            dialog.show();
                        }
                    }
                );

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        }
    }
}
