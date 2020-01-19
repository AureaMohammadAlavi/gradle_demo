package com.manning.gia.todo.web


import geb.junit4.GebReportingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.By
import org.openqa.selenium.Keys

@RunWith(JUnit4)
class ToDoTest extends GebReportingTest {
    @Test
    void theToDoHomepage() {
        to ToDoHomepage

        $(By.id("new-todo")).value 'Write functional tests'
        $(By.id("new-todo")) <<  Keys.ENTER

        waitFor { at ToDoInsert }
    }
}