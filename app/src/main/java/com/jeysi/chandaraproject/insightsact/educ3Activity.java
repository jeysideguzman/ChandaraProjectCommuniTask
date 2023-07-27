package com.jeysi.chandaraproject.insightsact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jeysi.chandaraproject.R;

public class educ3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educ3);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}