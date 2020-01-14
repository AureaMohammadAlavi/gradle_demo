package com.manning.gia.todo.model


class ToDoItem extends Ordered[ToDoItem] {
  var id: Long = _
  var name: String = _
  var completed: Boolean = _

  def getId: Long = {
    id
  }

  def setId(id: Long) = {
    this.id = id
  }

  def getName: String = {
    name
  }

  def setName(name: String) = {
    this.name = name
  }

  def isCompleted: Boolean = {
    completed
  }

  def setCompleted(completed: Boolean) = {
    this.completed = completed
  }


  override def compare(that: ToDoItem): Int = this.id compare that.id

  override def equals(obj: Any): Boolean = {
    obj match {
      case t: ToDoItem => t.id == id && t.name == name && t.completed == completed
      case _ => false
    }
  }
}