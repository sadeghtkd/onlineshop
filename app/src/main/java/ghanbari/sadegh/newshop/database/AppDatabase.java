package ghanbari.sadegh.newshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Basket.class}, version = 4)
public abstract  class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME= "myshop3_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext() , AppDatabase.class, DATABASE_NAME ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract BasketDAO basketDAO();

}
