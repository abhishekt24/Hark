package com.techiespace.projects.hark.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Clips.class}, version = 1, exportSchema = false)
public abstract class ClipDatabase extends RoomDatabase {


    private static final String DB_NAME = "clips.db";
    private static ClipDatabase INSTANCE = null;

    public static ClipDatabase getDatabase(final Context context) {    //https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#6
        if (INSTANCE == null) {
            synchronized (ClipDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ClipDatabase.class, DB_NAME)//.allowMainThreadQueries()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    {
                                        super.onCreate(db);
                                        Log.d("org.db", "onCreate: Populating with data...");
                                        new PopulateDbAsync(INSTANCE).execute();
                                    }
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ClipsDao clipsDao();

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ClipsDao clipsDao;

        PopulateDbAsync(ClipDatabase instance) {
            clipsDao = instance.clipsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clipsDao.deleteAll();
            clipsDao.insertAll(
                    new Clips("RzqC2CtsKE0", "How Americans live", "1", "Russian/American", "F", "1:20 3:30"),
                    new Clips("arj7oStGLkU", "Inside the mind of a master procrastinator", "2", "American", "M", "2:10 2:50"),
                    new Clips("dfnCAmr569k", "Taylor Swift - End Game ft. Ed Sheeran, Future", "9", "British", "M", "2:10 2:50"),
                    new Clips("TQMbvJNRpLE", "How to Achieve Your Most Ambitious Goals", "4", "US", "M", "1:17 1:32 1:32 1:45 4:18 4:27 4:27 4:40 4:40 4:50 4:50 5:06 16:57 17:43"));
            return null;
        }
    }

}
