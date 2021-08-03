package toby3.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class CalcSumTest {

  @Test
  public void sumOfNumbers() throws IOException {
    FileReadTemplate fileReadTemplate = new FileReadTemplate();
    Calculator calculator = new Calculator(fileReadTemplate);
    int sum = calculator.calcSum(getClass().getResource("/numbers.txt").getPath());
    assertThat(sum).isEqualTo(10);
  }


}
