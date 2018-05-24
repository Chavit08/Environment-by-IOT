package com.example.chavit.projectenvnew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1 * 1000);
                    Intent i = new Intent(getApplicationContext(), Viewinformation.class);
                    startActivity(i);
                    finish();

                } catch (Exception ex) {
                }
            }
        };
        thread.start();
    }
}



