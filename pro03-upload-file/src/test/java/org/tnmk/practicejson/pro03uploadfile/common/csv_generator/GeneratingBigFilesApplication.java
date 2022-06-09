package org.tnmk.practicejson.pro03uploadfile.common.csv_generator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tnmk.practicejson.pro03uploadfile.common.Item;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneratingBigFilesApplication {
  @Autowired
  CsvWriterService csvWriterService;

  @Test
  public void execute() throws IOException {
    List<Item> items = generateItems(90000);
    csvWriterService.writeItemsToCsvFile(items, "C:\\Users\\trank\\Desktop\\testdata.csv");
  }

  private List<Item> generateItems(int itemsCount) {
    return IntStream.range(0, itemsCount).mapToObj(i -> new Item(i, "Item " + i)).collect(Collectors.toList());
  }
}
