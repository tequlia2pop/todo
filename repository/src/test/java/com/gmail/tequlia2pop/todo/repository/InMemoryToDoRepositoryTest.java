package com.gmail.tequlia2pop.todo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.gmail.tequlia2pop.todo.model.ToDoItem;

public class InMemoryToDoRepositoryTest {

	private ToDoRepository inMemoryToDoRepository;

	@Before
	public void setUp() {
		inMemoryToDoRepository = new InMemoryToDoRepository();
	}

	@Test
	public void testInsertToDoItem() {
		ToDoItem newToDoItem = new ToDoItem();
		newToDoItem.setName("Write unit tests");
		Long newId = inMemoryToDoRepository.insert(newToDoItem);
		assertNotNull(newId);

		ToDoItem persistedToDoItem = inMemoryToDoRepository.findById(newId);
		assertNotNull(persistedToDoItem);
		assertEquals(newToDoItem, persistedToDoItem);
	}

}
