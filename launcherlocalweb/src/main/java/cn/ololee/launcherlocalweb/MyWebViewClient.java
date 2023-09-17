package cn.ololee.launcherlocalweb;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import com.orhanobut.logger.Logger;

public class MyWebViewClient extends WebViewClient {

  @Nullable @Override
  public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
    Logger.d("shouldInterceptRequest: " + request.getUrl());
    return super.shouldInterceptRequest(view, request);
  }

  @Override public void onLoadResource(WebView view, String url) {
    super.onLoadResource(view, url);
    Logger.d("onLoadResource: " + url);
  }
}
