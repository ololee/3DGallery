package cn.ololee.launcherlocalweb;

import android.content.Context;
import com.orhanobut.logger.Logger;
import org.nanohttpd.protocols.http.IHTTPSession;
import org.nanohttpd.protocols.http.NanoHTTPD;
import org.nanohttpd.protocols.http.response.Response;
import org.nanohttpd.protocols.http.response.Status;

public class WebServer extends NanoHTTPD {
  private Context context = null;

  public WebServer(Context context, int port) {
    super(port);
    this.context = context;
  }

  public WebServer(String hostname, int port) {
    super(hostname, port);
  }

  @Override protected Response serve(IHTTPSession session) {
    return super.serve(session);
  }

  @Override public Response handle(IHTTPSession session) {
    String uri = session.getUri().replaceFirst("/", "");

    String suffix = uri.substring(uri.lastIndexOf(".") + 1);
    String mimeType = SuffixUtils.getMimeTypeBySuffix(suffix);
    Logger.d("handle: " + uri + " mimeType: " + mimeType);
    try {
      return Response.newChunkedResponse(Status.OK, mimeType,
          AssetsUtils.getAssets(context, uri));
    } catch (Exception e) {
      e.printStackTrace();
      return Response.newChunkedResponse(Status.NOT_FOUND, mimeType, null);
    }
  }
}
