package cn.ololee.create3dgallery.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface PictureDao {

  @Insert
  void insertPicture(PictureEnity picture);

  @Delete
  void deletePicture(PictureEnity picture);

  @Update
  void updatePicture(PictureEnity picture);

  @Query("SELECT * FROM PictureEnity")
  List<PictureEnity> getAllPictures();

  @Query("SELECT * FROM PictureEnity WHERE id = :id")
  PictureEnity getPictureById(int id);

  @Query("SELECT * FROM PictureEnity order by id DESC limit 1 ")
  PictureEnity getLastPicture();
}
