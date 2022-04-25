package cn.ololee.create3dgallery;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import cn.ololee.create3dgallery.adapter.GallerChooseAdapter;
import cn.ololee.create3dgallery.databinding.ActivityMainBinding;
import cn.ololee.create3dgallery.entry.Photo;
import cn.ololee.create3dgallery.utils.PermissionUtils;
import cn.ololee.create3dgallery.utils.PhotoRepository;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private ActivityMainBinding binding;
  private GallerChooseAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    binding.chooseCount.setText(String.format(getString(R.string.choose_count), 0));
    setContentView(binding.getRoot());

    PermissionUtils.getPermissions(this);

    final List<Photo> photos = PhotoRepository.getPhoto(this);
    adapter = new GallerChooseAdapter(photos);
    adapter.registerChoosedUpdateListener(
        count -> binding.chooseCount.setText(
            String.format(getString(R.string.choose_count), count)));
    binding.grid.setAdapter(adapter);
    binding.grid.setLayoutManager(new GridLayoutManager(this, 3));
    binding.chooseAllBtn.setOnClickListener(v -> {
      adapter.setAllSelectedStatus(binding.chooseAllBtn.isChecked());
    });
    binding.makeBtn.setOnClickListener(v -> {
      int selectedCount = adapter.getSelectedCount();
      if (selectedCount != 0 && selectedCount <= 30) {
        WebActivity.start(this, adapter.getSelectedPhotos());
      }
    });
  }
}