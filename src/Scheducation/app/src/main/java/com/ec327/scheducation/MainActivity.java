package com.ec327.scheducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

// Main menu with button to calendar activity which can then access task activity.
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Main View
    }

    public void gotoCalendar(View view) {
        startActivity(new Intent(MainActivity.this, CalendarActivity.class));
    }
}
