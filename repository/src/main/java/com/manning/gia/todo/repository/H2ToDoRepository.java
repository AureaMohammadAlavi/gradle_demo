package com.manning.gia.todo.repository;

import com.manning.gia.todo.model.ToDoItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class H2ToDoRepository implements ToDoRepository {

  @Override
  public List<ToDoItem> findAll() {
    return getToDoItems("SELECT id, name, completed from todo_item");
  }

  private List<ToDoItem> getToDoItems(String sql) {
    List<ToDoItem> toDoItems = new ArrayList<>();

    try (Connection conn = createConnection()) {
      try (Statement stmt = conn.createStatement()) {
        try (ResultSet rs = stmt.executeQuery(sql)) {
          while (rs.next()) {
            toDoItems.add(extractToDoItem(rs));
          }
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new DatabaseException(e.getMessage(), e);
    }
    return toDoItems;
  }

  @Override
  public List<ToDoItem> findAllActive() {
    return getToDoItems("SELECT id, name, completed FROM todo_item WHERE completed = 0");
  }

  @Override
  public List<ToDoItem> findAllCompleted() {
    return getToDoItems("SELECT id, name, completed FROM todo_item WHERE completed = 1");
  }

  private static ToDoItem extractToDoItem(ResultSet rs) throws SQLException {
    ToDoItem toDoItem = new ToDoItem();
    toDoItem.setId(rs.getLong("id"));
    toDoItem.setName(rs.getString("name"));
    toDoItem.setCompleted(rs.getBoolean("completed"));
    return toDoItem;
  }

  @Override
  public ToDoItem findById(Long id) {

    try (Connection conn = createConnection()) {
      try (PreparedStatement stmt = conn
          .prepareStatement("SELECT id, name, completed from todo_item WHERE id = ?")) {
        stmt.setLong(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            return extractToDoItem(rs);
          }
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new DatabaseException(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public Long insert(ToDoItem toDoItem) {

    try (Connection conn = createConnection()) {
      try (PreparedStatement stmt = conn
          .prepareStatement("INSERT INTO todo_item (name, completed) VALUES (?,?)",
              Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, toDoItem.getName());
        stmt.setBoolean(2, toDoItem.isCompleted());
        stmt.executeUpdate();
        try (ResultSet rs = stmt.getGeneratedKeys()) {
          if (rs.next()) {
            return rs.getLong(1);
          }
        }
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new DatabaseException(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public void update(ToDoItem toDoItem) {
    try (Connection conn = createConnection()) {
      try (PreparedStatement stmt = conn
          .prepareStatement("UPDATE todo_item SET name = ?, completed = ? where id = ?")) {
        stmt.setString(1, toDoItem.getName());
        stmt.setBoolean(2, toDoItem.isCompleted());
        stmt.setLong(3, toDoItem.getId());
        stmt.executeUpdate();
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new DatabaseException(e.getMessage(), e);
    }
  }

  @Override
  public void delete(ToDoItem toDoItem) {
    try (Connection conn = createConnection()) {
      try (PreparedStatement stmt = conn
          .prepareStatement("DELETE FROM todo_item WHERE id = ?")) {
        stmt.setLong(1, toDoItem.getId());
        stmt.executeUpdate();
      }
    } catch (SQLException | ClassNotFoundException e) {
      throw new DatabaseException(e.getMessage(), e);
    }
  }

  private Connection createConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.h2.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:h2:~/todo", "sa", "");
    return DriverManager
        .getConnection("jdbc:h2:tcp://localhost:1300/~/todo", "sa", "");
  }
}