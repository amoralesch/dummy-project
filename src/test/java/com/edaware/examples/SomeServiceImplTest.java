package com.edaware.examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SomeServiceImplTest {
  @Mock
  private SomeDAO dao;

  private SomeServiceImpl service = null;

  @BeforeEach
  void setUp() {
    service = new SomeServiceImpl();

    service.setSomeDAO(dao);
  }

  @Test
  void insert_ThrowsException_WhenNullValue() {
    assertThatThrownBy(() -> service.insert(null))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void insert_ReturnsZero_WhenValueAlreadyPresentInDAO() {
    List<String> repo = new ArrayList<>();

    repo.add("uno");
    repo.add("dos");
    repo.add("tres");

    doReturn(repo.stream()).when(dao).get();

    assertThat(service.insert("dos")).isEqualTo(0);

    verify(dao, never()).insert(any());
  }

  @Test
  void insert_PropagatesException_WhenErrorLoadingInfo()
  {
    RuntimeException t = new RuntimeException("get");

    doThrow(t).when(dao).get();

    assertThatThrownBy(() -> service.insert("uno")).isSameAs(t);

    verify(dao, never()).insert(any());
  }

  @Test
  void insert_InsertsRecord_WhenValueNotPresentAlready() {
    List<String> repo = new ArrayList<>();

    repo.add("uno");
    repo.add("tres");

    doReturn(repo.stream()).when(dao).get();
    doReturn(1).when(dao).insert("dos");

    assertThat(service.insert("dos")).isEqualTo(1);
  }

  @Test
  void insert_PropagatesException_WhenErrorInsertingValue()
  {
    RuntimeException t = new RuntimeException("insert");
    List<String> repo = new ArrayList<>();

    repo.add("uno");
    repo.add("tres");

    doReturn(repo.stream()).when(dao).get();
    doThrow(t).when(dao).insert("dos");

    assertThatThrownBy(() -> service.insert("dos")).isSameAs(t);
  }
}
