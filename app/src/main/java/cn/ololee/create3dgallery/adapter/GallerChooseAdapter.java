package cn.ololee.create3dgallery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import cn.ololee.create3dgallery.databinding.ItemGridImageBinding;
import cn.ololee.create3dgallery.entry.Photo;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class GallerChooseAdapter extends RecyclerView.Adapter<GallerChooseAdapter.ViewHolder> {
  private List<Photo> photos;
  private int selectedCount = 0;
  private ChoosedUpdateListener choosedUpdateListener;

  public GallerChooseAdapter(List<Photo> photos) {
    this.photos = photos;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    ItemGridImageBinding binding =
        ItemGridImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new ViewHolder(binding);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    Photo photo = photos.get(holder.getAdapterPosition());
    Glide.with(holder.binding.getRoot().getContext())
        .load(photo.getPath())
        .centerCrop()
        .into(holder.binding.image);
    holder.binding.chooseBtn.setChecked(photo.isSelected());
    holder.binding.getRoot().setOnClickListener(v -> {
      photo.setSelected(!holder.binding.chooseBtn.isChecked());
      if (photo.isSelected()) {
       setSelectedCount(getSelectedCount() + 1);
      } else {
        setSelectedCount(getSelectedCount() - 1);
      }
      notifyItemChanged(holder.getAdapterPosition());
    });
  }

  @Override public int getItemCount() {
    return photos != null ? photos.size() : 0;
  }

  public void setAllSelectedStatus(boolean isSelect) {
    for (Photo photo : photos) {
      photo.setSelected(isSelect);
    }
    if (isSelect) {
      setSelectedCount(photos.size());
    } else {
      setSelectedCount(0);
    }
    notifyDataSetChanged();
  }

  public int getSelectedCount() {
    return selectedCount;
  }

  public void registerChoosedUpdateListener(ChoosedUpdateListener choosedUpdateListener) {
    this.choosedUpdateListener = choosedUpdateListener;
  }

  public void setSelectedCount(int count) {
    selectedCount = count;
    if(choosedUpdateListener != null) {
      choosedUpdateListener.onChoosedUpdate(selectedCount);
    }
  }

  public List<Photo> getSelectedPhotos() {
    List<Photo> selectedPhotos = new ArrayList<>();
    for (Photo photo : photos) {
      if (photo.isSelected()) {
        selectedPhotos.add(photo);
      }
    }
    return selectedPhotos;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemGridImageBinding binding;

    public ViewHolder(ItemGridImageBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  public interface ChoosedUpdateListener {
    void onChoosedUpdate(int count);
  }
}

