package cn.ololee.launcherlocalweb;

import java.util.HashMap;

public class SuffixUtils {

  private static HashMap<String, String> mimeMap = new HashMap<>();

  static {
    mimeMap.put("html", "text/html");
    mimeMap.put("css", "text/css");
    mimeMap.put("js", "application/javascript");
    mimeMap.put("png", "image/png");
    mimeMap.put("jpg", "image/jpeg");
  }

  public static String getMimeTypeBySuffix(String suffix) {
    return mimeMap.get(suffix);
  }
}
