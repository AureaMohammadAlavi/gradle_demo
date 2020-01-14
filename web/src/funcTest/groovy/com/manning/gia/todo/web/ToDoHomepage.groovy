package com.manning.gia.todo.web


import geb.Page

class ToDoHomepage extends Page {
    static url = "http://localhost:8090/todo"
    static at = { title == "To Do application" }
}