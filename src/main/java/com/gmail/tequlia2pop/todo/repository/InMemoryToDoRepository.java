package com.gmail.tequlia2pop.todo.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.gmail.tequlia2pop.todo.model.ToDoItem;

/**
 * 基于内存的仓库实现类，它将所有的 to-do 项目放在 ConcurrentHashMap 实例中。
 * 
 * @author tequlia2pop
 */
public class InMemoryToDoRepository implements ToDoRepository {
	
	/**
	 * 线程安全的标识符序列号。
	 */
    private AtomicLong currentId = new AtomicLong();
    
    /**
     * 用来保存 to-do 项目的有效内存结构。
     */
    private ConcurrentMap<Long, ToDoItem> toDos = new ConcurrentHashMap<>();

    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> toDoItems = new ArrayList<>(toDos.values());
        Collections.sort(toDoItems);
        return toDoItems;
    }

    @Override
    public ToDoItem findById(Long id) {
        return toDos.get(id);
    }

    @Override
    public Long insert(ToDoItem toDoItem) {
        Long id = currentId.incrementAndGet();
        toDoItem.setId(id);
        toDos.putIfAbsent(id, toDoItem);
        return id;
    }

    @Override
    public void update(ToDoItem toDoItem) {
        toDos.replace(toDoItem.getId(), toDoItem);
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        toDos.remove(toDoItem.getId());
    }
}