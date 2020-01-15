package com.manning.gia.todo.web


import geb.Page

class ToDoHomepage extends Page {
    static url = "http://jenkins-blueocean:8090/todo"
    static at = { title == "To Do application" }
}