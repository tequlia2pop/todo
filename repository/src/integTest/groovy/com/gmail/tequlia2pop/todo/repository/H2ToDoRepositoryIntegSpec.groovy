package com.gmail.tequlia2pop.todo.repository

import com.gmail.tequlia2pop.todo.model.ToDoItem

import spock.lang.Specification

class H2ToDoRepositoryIntegSpec extends Specification {
	def "Insert To Do item"() {
		setup:
		ToDoRepository h2ToDoRepository = new H2ToDoRepository()
		when:
		ToDoItem newToDoItem = new ToDoItem();
		newToDoItem.name = "Write integration tests"
		Long newId = h2ToDoRepository.insert(newToDoItem)
		newToDoItem.id = newId
		then:
		ToDoItem persistedToDoItem = h2ToDoRepository.findById(newId)
		newId != null
		persistedToDoItem != null
		newToDoItem == persistedToDoItem
	}
}