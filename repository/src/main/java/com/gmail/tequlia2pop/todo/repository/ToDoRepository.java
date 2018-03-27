package com.gmail.tequlia2pop.todo.repository;

import java.util.List;

import com.gmail.tequlia2pop.todo.model.ToDoItem;

/**
 * 仓库类接口。
 * 
 * @author tequlia2pop
 */
public interface ToDoRepository {
	List<ToDoItem> findAll();
	
	List<ToDoItem> findAllActive();

    List<ToDoItem> findAllCompleted();

	ToDoItem findById(Long id);

	Long insert(ToDoItem toDoItem);

	void update(ToDoItem toDoItem);

	void delete(ToDoItem toDoItem);
}