package com.ruslander.leo.leoactivitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ruslander.leo.leoactivitytracker.utils.DateTimePicker;

public class DiaperActivity extends AppCompatActivity {

    private final static int SUMMARY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUMMARY_REQUEST && resultCode == RESULT_OK) {
            finish();
        }
    }

    /**
     * Called when the Next button is clicked.
     *
     * @param view The object that was clicked
     */
    public void nextClick(View view) {
        String subtype = "";
        if (((CheckBox)findViewById(R.id.chk_pee)).isChecked()) {
            subtype = "Pee";
        }
        if (((CheckBox)findViewById(R.id.chk_poop)).isChecked()) {
            if (!subtype.isEmpty()) {
                subtype += "/";
            }
            subtype += "Poop";
        }
        String details = ((EditText)findViewById(R.id.txt_notes)).getText().toString();
        String starttime = ((EditText)findViewById(R.id.txt_diapertime)).getText().toString();
        String endtime = ((EditText)findViewById(R.id.txt_diapertime)).getText().toString();

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra(getString(R.string.ACTIVITY_TYPE), "DIAPER");
        intent.putExtra(getString(R.string.ACTIVITY_SUBTYPE), subtype);
        intent.putExtra(getString(R.string.ACTIVITY_DETAILS), details);
        intent.putExtra(getString(R.string.ACTIVITY_STARTTIME), starttime);
        intent.putExtra(getString(R.string.ACTIVITY_ENDTIME), endtime);
        startActivityForResult(intent, SUMMARY_REQUEST);
    }

    public void diaperTimeClick(View view) {
        EditText textbox = findViewById(R.id.txt_diapertime);
        DateTimePicker.pickDateTime(this, textbox);
    }
}
