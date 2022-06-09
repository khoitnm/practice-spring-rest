package org.tnmk.practicejson.pro03uploadfile.pro03_01_simple_upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tnmk.practicejson.pro03uploadfile.common.Item;
import org.tnmk.practicejson.pro03uploadfile.common.UploadedFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class Pro03_01_SimpleUploadController {

  private final Pro03_01_ItemsReader itemsReader;

  @PostMapping("/upload-items")
  public UploadedFile handleFileUpload(@RequestParam("file") MultipartFile file) {
    try (InputStream inputStream = file.getInputStream()) {
      List<Item> items = itemsReader.readItems(inputStream);
      UploadedFile uploadedFile = new UploadedFile(file.getOriginalFilename(), file.getContentType(), items);
      return uploadedFile;
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read inputstream from file " + file);
    }
  }
}
