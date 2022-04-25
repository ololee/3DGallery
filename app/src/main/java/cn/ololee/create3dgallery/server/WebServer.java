package cn.ololee.create3dgallery.server;

import android.content.Context;
import cn.ololee.create3dgallery.database.PictureDatabase;
import cn.ololee.create3dgallery.database.PictureEnity;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

import static android.media.CamcorderProfile.get;

public class WebServer extends NanoHTTPD {
  private Context context;
  public WebServer(Context context,int port) {
    super(port);
    this.context = context;
  }

  @Override protected Response serve(IHTTPSession session) {
    String uri = session.getUri();
    Logger.d("server uri: " + uri);
    if (uri.equals("/")) {
      return Response.newFixedLengthResponse(Status.OK, "text/html", getIndexHtml());
    } else if (uri.startsWith("/file/")) {
      Logger.d("server file");
      String filePath = uri.replace("/file", "");
      File file = new File(filePath);
      if (file.exists()) {
        Logger.d("server file exists");
        try {
          return Response.newChunkedResponse(Status.OK, "image/jpeg", new FileInputStream(file));
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    return Response.newFixedLengthResponse("Hello, World!");
  }

  private String getIndexHtml() {
    return  PictureDatabase.getInstance(context).getPictureDao().getLastPicture().path;
  }
}
