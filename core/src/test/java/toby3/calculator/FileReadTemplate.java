package toby3.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReadTemplate {

  public Integer fildReadTemplate(String path, BufferedReaderCallBack bufferedReaderCallBack)
      throws IOException {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(path));
      int res = bufferedReaderCallBack.doSomethingWithReader(br);
      return res;
    } catch (IOException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }
}
