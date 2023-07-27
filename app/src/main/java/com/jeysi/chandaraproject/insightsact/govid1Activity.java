package com.jeysi.chandaraproject.insightsact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jeysi.chandaraproject.R;

public class govid1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govid1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}