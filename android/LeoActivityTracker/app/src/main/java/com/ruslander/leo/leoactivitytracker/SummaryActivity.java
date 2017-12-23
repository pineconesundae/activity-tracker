package com.ruslander.leo.leoactivitytracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {
    final Context context = this;

    private String type;
    private String subtype;
    private String details;
    private String start;
    private String end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Get the Intent that started this Activity and extract the messages
        Intent intent = getIntent();
        type = intent.getStringExtra(getString(R.string.ACTIVITY_TYPE));
        subtype = intent.getStringExtra(getString(R.string.ACTIVITY_SUBTYPE));
        details = intent.getStringExtra(getString(R.string.ACTIVITY_DETAILS));
        start = intent.getStringExtra(getString(R.string.ACTIVITY_STARTTIME));
        end = intent.getStringExtra(getString(R.string.ACTIVITY_ENDTIME));

        // Set the values to the labels on the summary activity
        ((TextView)findViewById(R.id.lbl_type)).setText(type);
        ((TextView)findViewById(R.id.lbl_subtype)).setText(subtype);
        ((TextView)findViewById(R.id.lbl_details)).setText(details);
        ((TextView)findViewById(R.id.lbl_start)).setText(start);
        ((TextView)findViewById(R.id.lbl_end)).setText(end);
    }

    /**
     * Submits the activity to the database.
     *
     * @param view The object that was clicked
     */
    public void submitClick(View view) {
        // TODO: Test what happens if this URL isn't valid
        String url = "http://192.168.1.145/leo/activity/put.php?date={0}&type={1}&subtype={2}&details={3}&start={4}&end={5}&logged={6}";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd", Locale.US);
        String date = format.format(cal.getTime());

        Object[] args = {date, type, subtype, details, start, end, "Test"};
        MessageFormat msgFormat = new MessageFormat(url);
        String formattedUrl = msgFormat.format(args);

        StringRequest jsObjRequest = new StringRequest
                (Request.Method.POST, formattedUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage(response);
                                AlertDialog dialog = builder.create();
                                dialog.setTitle("Submit successful!");
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                });
                                dialog.show();
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
        queue.add(jsObjRequest);
    }
}
