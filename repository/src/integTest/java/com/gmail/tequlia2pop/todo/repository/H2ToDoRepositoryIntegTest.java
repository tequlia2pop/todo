package com.gmail.tequlia2pop.todo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.gmail.tequlia2pop.todo.model.ToDoItem;

public class H2ToDoRepositoryIntegTest {
	
	private ToDoRepository h2ToDoRepository;

	@Before
	public void setUp() {
		h2ToDoRepository = new H2ToDoRepository();
	}

	@Test
	public void testInsertToDoItem() {
		ToDoItem newToDoItem = new ToDoItem();
		newToDoItem.setName("Write integration tests");
		Long newId = h2ToDoRepository.insert(newToDoItem);
		newToDoItem.setId(newId);
		assertNotNull(newId);

		ToDoItem persistedToDoItem = h2ToDoRepository.findById(newId);
		assertNotNull(persistedToDoItem);
		assertEquals(newToDoItem, persistedToDoItem);
	}
}