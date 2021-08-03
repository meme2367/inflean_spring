package toby3.calculator;

import java.io.IOException;

public class Calculator {

  FileReadTemplate fileReadTemplate;

  public Calculator(FileReadTemplate fileReadTemplate) {
    this.fileReadTemplate = fileReadTemplate;
  }

  public int calcSum(String path) throws IOException {

    BufferedReaderCallBack bufferedReaderCallBack = (br) -> {
      Integer sum = 0;
      String line = null;
      while ((line = br.readLine()) != null) {
        sum += Integer.valueOf(line);
      }
      return sum;
    };

    return fileReadTemplate.fildReadTemplate(path, bufferedReaderCallBack);
  }

  public int calcMultiply(String path) throws IOException {
    BufferedReaderCallBack bufferedReaderCallBack = (br) -> {
      Integer mul = 1;
      String line = null;
      while ((line = br.readLine()) != null) {
        mul *= Integer.valueOf(line);
      }
      return mul;
    };
    return fileReadTemplate.fildReadTemplate(path, bufferedReaderCallBack);
  }
}
