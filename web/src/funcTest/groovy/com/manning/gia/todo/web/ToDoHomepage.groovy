package com.manning.gia.todo.web


import geb.Page

class ToDoHomepage extends Page {
    static url = "todo"
    static at = { title == "To Do application" }
}