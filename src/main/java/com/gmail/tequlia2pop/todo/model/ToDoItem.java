package com.gmail.tequlia2pop.todo.model;

/**
 * 代表 to-do 列表中的一个执行项目。
 * 
 * 注意，该类具有内存的排序功能，但是与 equals 不一致。
 * 
 * @author tequlia2pop
 */
public class ToDoItem implements Comparable<ToDoItem> {

	private Long id;
	
	private String name;
	
	/**
	 * 项目是否已完成。
	 */
	private boolean completed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof ToDoItem))
			return false;

		ToDoItem that = (ToDoItem) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		if (completed != that.completed)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (completed ? 1 : 0);
		return result;
	}

	@Override
	public int compareTo(ToDoItem toDoItem) {
		return this.getId().compareTo(toDoItem.getId());
	}

	@Override
	public String toString() {
		return id + ": " + name + " [completed: " + completed + "]";
	}
}