package com.example.qsomafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void joinClass(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
    public void getTimetable(View v)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);

    }
}
