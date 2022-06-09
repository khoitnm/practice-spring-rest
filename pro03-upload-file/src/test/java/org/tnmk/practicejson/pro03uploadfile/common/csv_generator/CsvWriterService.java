package org.tnmk.practicejson.pro03uploadfile.common.csv_generator;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.tnmk.practicejson.pro03uploadfile.common.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvWriterService {
  public void writeItemsToCsvFile(List<Item> items, String fileAbsPath) throws IOException {
    List<String[]> lines = toLines(items);
    writeLinesToCsvFile(lines, fileAbsPath);
  }

  private List<String[]> toLines(List<Item> items) {
    return items.stream().map(item -> toLine(item)).collect(Collectors.toList());
  }

  private String[] toLine(Item item) {
    return new String[] { "" + item.getId(), item.getName() };
  }

  public void writeLinesToCsvFile(List<String[]> lines, String fileAbsPath) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(fileAbsPath))) {
      writer.writeAll(lines);
    }
  }

}
