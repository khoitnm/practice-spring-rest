package org.tnmk.practicejson.pro03uploadfile.common.csv_generator;

import org.junit.jupiter.api.Test;
import org.tnmk.practicejson.pro03uploadfile.common.Item;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneratingBigFilesApplication {
  private final CsvWriterService csvWriterService = new CsvWriterService();

  @Test
  public void execute() throws IOException {
    int itemsCount = 90000;
    List<Item> items = generateItems(itemsCount);
    csvWriterService.writeItemsToCsvFile(items, "C:\\dev\\workspace\\personal\\practice-spring-rest\\pro03-upload-file\\support\\manual-test\\test-data\\Items_" + itemsCount + ".csv");
  }

  private List<Item> generateItems(int itemsCount) {
    return IntStream.range(0, itemsCount).mapToObj(i -> new Item(i, "Item " + i)).collect(Collectors.toList());
  }
}
