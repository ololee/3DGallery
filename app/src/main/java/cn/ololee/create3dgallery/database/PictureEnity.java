package cn.ololee.create3dgallery.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PictureEnity {

  @PrimaryKey
  @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
  public int id;

  @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
  public String name;

  @ColumnInfo(name = "path", typeAffinity = ColumnInfo.TEXT)
  public String path;


  public PictureEnity(int id, String name, String path) {
    this.id = id;
    this.name = name;
    this.path = path;
  }
}
