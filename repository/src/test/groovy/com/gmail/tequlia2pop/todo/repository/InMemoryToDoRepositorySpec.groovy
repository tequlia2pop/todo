package com.gmail.tequlia2pop.todo.repository

import com.gmail.tequlia2pop.todo.model.ToDoItem

import spock.lang.Specification

class InMemoryToDoRepositorySpec extends Specification {
	def "Insert To Do item"() {
		setup:
		ToDoRepository inMemoryToDoRepository = new InMemoryToDoRepository()
		when:
		ToDoItem newToDoItem = new ToDoItem();
		newToDoItem.name = "Write unit tests"
		Long newId = inMemoryToDoRepository.insert(newToDoItem)
		newToDoItem.id = newId
		then:
		ToDoItem persistedToDoItem = inMemoryToDoRepository.findById(newId)
		newId != null
		persistedToDoItem != null
		newToDoItem == persistedToDoItem
	}
}