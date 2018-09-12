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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clips);
        recyclerViewMainClips = findViewById(R.id.recyclerview_main_clips);
        clipsAdapter = new ClipsAdapter(this);
        recyclerViewMainClips.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMainClips.setAdapter(clipsAdapter);

        clipsViewModel = ViewModelProviders.of(this).get(ClipsViewModel.class);
        clipsViewModel.getClipsLiveData().observe(ListClipsActivity.this, new Observer<List<Clips>>() {  //If we don't use live data, we need to execute queries in Async task in ViewModel
            @Override
            //even after that we get wierd probles like - recheck
            public void onChanged(@Nullable List<Clips> clipsList) {
                clipsAdapter.setClipsList(clipsList);
            }
        });
    }
}
