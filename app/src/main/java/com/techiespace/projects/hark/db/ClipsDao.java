package com.techiespace.projects.hark.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ClipsDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insertAll(Clips... clips);

/*    @Query("SELECT * FROM Clips")
    LiveData<List<Clips>> getAllClipsLiveData();*/

    @Query("SELECT * FROM Clips WHERE difficulty = :difficulty")
    LiveData<List<Clips>> getClipsLiveDataByLevel(String difficulty);

    @Query("DELETE FROM Clips")
    void deleteAll();

    @Query("UPDATE Clips SET accuracy = :acc WHERE clipID = :id")
    int updateAcc(String acc, String id);

    @Query("SELECT accuracy FROM Clips WHERE clipID = :id LIMIT 1")
    String findAccById(String id);

    @Query("SELECT * FROM Clips WHERE clipID LIKE :id")
    LiveData<List<Clips>> findLiveAccById(String id);
}