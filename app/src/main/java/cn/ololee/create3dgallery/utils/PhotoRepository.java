package cn.ololee.create3dgallery.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import cn.ololee.create3dgallery.entry.Photo;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {

  private static final String[] STORE_IMAGES = {
      MediaStore.Images.Media._ID,
      MediaStore.Images.Media.DATA,
      MediaStore.Images.Media.DISPLAY_NAME,
      MediaStore.Images.ImageColumns.MIME_TYPE,
      MediaStore.Images.Media.SIZE,
  };

  public static List<Photo> getPhoto(Context context) {
    Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    ContentResolver contentResolver = context.getContentResolver();
    String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " desc";
    Cursor cursor = contentResolver.query(uri, STORE_IMAGES, null, null, sortOrder);

    List<Photo> result = new ArrayList<>();

    while (cursor != null && cursor.moveToNext()) {
      Photo photo = new Photo();

      photo.setId(cursor.getLong(0));
      photo.setPath(cursor.getString(1));
      photo.setName(cursor.getString(2));
      photo.setMimetype(cursor.getString(3));
      photo.setSize(cursor.getLong(4));

      result.add(photo);
    }

    if (cursor != null) cursor.close();

    Logger.d("getPhoto: size=" + result.size());

    return result;
  }
}
