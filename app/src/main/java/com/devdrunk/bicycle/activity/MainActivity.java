package com.devdrunk.bicycle.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devdrunk.bicycle.R;
import com.devdrunk.bicycle.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }
    }
}
