package com.techiespace.projects.hark;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.techiespace.projects.hark.db.ClipDatabase;
import com.techiespace.projects.hark.db.Clips;
import com.techiespace.projects.hark.db.ClipsDao;

import java.util.List;

public class SectionsViewModel extends AndroidViewModel {

    private LiveData<List<Clips>> accuracyLiveData;

    public SectionsViewModel(@NonNull Application application, String vidId) {
        super(application);
        ClipsDao clipsDao = ClipDatabase.getDatabase(application).clipsDao();
        accuracyLiveData = clipsDao.findLiveAccById(vidId);
    }

    public LiveData<List<Clips>> getAccuracyLiveData() {
        return accuracyLiveData;
    }
}