package cn.ololee.launcherlocalweb;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

public class AssetsUtils {
  public static InputStream getAssets(Context context,String path)throws IOException {
    return context.getAssets().open(path);
  }
}
