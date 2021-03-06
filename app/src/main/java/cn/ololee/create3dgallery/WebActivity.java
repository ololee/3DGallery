package cn.ololee.create3dgallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.create3dgallery.database.PictureDatabase;
import cn.ololee.create3dgallery.database.PictureEnity;
import cn.ololee.create3dgallery.databinding.ActivityWebBinding;
import cn.ololee.create3dgallery.entry.Photo;
import cn.ololee.create3dgallery.server.WebServer;
import cn.ololee.create3dgallery.utils.AssetsHelper;
import cn.ololee.create3dgallery.utils.FileUtils;
import cn.ololee.create3dgallery.utils.RandomUtils;
import cn.ololee.localserver.NativeLib;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class WebActivity extends AppCompatActivity {
  public static final String PHOTO_LIST = "PHOTO_LIST";
  private List<String> photoList;
  private ActivityWebBinding binding;
  private String webPageData;

  public static void start(Context context, List<Photo> photos) {
    Intent intent = new Intent(context, WebActivity.class);
    ArrayList<String> photoPaths = new ArrayList<>(photos.size());
    for (Photo photo : photos) {
      photoPaths.add(photo.getPath());
    }
    intent.putStringArrayListExtra(PHOTO_LIST, photoPaths);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityWebBinding.inflate(getLayoutInflater());




    setContentView(binding.getRoot());
    if (getSupportActionBar() != null) {
      getSupportActionBar().hide();
    }
    if (getIntent() != null) {
      initPhotoList();
      if (photoList != null && !photoList.isEmpty()) {
        initWebView();
      }
    }
  }

  private void initPhotoList() {
    photoList = getIntent().getStringArrayListExtra(PHOTO_LIST);
  }

  private void formatPhotoList() {
    String s = AssetsHelper.readAssetsFile(this, "index.html");
    String ip = getIPAddress(this);
    Logger.d("formatPhotoList ip = " + ip);
    StringBuilder sb = new StringBuilder();
    //String fileDir = getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
    for (int i = 0; i < 3; i++) {
      List<Integer> randomList = RandomUtils.getRandomList(photoList.size());
      for (Integer  photoIndex : randomList) {
        try {
          //File destFile = new File(fileDir, photo.substring(photo.lastIndexOf("/") + 1));
          //FileUtils.copyFile(new File(photo), destFile);
          sb.append("<img class=\"f"+(i+1)+"\" src=\"http://"+ip+":8081/file").append(photoList.get(photoIndex)).append("\"/>\n");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    webPageData = s.replace("<!-- insert photo here -->", sb.toString());
    Logger.d("formatPhotoList" + webPageData);
  }

  private void initWebView() {
    formatPhotoList();
    new Thread(() -> PictureDatabase.getInstance(WebActivity.this).getPictureDao().insertPicture(new PictureEnity(
        (int) (System.currentTimeMillis()),"1",webPageData))).start();
    binding.threeGalleryWebview.loadData(webPageData, "text/html", "UTF-8");
    //binding.threeGalleryWebview.setWebViewClient(new MwebViewClient());
    binding.threeGalleryWebview.getSettings().setJavaScriptEnabled(true);
    binding.threeGalleryWebview.getSettings().setAllowFileAccess(true);
    binding.threeGalleryWebview.getSettings().setDomStorageEnabled(true);
    binding.threeGalleryWebview.getSettings().setAllowContentAccess(true);
    binding.threeGalleryWebview.getSettings().setAllowFileAccessFromFileURLs(true);
    binding.threeGalleryWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
    binding.threeGalleryWebview.getSettings().setLoadsImagesAutomatically(true);


  }

  /**??????IP????????????????????????????????????wifi???????????????????????????????????????ip?????????????????????*/
  String getIPAddress(Context context) {
    NetworkInfo info = ((ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    if (info != null && info.isConnected()) {
      if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//????????????2G/3G/4G??????
        try {
          //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
          for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
              InetAddress inetAddress = enumIpAddr.nextElement();
              if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                return inetAddress.getHostAddress();
              }
            }
          }
        } catch (SocketException e) {
          e.printStackTrace();
        }
      } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//????????????????????????
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        //???????????????int????????????????????????
        String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//??????IPV4??????
        return ipAddress;
      }
    } else {
      //?????????????????????,???????????????????????????
    }
    return null;
  }
  /**
   * ????????????int?????????IP?????????String??????
   * @param ip
   * @return
   */
  String intIP2StringIP(int ip) {
    return (ip & 0xFF) + "." +
        ((ip >> 8) & 0xFF) + "." +
        ((ip >> 16) & 0xFF) + "." +
        (ip >> 24 & 0xFF);
  }
}

