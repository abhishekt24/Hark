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

            //Rookie
            /*clipsDao.insertAll(
                    new Clips("RzqC2CtsKE0", "How Americans live", "1", "Rookie", "Russian/American", "F", "1:20 3:30"),
                    new Clips("arj7oStGLkU", "Inside the mind of a master procrastinator", "2", "Beginner", "American", "M", "2:10 2:50"),
                    new Clips("dfnCAmr569k", "Taylor Swift - End Game ft. Ed Sheeran, Future", "9", "Rap", "British", "M", "2:10 2:50"),
                    new Clips("TQMbvJNRpLE", "How to Achieve Your Most Ambitious Goals", "4", "Advanced", "US", "M", "1:17 1:32 1:32 1:45 4:18 4:27 4:27 4:40 4:40 4:50 4:50 5:06 16:57 17:43")
            );*/
            //Rookie L1,2
            clipsDao.insertAll(
                    new Clips("arj7oStGLkU", "Inside the mind of a master procrastinator", "2", "Rookie", "US?", "M", "00:12 00:19 00:20 00:34 00:35 00:50 03:55 04:10 06:15 06:42 06:50 07:29 12:36 13:54"),
                    new Clips("v5FL9VTBZzQ", "The power of creative constraints", "2", "Rookie", "Unknown", "M", "0:08 0:17 0:17 0:22 0:22 0:30 0:30 0:44 0:44 1:02 1:52 3:22 4:02 4:48")
            );
            //Beginner L3,4
            clipsDao.insertAll(
                    new Clips("rXhpK_lhonA", "How does Noble Peace Prize work?", "3", "Beginner", "?", "M", "00:09 00:29 00:29 01:10 01:52 02:12 02:21 02:44 03:08 03:23 03:24 03:44 3:44 4:50"),
                    new Clips("kak8PEl_v1I", "Start Your Day Right", "4", "Beginner", "Mixed", "Mixed", "00:04 00:22 00:27 00:48 00:48 1:17 1:26 1:51 03:01 3:32 5:02 5:15 05:16 06:13")
            );
            //Intermediate L5,6,7
            clipsDao.insertAll(
                    new Clips("MmfikLimeQ8", "Programming your mind for success", "5", "Intermediate", "?", "F", "3:43 3:53 3:58 4:09 4:09 4:32 10:07 10:53 10:53 11:20 12:25 12:56 14:34 15:20")
            );
            //Advanced L8,9
            clipsDao.insertAll(

            );
            //Rap Song L10
            clipsDao.insertAll(
                    new Clips("DVwaPJifStA", "Rap God Remix", "10", "Rap", "?", "M", "1:21 1:24 1:24 1:27 1:33 1:37 01:37 01:42 02:29 2:22 2:57 03:04 05:14 05:32")
            );
            return null;
        }
    }

}
