package cn.ololee.create3dgallery.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtils {
  public static List<Integer> getRandomList(int size){
    ArrayList<Integer> result = new ArrayList<>();
    ArrayList<Integer> temp = new ArrayList<>();
    Random random = new Random();
    for (int i = 0; i < size; i++) {
      temp.add(i);
    }
    for (int i = 0; i < size; i++) {
      int anInt = random.nextInt(size - i);
      Integer randomInt = temp.get(anInt);
      result.add(randomInt);
      temp.remove(randomInt);
    }
    return result;
  }
}
