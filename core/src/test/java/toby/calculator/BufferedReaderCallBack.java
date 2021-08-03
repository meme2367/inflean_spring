package toby.calculator;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallBack {

  Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
