package org.tnmk.practicejson.pro03uploadfile.pro03_01_simple_upload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.tnmk.practicejson.pro03uploadfile.common.UploadedFile;

@RestController
public class Pro03_01_SimpleUploadController {

  @PostMapping("/upload-items")
  public UploadedFile handleFileUpload(@RequestParam("file") MultipartFile file) {
    UploadedFile uploadedFile = new UploadedFile(file.getOriginalFilename(), file.getContentType(), null);
    return uploadedFile;
  }
}
