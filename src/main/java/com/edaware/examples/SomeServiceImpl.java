package com.edaware.examples;

import static java.util.Objects.requireNonNull;

public class SomeServiceImpl implements SomeService {
  private SomeDAO someDAO = null;

  @Override
  public int insert(String value) {
    requireNonNull(value, "value may not be null");

    if (someDAO.get().anyMatch(v -> value.equals(v)))
      return 0;

    return someDAO.insert(value);
  }

  public void setSomeDAO(SomeDAO dao) {
    someDAO = dao;
  }
}
