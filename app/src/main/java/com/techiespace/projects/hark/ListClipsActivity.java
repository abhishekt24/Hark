package com.techiespace.projects.hark;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.techiespace.projects.hark.db.Clips;

import java.util.List;

public class ListClipsActivity extends AppCompatActivity {

    public RecyclerView recyclerViewMainClips;
    public ClipsAdapter clipsAdapter;
    public ClipsViewModel clipsViewModel;
    String difficultyStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clips);

        difficultyStr = getIntent().getStringExtra("difficulty");

        recyclerViewMainClips = findViewById(R.id.recyclerview_main_clips);
        clipsAdapter = new ClipsAdapter(this);
        recyclerViewMainClips.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMainClips.setAdapter(clipsAdapter);

        clipsViewModel = ViewModelProviders.of(this, new ClipsViewModelFactory(this.getApplication(), difficultyStr)).get(ClipsViewModel.class);
        clipsViewModel.getClipsLiveDataByLevel().observe(ListClipsActivity.this, new Observer<List<Clips>>() {  //If we don't use live data, we need to execute queries in Async task in ViewModel
            @Override
            //even after that we get weird problems like - recheck
            public void onChanged(@Nullable List<Clips> clipsList) {
                clipsAdapter.setClipsList(clipsList);
            }
        });
    }
}
