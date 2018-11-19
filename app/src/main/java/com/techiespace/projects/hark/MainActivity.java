package com.techiespace.projects.hark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerViewMain;
    public MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] listItems = {"Rookie", "Beginner", "Intermediate", "Pro", "Rap"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainAdapter = new MainAdapter(this, listItems);
        recyclerViewMain = findViewById(R.id.recyclerview_main);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(mainAdapter);

//        Toolbar myToolbar = findViewById(R.id.too);
//        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return true;
    }
}
