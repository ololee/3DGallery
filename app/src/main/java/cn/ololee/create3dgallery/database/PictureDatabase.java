package cn.ololee.create3dgallery.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PictureEnity.class}, version = 1, exportSchema = true)
public abstract class PictureDatabase extends RoomDatabase {
  public static final String DATABASE_NAME = "picture_database";

  private static PictureDatabase instance;

  public static synchronized  PictureDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), PictureDatabase.class,
          DATABASE_NAME).build();
    }
    return instance;
  }

  public abstract PictureDao getPictureDao();
}
