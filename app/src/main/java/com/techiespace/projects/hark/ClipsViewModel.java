package com.techiespace.projects.hark;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.techiespace.projects.hark.db.ClipDatabase;
import com.techiespace.projects.hark.db.Clips;
import com.techiespace.projects.hark.db.ClipsDao;

import java.util.List;

public class ClipsViewModel extends AndroidViewModel {

    private ClipsDao clipsDao;
    private LiveData<List<Clips>> clipsLiveData;
    private LiveData<List<Clips>> clipsLiveDataByLevel;

    public ClipsViewModel(@NonNull final Application application, String level) {
        super(application);
        clipsDao = ClipDatabase.getDatabase(application).clipsDao();
        //clipsLiveData = clipsDao.getAllClipsLiveData();
        clipsLiveDataByLevel = clipsDao.getClipsLiveDataByLevel(level);
    }

//    public LiveData<List<Clips>> getClipsLiveData() {
//        return clipsLiveData;
//    }

    public LiveData<List<Clips>> getClipsLiveDataByLevel() {
        return clipsLiveDataByLevel;
    }
}
