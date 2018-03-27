package com.gmail.tequlia2pop.todo.repository;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gmail.tequlia2pop.todo.model.ToDoItem;

public class InMemoryToDoRepositoryNGTest {
	
	private ToDoRepository inMemoryToDoRepository;

	@BeforeClass
	public void setUp() {
		inMemoryToDoRepository = new InMemoryToDoRepository();
	}

	@Test
	public void testToDoItem() {
		ToDoItem newToDoItem = new ToDoItem();
		newToDoItem.setName("Write unit tests");
		Long newId = inMemoryToDoRepository.insert(newToDoItem);
		assertNotNull(newId);

		ToDoItem persistedToDoItem = inMemoryToDoRepository.findById(newId);
		assertNotNull(persistedToDoItem);
		assertEquals(newToDoItem, persistedToDoItem);
	}
}
