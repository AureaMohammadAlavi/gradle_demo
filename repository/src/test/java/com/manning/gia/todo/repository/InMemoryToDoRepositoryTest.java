package com.manning.gia.todo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.manning.gia.todo.model.ToDoItem;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class InMemoryToDoRepositoryTest {

  @Test
  public void testInsertAndFindById() {
    ToDoItem item = new ToDoItem();
    item.setName("do something");

    InMemoryToDoRepository inMemoryToDoRepository = new InMemoryToDoRepository();
    long id = inMemoryToDoRepository.insert(item);
    ToDoItem byId = inMemoryToDoRepository.findById(id);

    SoftAssertions assertions = new SoftAssertions();
    assertions.assertThat(byId.getName()).isEqualTo(item.getName());
    assertions.assertThat(byId.getId()).isEqualTo(id);
    assertions.assertThat(byId.isCompleted()).isEqualTo(item.isCompleted());
    assertions.assertAll();
  }

  @Test
  public void testFindAllActiveReturnsEmpty() {
    InMemoryToDoRepository inMemoryToDoRepository = new InMemoryToDoRepository();
    assertThat(inMemoryToDoRepository.findAllActive()).isEmpty();
  }

}
