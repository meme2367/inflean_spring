package toby3.calculator;

import java.io.IOException;

public class Calculator {

  Template template;

  public Calculator(Template template) {
    this.template = template;
  }

  public int calcSum(String path) throws IOException {
/*
    BufferedReaderCallBack bufferedReaderCallBack = (br) -> {
      Integer sum = 0;
      String line = null;
      while ((line = br.readLine()) != null) {
        sum += Integer.valueOf(line);
      }
      return sum;
    };
    return template.fildReadTemplate(path, bufferedReaderCallBack);

 */
    LineCallBack lineCallBack = (val, line) -> {
      return val + Integer.valueOf(line);
    };
    return template.lineReadTemplate(0, path, lineCallBack);
  }

  public int calcMultiply(String path) throws IOException {
    /*
    BufferedReaderCallBack bufferedReaderCallBack = (br) -> {
      Integer mul = 1;
      String line = null;
      while ((line = br.readLine()) != null) {
        mul *= Integer.valueOf(line);
      }
      return mul;
    };
    return template.fildReadTemplate(path, bufferedReaderCallBack);
     */

    LineCallBack lineCallBack = (val, line) -> {
      return val * Integer.valueOf(line);
    };
    return template.lineReadTemplate(1, path, lineCallBack);
  }
}
