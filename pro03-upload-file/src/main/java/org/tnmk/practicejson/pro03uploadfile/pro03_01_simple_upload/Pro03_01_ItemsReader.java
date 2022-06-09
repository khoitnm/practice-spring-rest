package org.tnmk.practicejson.pro03uploadfile.pro03_01_simple_upload;

import org.springframework.stereotype.Service;
import org.tnmk.practicejson.pro03uploadfile.common.Item;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Pro03_01_ItemsReader {
  public List<Item> readItems(InputStream inputStream) {
    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

    // skip the header of the csv
    List<Item> items = br.lines().skip(1).map(line -> toItem(line)).collect(Collectors.toList());
    return items;
  }

  private Item toItem(String line) {
    String[] lineParts = line.split(",");
    return new Item(Integer.valueOf(lineParts[0]), lineParts[1]);
  }
}
