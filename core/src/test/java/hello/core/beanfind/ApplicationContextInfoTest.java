package hello.core.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationContextInfoTest {

  @Autowired
  ApplicationContext ac;

  @Test
  @DisplayName("모든 빈 출력하기")
  void findAllBean() {
    String[] beanDefinitionNames = ac.getBeanDefinitionNames();

    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + " , object =" + bean);
    }
  }


}
