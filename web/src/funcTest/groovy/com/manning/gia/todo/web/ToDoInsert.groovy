package com.manning.gia.todo.web

import geb.Page

class ToDoInsert extends Page {
    static url = "todo/insert"
    static at = { title == "To Do application" }
}