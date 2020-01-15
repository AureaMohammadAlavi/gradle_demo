package com.manning.gia.todo.web

import geb.Page

class ToDoInsert extends Page {
    static url = "http://jenkins-blueocean:8090/todo/insert"
    static at = { title == "To Do application" }
}