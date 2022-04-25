package cn.ololee.create3dgallery;

import android.app.Application;
import cn.ololee.create3dgallery.server.WebServer;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import java.io.IOException;

public class App extends Application {
  public static WebServer  server;

  @Override public void onCreate() {
    super.onCreate();
    PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(false)
        .methodCount(0)
        .methodOffset(2)
        .build();
    AndroidLogAdapter androidLogAdapter = new AndroidLogAdapter(strategy);
    Logger.addLogAdapter(androidLogAdapter);
    server = new WebServer(this,8081);
    try {
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public void onTerminate() {
    server.stop();
    super.onTerminate();
  }
}
