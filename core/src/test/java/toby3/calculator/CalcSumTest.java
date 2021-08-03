package toby3.calculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcSumTest {

  Calculator calculator;
  String path;

  @BeforeEach
  public void setUp() {
    this.calculator = new Calculator(new Template());
    this.path = getClass().getResource("/numbers.txt").getPath();
  }

  @Test
  public void sumOfNumbers() throws IOException {
    int sum = calculator.calcSum(this.path);
    assertThat(sum).isEqualTo(10);
  }

  @Test
  public void multiplyOfNumbers() throws IOException {
    int mul = calculator.calcMultiply(this.path);
    assertThat(mul).isEqualTo(24);
  }

  @Test
  public void concatenateStrings() throws IOException {
    assertThat(calculator.concatenate(this.path)).isEqualTo("1234");
  }
}
