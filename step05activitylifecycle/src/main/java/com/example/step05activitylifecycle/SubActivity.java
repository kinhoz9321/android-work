package com.example.step05activitylifecycle;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Log.e("SubActivity", "onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("SubActivity", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("SubActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("SubActivity", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("SubActivity", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("SubActivity", "onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("SubActivity", "onRestart()");
    }
}