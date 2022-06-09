package org.tnmk.practicejson.pro03uploadfile.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadedFile {
  private String fileName;
  private String contentType;
  private List<Item> items;
}
