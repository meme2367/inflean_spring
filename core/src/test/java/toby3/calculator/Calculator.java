package toby3.calculator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

  public int calcSum(String path) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(path));
    Integer sum = 0;
    String line = null;
    while((line = br.readLine()) != null) {
      sum += Integer.valueOf(line);
    }

    br.close();
    return sum;
  }
}
