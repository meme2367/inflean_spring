package toby.calculator;

public interface LineCallBack<T> {

  T doSomething(T val, String line);
}
