package com.manning.gia.todo.web;

import com.manning.gia.todo.model.ToDoItem;
import com.manning.gia.todo.repository.H2ToDoRepository;
import com.manning.gia.todo.repository.ToDoRepository;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToDoServlet extends HttpServlet {

  public static final String FIND_ALL_SERVLET_PATH = "/all";
  public static final String INDEX_PAGE = "/jsp/todo-list.jsp";
  private final ToDoRepository toDoRepository = new H2ToDoRepository();

  private static String getVersion() throws IOException {
    try (InputStream resourceAsStream = ToDoServlet.class.getClassLoader().getResourceAsStream(
        ToDoServlet.class.getPackage().getName().replace(".", "/") + "/version.properties")) {
      Properties properties = new Properties();
      properties.load(Objects.requireNonNull(resourceAsStream));
      return properties.getProperty("version");
    }
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String servletPath = request.getServletPath();
    String view = processRequest(servletPath, request);

    if (view != null) {
      RequestDispatcher dispatcher = request.getRequestDispatcher(view);
      dispatcher.forward(request, response);
    }
  }

  private String processRequest(String servletPath, HttpServletRequest request) throws IOException {
    switch (servletPath) {
      case FIND_ALL_SERVLET_PATH:
        return handleAll(request);
      case "/active":
        return handleStatus(request, true, "active");
      case "/completed":
        return handleStatus(request, false, "completed");
      case "/insert":
        return handleInsert(request);
      case "/update":
        return handleUpdate(request);
      case "/delete":
        return handleDelete(request);
      case "/toggleStatus":
        return handleToggle(request);
      case "/clearCompleted":
        return handleClear(request);
      default:
        return FIND_ALL_SERVLET_PATH;
    }

  }

  private String handleClear(HttpServletRequest request) {
    List<ToDoItem> toDoItems = toDoRepository.findAll();

    for (ToDoItem toDoItem : toDoItems) {
      if (toDoItem.isCompleted()) {
        toDoRepository.delete(toDoItem);
      }
    }

    return "/" + request.getParameter("filter");
  }

  private String handleToggle(HttpServletRequest request) {
    ToDoItem toDoItem = toDoRepository.findById(Long.parseLong(request.getParameter("id")));

    if (toDoItem != null) {
      toDoItem.setCompleted("on".equals(request.getParameter("toggle")));
      toDoRepository.update(toDoItem);
    }

    return "/" + request.getParameter("filter");
  }

  private String handleDelete(HttpServletRequest request) {
    ToDoItem toDoItem = toDoRepository.findById(Long.parseLong(request.getParameter("id")));

    if (toDoItem != null) {
      toDoRepository.delete(toDoItem);
    }

    return "/" + request.getParameter("filter");
  }

  private String handleUpdate(HttpServletRequest request) {
    ToDoItem toDoItem = toDoRepository.findById(Long.parseLong(request.getParameter("id")));

    if (toDoItem != null) {
      toDoItem.setName(request.getParameter("name"));
      toDoRepository.update(toDoItem);
    }

    return null;
  }

  private String handleInsert(HttpServletRequest request) {
    ToDoItem toDoItem = new ToDoItem();
    toDoItem.setName(request.getParameter("name"));
    toDoRepository.insert(toDoItem);
    return "/" + request.getParameter("filter");
  }

  private String handleStatus(HttpServletRequest request, boolean active, String filter)
      throws IOException {
    List<ToDoItem> toDoItems = toDoRepository.findAll();
    request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, active));
    request.setAttribute("stats", determineStats(toDoItems));
    request.setAttribute("filter", filter);
    request.setAttribute("version", getVersion());
    return INDEX_PAGE;
  }

  private String handleAll(HttpServletRequest request) throws IOException {
    List<ToDoItem> toDoItems = toDoRepository.findAll();
    request.setAttribute("toDoItems", toDoItems);
    request.setAttribute("stats", determineStats(toDoItems));
    request.setAttribute("filter", "all");
    request.setAttribute("version", getVersion());
    return INDEX_PAGE;
  }

  private List<ToDoItem> filterBasedOnStatus(List<ToDoItem> toDoItems, boolean active) {
    List<ToDoItem> filteredToDoItems = new ArrayList<>();

    for (ToDoItem toDoItem : toDoItems) {
      if (toDoItem.isCompleted() != active) {
        filteredToDoItems.add(toDoItem);
      }
    }

    return filteredToDoItems;
  }

  private ToDoListStats determineStats(List<ToDoItem> toDoItems) {
    ToDoListStats toDoListStats = new ToDoListStats();

    for (ToDoItem toDoItem : toDoItems) {
      if (toDoItem.isCompleted()) {
        toDoListStats.addCompleted();
      } else {
        toDoListStats.addActive();
      }
    }

    return toDoListStats;
  }

  public static class ToDoListStats {

    private int active;
    private int completed;

    private void addActive() {
      active++;
    }

    private void addCompleted() {
      completed++;
    }

    public int getActive() {
      return active;
    }

    public int getCompleted() {
      return completed;
    }

    public int getAll() {
      return active + completed;
    }
  }
}