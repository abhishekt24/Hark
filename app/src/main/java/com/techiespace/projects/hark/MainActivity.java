package com.techiespace.projects.hark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerViewMain;
    public MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] listItems = {"Rookie", "Beginner", "Intermediate", "Advanced", "Rap Songs"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAdapter = new MainAdapter(this, listItems);
        recyclerViewMain = findViewById(R.id.recyclerview_main);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(mainAdapter);
    }
}
