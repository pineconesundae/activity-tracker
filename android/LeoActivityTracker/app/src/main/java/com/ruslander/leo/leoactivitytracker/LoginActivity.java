package com.ruslander.leo.leoactivitytracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ruslander.leo.leoactivitytracker.utils.PasswordHasher;
import com.ruslander.leo.leoactivitytracker.utils.User;
import com.ruslander.leo.leoactivitytracker.utils.UserCookie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private final static int REGISTER_REQUEST = 2;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!UserCookie.isCookieDirty(this)) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTER_REQUEST && resultCode == RESULT_OK) {
            String email = data.getStringExtra(getString(R.string.REGISTER_EMAIL));
            ((EditText)findViewById(R.id.txt_email)).setText(email);
        }
    }

    public void login(View view) {
        final String email = ((TextView)findViewById(R.id.txt_email)).getText().toString();
        final String password = ((TextView)findViewById(R.id.txt_password)).getText().toString();

        String url = "http://192.168.1.145/leo/activity/get.php?object=user&email={0}";

        Object[] args = {email};
        MessageFormat msgFormat = new MessageFormat(url);
        String formattedUrl = msgFormat.format(args);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, formattedUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray records = response.getJSONArray("records");
                                if (records.length() == 1) {
                                    JSONObject object = records.getJSONObject(0);
                                    String salt = String.valueOf(object.getInt("salt"));
                                    String hashedPassword = PasswordHasher.hashPassword(password, salt);

                                    String storedPassword = object.getString("password");
                                    if (storedPassword.equalsIgnoreCase(hashedPassword)) {
                                        User user = new User(object);
                                        UserCookie.writeCookie(context, user, false);

                                        Intent intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Incorrect Password");
                                        AlertDialog dialog = builder.create();
                                        dialog.setTitle("The password entered does not match the supplied username!");
                                        dialog.show();
                                    }
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("System Error");
                                    AlertDialog dialog = builder.create();
                                    dialog.setTitle("A severe system error has occurred. Please contact application support!");
                                    dialog.show();
                                }
                            } catch (JSONException ex) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("System Error");
                                AlertDialog dialog = builder.create();
                                dialog.setTitle("A severe system error has occurred. Please contact application support!");
                                dialog.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("System Error");
                            AlertDialog dialog = builder.create();
                            dialog.setTitle("A severe system error has occurred. Please contact application support!");
                            dialog.show();
                        }
                    }
                );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }

    public void register(View view) {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivityForResult(intent, REGISTER_REQUEST);
    }
}
