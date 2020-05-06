package com.edaware.examples;

import java.util.stream.Stream;

public interface SomeDAO {
  Stream<String> get();

  int insert(String value);
}
