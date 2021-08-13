package com.example.myapplication;
import com.example.myapplication.Database.databaseInterface;
import com.example.myapplication.Model.*;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {favDoge.class}, version = 1)
public abstract class createDatabase extends RoomDatabase {

    private static createDatabase instance;

    public abstract databaseInterface Dao();

    public static synchronized createDatabase getInstance(Context context) {

        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(),
                            createDatabase.class, "faveDoge_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(createDatabase instance) {
            databaseInterface dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
