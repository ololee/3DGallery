package cn.ololee.launcherlocalweb;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import cn.ololee.launcherlocalweb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
  private ActivityMainBinding binding;
  private MyWebViewClient webViewClient;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    FullScreenUtils.fullScreen(this,true);
    binding.web.getSettings().setJavaScriptEnabled(true);
    webViewClient = new MyWebViewClient();
    binding.web.setWebViewClient(webViewClient);
    binding.web.loadUrl("http://localhost:9999/tractor.html");
  }
}