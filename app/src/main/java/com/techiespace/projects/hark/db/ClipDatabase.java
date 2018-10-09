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

            //Rookie L1,2
            clipsDao.insertAll(
                    new Clips("pcF6IRpe-Og", "Halima Aden", "1", "Rookie", "--", "Female", "13592 29191 32255 39958 43250 53920 53945 61572 61596 69474 71716 78414 80326 98911", "0 0 0 0 0 0 0 0"),
                    new Clips("24Tzq9sdTas", "Yukiko Nakayama", "1", "Rookie", "--", "Female", "19072 29603 30642 41769 43052 55458 56605 69089 70727 81045 96974 114362 115397 150926", "0 0 0 0 0 0 0 0"),
                    new Clips("_VB39Jo8mAQ", "Adam Carroll - Money", "2", "Rookie", "--", "Male", "12170 25360 32810 41149 49030 56350 57500 65560 66920 73620 78950 88540 91450 109860", "0 0 0 0 0 0 0 0"),
                    new Clips("Ge7c7otG2mk", "Marianna Pascal", "2", "Rookie", "--", "Female", "22732 30234 30804 42631 46713 56299 58993 65038 65063 72809 81024 88816 89579 110352", "0 0 0 0 0 0 0 0"),
                    new Clips("rmeGVhhbGrM", "Mithila Palkar - Plan", "2", "Rookie", "--", "Female", "84206 91827 92318 99829 104490 113200 114431 125242 127131 140141 144334 152094 157514 184756", "0 0 0 0 0 0 0 0"),
                    new Clips("0oAJsPFH2wk", "Crystal Robello - Introvert", "2", "Rookie", "--", "Female", "9487 16225 21757 28261 28751 40394 40394 46250 46250 54266 54266 59762 60743 80785", "0 0 0 0 0 0 0 0"),
                    new Clips("hiM48j_NZOU", "Roxanne Pomerantz - Language", "2", "Rookie", "--", "Female", "12501 19263 20000 33757 34517 46154 46155 54571 59479 68803 70029 80796 80797 101275", "0 0 0 0 0 0 0 0"),
                    new Clips("arj7oStGLkU", "Tim Urban - Procrastination", "2", "Rookie", "--", "Male", "12000 19000 20000 34000 35000 50000 235000 250000 375000 402000 410000 449000 756000 834000", "0 0 0 0 0 0 0 0")
            );
            //Beginner L3,4
            clipsDao.insertAll(
                    new Clips("5qzy1fHYQNg", "Fred Krupp", "3", "Beginner", "--", "Male", "13230 21815 26712 36053 42046 49768 50498 59260 59284 68442 77592 86072 92673 118345", "0 0 0 0 0 0 0 0"),
                    new Clips("MY5SatbZMAo", "Mariana Atencio", "3", "Beginner", "--", "Female", "9070 19919 19999 29429 37740 46350 56649 63838 74850 82050 82050 89539 90279 116530", "0 0 0 0 0 0 0 0"),
                    new Clips("tGdsOXZpyWE", "Tasha Eurich", "3", "Beginner", "--", "Female", "12104 21853 33321 40243 44651 52973 53210 61894 62259 70289 70454 77972 78509 100499", "0 0 0 0 0 0 0 0"),
                    new Clips("5pW2b1vwwf4", "Andrea Pennington - Become who you are", "3", "Beginner", "--", "Female", "15128 24761 25127 36430 39342 47334 51498 60046 60462 69137 69484 76210 80796 104705", "0 0 0 0 0 0 0 0"),
                    new Clips("Bg_Q7KYWG1g", "Rick Rigsby - Dropout", "3", "Beginner", "--", "Male", "6114 17285 17285 27747 29623 38960 38960 48073 50436 57478 60461 68989 72759 88303", "0 0 0 0 0 0 0 0"),
                    new Clips("w-HYZv6HzAs", "Dr. Ivan Joseph - Self-confidence", "3", "Beginner", "--", "Male", "6955 12109 12109 18869 18870 24298 26085 31369 31370 36863 36863 42620 42620 66267", "0 0 0 0 0 0 0 0"),
                    new Clips("TQMbvJNRpLE", "Stephen Duneier - Goals", "4", "Beginner", "--", "Male", "10134 17535 17964 24591 27207 34620 37242 44623 55245 63128 67552 74271 78167 98092", "0 0 0 0 0 0 0 0"),
                    new Clips("FwLeiY5f7sI", "Jennice Vilhauer - Expectation", "4", "Beginner", "--", "Female", "9867 22643 25932 31754 32203 38598 44820 54868 55228 63736 67478 73322 74232 102678", "0 0 0 0 0 0 0 0"),
                    new Clips("9fLlkOMrMq4", "April Qu - Reading", "4", "Beginner", "--", "Female", "24739 33823 34293 45243 51524 62709 62709 73298 76769 83919 84729 95339 101190 123768", "0 0 0 0 0 0 0 0"),
                    new Clips("AhUhpfKeLus", "Anthony D. Romero", "4", "Beginner", "--", "Male", "12756 20060 20899 33756 36980 47196 50500 59940 61980 71020 86100 97380 98100 118996", "0 0 0 0 0 0 0 0")
            );
            /*//Intermediate L5,6,7
            clipsDao.insertAll(
                    new Clips("MmfikLimeQ8", "Programming your mind for success", "5", "Intermediate", "?", "F", "3:43 3:53 3:58 4:09 4:09 4:32 10:07 10:53 10:53 11:20 12:25 12:56 14:34 15:20")
            );
            //Advanced L8,9
            clipsDao.insertAll(

            );
            //Rap Song L10
            clipsDao.insertAll(
                        new Clips("DVwaPJifStA", "Rap God Remix", "10", "Rap", "?", "M", "1:21 1:24 1:24 1:27 1:33 1:37 01:37 01:42 02:29 2:22 2:57 03:04 05:14 05:32")
            );*/
            return null;
        }
    }

}
