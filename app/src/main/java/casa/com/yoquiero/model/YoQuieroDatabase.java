package casa.com.yoquiero.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by jose_ on 15/08/2018.
 */
@Database(entities = {Action.class}, version = 1)
public abstract class YoQuieroDatabase extends RoomDatabase {

    public abstract ActionDao actionDao();

    private static YoQuieroDatabase database;

    public static YoQuieroDatabase getAppDatabase(Context context) {
        if (database == null)
        {
            database = Room.databaseBuilder(context.getApplicationContext(), YoQuieroDatabase.class, "action-db")
                            .allowMainThreadQueries()
                            .build();
        }
        return database;
    }

    public static void destroyInstance()
    {
        database = null;
    }
}
