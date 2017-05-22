package com.example.shaysheli.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    public static Activity mainActivity;
    public static int RESAULT_CHANGED = 200;
    public static int RESAULT_DEL = 300;
    public static int RESAULT_KEEP = 404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        Intent intent = new Intent(this, StudentList.class);
        startActivity(intent);
    }
}
