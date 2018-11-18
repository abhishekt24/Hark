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
                    new Clips("pWWBn9XXZoc", "Business - Android", "1", "Rookie", "American", "Male", "659 7958 7959 20980 23480 33390 37989 49250 55430 65430 65430 74820 79100 100090", "0 0 0 0 0 0 0 0"),
                    new Clips("Ge7c7otG2mk", "Marianna Pascal", "2", "Rookie", "--", "Female", "22732 30234 30804 42631 46713 56299 58993 65038 65063 72809 81024 88816 89579 110352", "0 0 0 0 0 0 0 0"),
                    new Clips("rmeGVhhbGrM", "Mithila Palkar - Plan", "2", "Rookie", "--", "Female", "84206 91827 92318 99829 104490 113200 114431 125242 127131 140141 144334 152094 157514 184756", "0 0 0 0 0 0 0 0"),
                    new Clips("0oAJsPFH2wk", "Crystal Robello - Introvert", "2", "Rookie", "--", "Female", "9487 16225 21757 28261 28751 40394 40394 46250 46250 54266 54266 59762 60743 80785", "0 0 0 0 0 0 0 0"),
                    new Clips("hiM48j_NZOU", "Roxanne Pomerantz - Language", "2", "Rookie", "--", "Female", "12501 19263 20000 33757 34517 46154 46155 54571 59479 68803 70029 80796 80797 101275", "0 0 0 0 0 0 0 0"),
                    new Clips("arj7oStGLkU", "Tim Urban - Procrastination", "2", "Rookie", "--", "Male", "12000 19000 20000 34000 35000 50000 235000 250000 375000 402000 410000 449000 756000 834000", "0 0 0 0 0 0 0 0"),
                    new Clips("devo3kdSPQY", "How does Clutch work?", "2", "Rookie", "--", "Male", "30 10260 12730 23549 26410 32279 34090 44639 51550 58739 59019 72260 72260 96139", "0 0 0 0 0 0 0 0"),
                    new Clips("rvskMHn0sqQ", "In a Nutshell - Egoistic Altruism", "2", "Rookie", "--", "Male", "1060 7920 11300 19340 20020 30660 36080 44180 44960 51740 52040 59260 59900 84480", "0 0 0 0 0 0 0 0"),
                    new Clips("f6VoaXYNx0I", "English Tutor", "2", "Rookie", "--", "Female", "99 10200 10200 18800 18800 30400 46690 57170 57170 70530 71530 80659 80659 107130", "0 0 0 0 0 0 0 0"),
                    new Clips("CAU2zx2Ri_M", "Speaking Skills", "2", "Rookie", "--", "Male", "440 12289 12289 23810 23810 35579 35579 47350 47350 61170 69030 79400 79400 100369", "0 0 0 0 0 0 0 0"),
                    new Clips("a49RcxrJ0xY", "English learning plan", "2", "Rookie", "--", "Female", "1170 8050 11800 21779 28029 38350 41049 52468 52469 60350 60350 70280 70280 93290", "0 0 0 0 0 0 0 0"),
                    new Clips("5pW2b1vwwf4", "Andrea Pennington - Become who you are", "2", "Rookie", "--", "Female", "15128 24761 25127 36430 39342 47334 51498 60046 60462 69137 69484 76210 80796 104705", "0 0 0 0 0 0 0 0"),
                    new Clips("3J-cYxxHQGQ", "Sadhguru - Stress", "2", "Rookie", "--", "Male", "5830 12809 12809 19580 21189 36840 37480 50369 51280 67019 67360 78900 79540 87029", "0 0 0 0 0 0 0 0"));

            //Beginner L3,4
            clipsDao.insertAll(
                    new Clips("5qzy1fHYQNg", "Fred Krupp", "3", "Beginner", "--", "Male", "13230 21815 26712 36053 42046 49768 50498 59260 59284 68442 77592 86072 92673 118345", "0 0 0 0 0 0 0 0"),
                    new Clips("EU-eQpCqBXQ", "Domics - Buying Used Things", "3", "Beginner", "American", "Male", "5500 13360 13520 22680 22680 29460 29640 38340 38400 44980 44980 50460 50460 67800", "0 0 0 0 0 0 0 0"),
                    new Clips("tGdsOXZpyWE", "Tasha Eurich", "3", "Beginner", "--", "Female", "12104 21853 33321 40243 44651 52973 53210 61894 62259 70289 70454 77972 78509 100499", "0 0 0 0 0 0 0 0"),
                    new Clips("Bg_Q7KYWG1g", "Rick Rigsby - Dropout", "3", "Beginner", "--", "Male", "6114 17285 17285 27747 29623 38960 38960 48073 50436 57478 60461 68989 72759 88303", "0 0 0 0 0 0 0 0"),
                    new Clips("w-HYZv6HzAs", "Dr. Ivan Joseph - Self-confidence", "3", "Beginner", "--", "Male", "6955 12109 12109 18869 18870 24298 26085 31369 31370 36863 36863 42620 42620 66267", "0 0 0 0 0 0 0 0"),
                    new Clips("MY5SatbZMAo", "Mariana Atencio", "3", "Beginner", "--", "Female", "9070 19919 19999 29429 37740 46350 56649 63838 74850 82050 82050 89539 90279 116530", "0 0 0 0 0 0 0 0"),
                    new Clips("H_SXtJf2oz4", "Business - Microsoft saved Apple", "3", "Beginner", "--", "Male", "5089 15870 17750 30570 30570 38000 38000 47460 49150 56649 56649 64640 64640 85700", "0 0 0 0 0 0 0 0"),
                    new Clips("jAhjPd4uNFY", "Genetic Engineering", "3", "Beginner", "--", "Male", "19860 27520 28100 36040 56240 64140 64420 73640 73640 79740 80420 87260 88480 109120", "0 0 0 0 0 0 0 0"),
                    new Clips("dGiQaabX3_o", "Human Origins", "3", "Beginner", "--", "Male", "760 8900 9400 18380 18540 29560 29740 39840 49000 56400 62200 75020 75940 97640", "0 0 0 0 0 0 0 0"),
                    new Clips("I2O7blSSzpI", "An Internet Mystery", "3", "Beginner", "--", "Male", "5780 14940 14940 23700 23700 31680 38680 47718 47720 57960 62900 70760 102900 134540", "0 0 0 0 0 0 0 0"),
                    new Clips("Y7WsX9ZEbNU", "Is Gemstone Really Works", "3", "Beginner", "--", "Male", "60 13290 13290 25460 25460 41190 41190 49829 49829 79630 79630 96150 96150 122229", "0 0 0 0 0 0 0 0"),
                    new Clips("FwLeiY5f7sI", "Jennice Vilhauer - Expectation", "4", "Beginner", "--", "Female", "9867 22643 25932 31754 32203 38598 44820 54868 55228 63736 67478 73322 74232 102678", "0 0 0 0 0 0 0 0"),
                    new Clips("9fLlkOMrMq4", "April Qu - Reading", "4", "Beginner", "--", "Female", "24739 33823 34293 45243 51524 62709 62709 73298 76769 83919 84729 95339 101190 123768", "0 0 0 0 0 0 0 0"),
                    new Clips("AhUhpfKeLus", "Anthony D. Romero", "4", "Beginner", "--", "Male", "12756 20060 20899 33756 36980 47196 50500 59940 61980 71020 86100 97380 98100 118996", "0 0 0 0 0 0 0 0"),
                    new Clips("TQMbvJNRpLE", "Stephen Duneier - Ambitious goals", "4", "Beginner", "--", "Male", "10134 17535 17964 24591 27207 34620 37242 44623 55245 63128 67552 74271 78167 98092", "0 0 0 0 0 0 0 0"),
                    new Clips("iO6lvhUFLJY", "Olympia LePoint - Failure to Success", "4", "Beginner", "--", "Female", "0 10110 20279 27599 31189 41250 122820 133200 192209 201019 201020 212750 223990 244629", "0 0 0 0 0 0 0 0"),
                    new Clips("A-QgGXbDyR0", "Netta Schramm - Perpetual motion machines", "4", "Beginner", "American", "Male", "8306 19996 19996 30217 30217 37566 46737 57371 57371 65470 65470 72829 76250 97440", "0 0 0 0 0 0 0 0"),
                    new Clips("YV8TT9LRBrY", "Elizabeth Cox - Is fire a solid, a liquid, or a gas?", "3", "Beginner", "American", "Male", "7017 14437 17107 25938 25938 33068 35418 43418 47368 55988 55988 68558 73378 99658", "0 0 0 0 0 0 0 0"),
                    new Clips("ao8L-0nSYzg", "Addiction", "3", "Beginner", "--", "Male", "643 7446 10225 17904 20449 28071 35128 42023 42483 51932 52582 59502 60272 80274", "0 0 0 0 0 0 0 0"),
                    new Clips("lrEkYscgbqE", "Jawaharlal Nehru", "4", "Beginner", "--", "Male", "14840 32890 32890 56210 64930 79040 80360 93180 95860 105159 106840 124100 127529 158239", "0 0 0 0 0 0 0 0"),
                    new Clips("_VB39Jo8mAQ", "Adam Carroll - Money", "4", "Beginner", "--", "Male", "12170 25360 32810 41149 49030 56350 57500 65560 66920 73620 78950 88540 91450 109860", "0 0 0 0 0 0 0 0")

            );
            //Intermediate L5,6,7
            clipsDao.insertAll(
                    new Clips("VzPD009qTN4", "In a Nutshell - Bacteria", "5", "Intermediate", "--", "Male", "1300 11160 14740 23419 28080 48300 48300 55120 55120 61320 63920 76100 76100 95119", "0 0 0 0 0 0 0 0"),
                    new Clips("5KyI9zhYA10", "Simon Sinek", "5", "Intermediate", "American", "Male", "820 9480 48410 63390 232049 241590 267270 273870 302310 311520 399050 409780 447880 469340", "0 0 0 0 0 0 0 0"),
                    new Clips("5EPLOj7N1A4", "Will Smith - Life", "5", "Intermediate", "American", "Male", "0 7168 7870 13380 13509 25140 25840 30269 46210 58349 58719 72928 75549 99750", "0 0 0 0 0 0 0 0"),
                    new Clips("yKzm2AyoZjI", "Elizabeth Olsen & Jimmy Fallon", "5", "Intermediate", "American", "Female", "30896 37270 39004 46011 49014 52885 52885 58591 58591 65698 65698 69836 78544 100232", "0 0 0 0 0 0 0 0"),
                    new Clips("vLaErMfgJ8Q", "Infographics - Death", "5", "Intermediate", "--", "Male", "329 6229 6230 13440 14849 23009 23010 29250 29250 37360 47450 54489 54490 74399", "0 0 0 0 0 0 0 0"),
                    new Clips("RS7IzU2VJIQ", "In a Nutshell - Plastic Pollution", "5", "Intermediate", "--", "Male", "840 12040 12490 22259 23160 36239 36880 45480 46300 55200 65100 71900 72120 102259", "0 0 0 0 0 0 0 0"),
                    new Clips("XxutY7ss1v4", "In a Nutshell - European Union", "5", "Intermediate", "--", "Male", "540 10860 11940 32580 33560 40040 44520 56380 57660 63199 64220 72159 72680 93799", "0 0 0 0 0 0 0 0"),
                    new Clips("y9c8oZ49pFc", "Veritasium - Shadows", "5", "Intermediate", "American", "Male", "3160 12900 14900 22840 23060 31600 35760 44780 45020 53569 54120 60140 60320 77920", "0 0 0 0 0 0 0 0"),
                    new Clips("0POHnKF2pH4", "Ellen - 5 year old", "5", "Intermediate", "--", "Female", "260 8370 8370 13480 16420 24991 28620 35274 41650 47710 47710 54325 56345 73826", "0 0 0 0 0 0 0 0"),
                    new Clips("uiqVRv9OMP0", "Trevor Noah- Stephen Colbart", "5", "Intermediate", "--", "Male", "3069 10577 10577 33066 33066 41241 41241 46346 46346 50683 52919 62562 66032 83550", "0 0 0 0 0 0 0 0"),
                    new Clips("mk3K_Vrve-E", "Scilla Elworthy - Non-violence", "5", "Intermediate", "--", "Male", "15620 26985 30486 37653 46467 60148 63164 74965 82434 88949 88949 95914 95914 121498", "0 0 0 0 0 0 0 0"),
                    new Clips("uAd1WJ9gXo0", "Navy Seal Admiral William H. McRaven", "6", "Intermediate", "American", "Male", "6580 14709 14709 23269 23269 28759 28759 36840 38750 43700 49820 56559 56560 73298", "0 0 0 0 0 0 0 0"),
                    new Clips("MlPd6zWjd_0", "The Tonight Show Starring Jimmy Fallon", "6", "Intermediate", "---", "Male", "4704 9475 10809 14914 19285 29028 31164 36736 36736 42241 45378 49982 49982 65531", "0 0 0 0 0 0 0 0"),
                    new Clips("4vMCbXdWEnw", "Priyanka Chopra", "6", "Intermediate", "--", "Both", "6300 11533 11533 33766 35466 39766 39766 47333 49133 53833 53833 63033 63033 83333", "0 0 0 0 0 0 0 0"),
                    new Clips("v76B8GUYflk", "Trevor Noah", "6", "Intermediate", "--", "Male", "4060 11940 13440 20550 66720 72040 72040 79439 85689 97400 97400 102500 108450 112688", "0 0 0 0 0 0 0 0"),
                    new Clips("gkjW9PZBRfk", "Emma Watson - HeForShe", "6", "Intermediate", "--", "Female", "0 6600 6600 15559 15560 27560 27600 35320 53740 67280 68960 78400 79580 104080", "0 0 0 0 0 0 0 0"),
                    new Clips("lqbL-UhhyPk", "My Battle With Anorexia", "6", "Intermediate", "--", "Male", "7717 17869 22521 32080 32081 42627 46618 54419 56386 63284 63285 74956 74957 92500", "0 0 0 0 0 0 0 0"),
                    new Clips("O3kfYEo7i9U", "Business - YouTube", "6", "Intermediate", "American", "Male", "8590 17890 17890 28250 28250 36550 36550 46499 46499 52499 52499 59399 59399 75780", "0 0 0 0 0 0 0 0"),
                    new Clips("2yFCyPX3kT0", "Annoying Customers", "6", "Intermediate", "--", "Male", "0 6519 6520 11960 14440 19500 19500 25500 25500 29740 29740 34680 34680 49160", "0 0 0 0 0 0 0 0"),
                    new Clips("t-MTeLbrt8Y", "Infographic Show - Zombies", "6", "Intermediate", "--", "Male", "160 8230 8230 16930 19160 26210 26210 32920 32920 40050 40050 45280 45280 60038", "0 0 0 0 0 0 0 0"),
                    new Clips("jOJLx4Du3vU", "Roger Frampton", "6", "Intermediate", "--", "Male", "7402 15773 15773 23754 24644 32115 33127 42585 43555 54249 54865 60292 60722 84536", "0 0 0 0 0 0 0 0")
            );
            //Advanced L8,9
            clipsDao.insertAll(
                    new Clips("nShlloNgM2E", "Gary Vaynerchuk - Millennials", "7", "Advanced", "American", "Male", "0 8760 172410 180450 402740 410000 451590 462639 490270 502360 502360 513520 627920 659750", "0 0 0 0 0 0 0 0"),
                    new Clips("iuNJLtj10Lg", "Elon Musk - Advice", "7", "Advanced", "American", "Male", "6600 15000 31000 38199 43000 50000 50024 60850 61200 73500 74200 82399 123600 150299", "0 0 0 0 0 0 0 0"),
                    new Clips("eRLJscAlk1M", "Prince Ea  - Dear Future Generations: Sorry", "7", "Advanced", "--", "Male", "8920 25019 26020 36899 36900 45748 46360 54060 56140 64139 64140 75780 76810 105850", "0 0 0 0 0 0 0 0"),
                    new Clips("BxY_eJLBflk", "Put God First - Denzel Washington", "7", "Advanced", "--", "Male", "9480 29609 30429 41759 42989 51840 53260 61979 61989 69419 70060 75030 75030 97069", "0 0 0 0 0 0 0 0"),
                    new Clips("-P-Bk-Sm9dY", "Perfect English!", "7", "Advanced", "--", "Male", "39 7479 7480 13349 13599 19618 20350 35909 45460 57090 68530 77580 78250 102629", "0 0 0 0 0 0 0 0"),
                    new Clips("EiTrl0W1QrM", "soft power - Shashi Tharoor", "7", "Advanced", "--", "Male", "15260 23260 27260 33260 35260 43260 45260 54260 54260 61260 61260 67260 67260 88260", "0 0 0 0 0 0 0 0"),
                    new Clips("dqTTojTija8", "Prince Ea - School System", "8", "Advanced", "--", "Male", "30 7588 8470 17520 17800 25470 25990 33660 34090 41099 41100 49799 50620 71430", "0 0 0 0 0 0 0 0"),
                    new Clips("_PsLRgEYf9E", "Prince Ea - School", "8", "Advanced", "--", "Male", "5000 15766 15766 23617 25718 33968 35796 44726 44726 52178 52179 60524 60524 80743", "0 0 0 0 0 0 0 0"),
                    new Clips("dRl8EIhrQjQ", "Prince Ea - Auto-Correct Humanity", "8", "Advanced", "--", "Male", "1520 12819 14509 24999 24999 32168 33140 45730 46370 58749 58750 65319 65869 90970", "0 0 0 0 0 0 0 0"),
                    new Clips("kjCh1u8SQlE", "Drop the Mic v. David Schwimmer and Rebel Wilson", "8", "Advanced", "--", "Male", "6306 23556 23556 36468 45011 67100 67100 74941 74941 85185 85185 93960 93960 126092", "0 0 0 0 0 0 0 0"),
                    new Clips("5cn4A5IZg1k", "Scenes with Benedict Cumberbatch", "8", "Advanced", "--", "Male", "1170 6360 6360 10600 14720 16970 16970 21830 21830 27670 27670 34540 34540 47860", "0 0 0 0 0 0 0 0"),
                    new Clips("F_ARj13CwWA", "Benedict Cumberbatch & Keira Knightley", "8", "Advanced", "--", "Male", "79680 87810 114570 122230 128820 137290 153740 162660 182320 190209 249940 256018 341340 357340", "0 0 0 0 0 0 0 0")
            );
            //Rap Song L10
            clipsDao.insertAll(
                    new Clips("7Oxz060iedY", "Eric Thomas - You owe you", "9", "Rap", "--", "Male", "9526 14320 15783 20192 22489 27608 27608 32971 32971 40127 44678 53957 53957 74073", "0 0 0 0 0 0 0 0"),
                    new Clips("h6katuaR5l4", "Eric Thomas & Gary Vee", "9", "Rap", "--", "Male", "25196 41934 41934 48718 59797 66307 71057 77437 77437 81837 89238 98611 107957 131017", "0 0 0 0 0 0 0 0"),
                    new Clips("HwXsFPZp3fQ", "Gary Vee - Advice", "9", "Rap", "--", "Male", "3370 9489 9489 17117 20287 27127 38405 43677 43677 50217 55055 60994 60994 77248", "0 0 0 0 0 0 0 0"),
                    new Clips("UTy8g93Trsk", "Gary Vee - Being an Entrepreneur", "9", "Rap", "--", "Male", "12913 24491 24491 30897 38071 48782 50250 56857 60661 70070 71371 76343 80280 96797", "0 0 0 0 0 0 0 0"),
                    new Clips("DEEwb1coDFc", "Gary Vee - Instagram", "9", "Rap", "--", "Male", "26160 33600 60090 68280 84720 91140 91140 98130 237810 244560 287380 294220 370350 415360", "0 0 0 0 0 0 0 0"),
                    new Clips("0Eu2w5X6w0w", "The Jon Snow Accent", "9", "Rap", "--", "Male", "4860 24060 24460 32180 32500 44220 44480 53040 53740 66040 66520 84439 84580 113110", "0 0 0 0 0 0 0 0"),
                    new Clips("sykacDdWQj4", "Luis Storytelling About Tip", "9", "Rap", "--", "Male", "17297 38185 38185 43378 43378 51358 51358 56423 56423 63138 63777 70819 70819 91782", "0 0 0 0 0 0 0 0"),
                    new Clips("ExKCcndqK5c", "Talking Fast With a Record", "10", "Rap", "--", "Male", "6815 10606 11948 17804 20055 28934 28934 32439 32439 46646 46646 50923 53789 69120", "0 0 0 0 0 0 0 0"),
                    new Clips("DfF0VlDDyzg", " BLACKPINK JENNIE RAP", "10", "Rap", "--", "Female", "3220 10820 10995 16520 17340 23800 23800 30220 30300 39260 40760 46920 46920 64500", "0 0 0 0 0 0 0 0"),
                    new Clips("b3o3_bccw20", "Mike Shinoda ft. K.Flay", "10", "Rap", "--", "Male", "6806 16416 16449 28627 28661 36369 36402 45645 45678 55655 55688 64963 64997 91189", "0 0 0 0 0 0 0 0")
            );
            return null;
        }
    }

}

