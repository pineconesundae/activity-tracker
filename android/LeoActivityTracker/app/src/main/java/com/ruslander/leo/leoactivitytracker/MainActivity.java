package com.ruslander.leo.leoactivitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // TODO: Have some sort of login and baby selection screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps an activity type button.
     *
     * @param view The object that was clicked
     */
    public void selectActivityType(View view) {
        if (view.getId() == R.id.btn_feed) {
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_nap) {
            Intent intent = new Intent(this, NapActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btn_diaper) {
            Intent intent = new Intent(this, DiaperActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, OtherActivity.class);
            startActivity(intent);
        }
    }
}
