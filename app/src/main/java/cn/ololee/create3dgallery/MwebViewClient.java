package cn.ololee.create3dgallery;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MwebViewClient extends WebViewClient {

  @Nullable @Override
  public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
    Logger.d("shouldInterceptRequest: " + request.getUrl());
    if (request.getUrl().toString().contains("file://")) {
      File file = new File(request.getUrl().toString().replace("file://", ""));
      Logger.d("shouldInterceptRequest" + file.getAbsolutePath());
      if (file.exists()) {
        try {
          FileInputStream fis = new FileInputStream(file);
          return new WebResourceResponse("image/jpg", "UTF-8", fis);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    return super.shouldInterceptRequest(view, request);
  }

  @Override public void onLoadResource(WebView view, String url) {
    Logger.d("onLoadResource: " + url);
    super.onLoadResource(view, url);
  }
}
