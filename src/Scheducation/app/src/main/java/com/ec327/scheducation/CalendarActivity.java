package com.ec327.scheducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


// Used to display calendar on calendar activity page, interactable.
public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { // CONTROLS EVERYTHING
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar); // Calendar View
    }

    public void gotoTasks(View view) {
        startActivity(new Intent(CalendarActivity.this, TaskActivity.class));
    }
}
