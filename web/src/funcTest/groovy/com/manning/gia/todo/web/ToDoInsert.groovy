package com.manning.gia.todo.web

import geb.Page

class ToDoInsert extends Page {
    static url = "http://localhost:8090/todo/insert"
    static at = { title == "To Do application" }
}