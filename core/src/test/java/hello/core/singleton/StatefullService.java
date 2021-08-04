package hello.core.singleton;

public class StatefullService {

  private int price;//상태를 가지는 싱글톤 빈

  public void order1(String name, int price) {
    System.out.println("order1 : name = " + name + " , price = " + price);
    this.price = price;
  }

  public int order2(String name, int price) {
    System.out.println("order2 : name = " + name + " , price = " + price);
    return price;
  }
  public int getPrice() {
    return price;
  }
}
