package com.manning.gia.todo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class InMemoryToDoRepositoryIntegrationTest {

  @Test
  public void testFindAllCompletedReturnsEmpty() {
    InMemoryToDoRepository inMemoryToDoRepository = new InMemoryToDoRepository();
    assertThat(inMemoryToDoRepository.findAllCompleted()).isEmpty();
  }

  @Test
  public void testFindAllActiveReturnsEmpty() {
    InMemoryToDoRepository inMemoryToDoRepository = new InMemoryToDoRepository();
    assertThat(inMemoryToDoRepository.findAllActive()).isEmpty();
  }

}
