package com.gmail.tequlia2pop.todo.utils;

import com.gmail.tequlia2pop.todo.model.ToDoItem;
import com.gmail.tequlia2pop.todo.repository.InMemoryToDoRepository;
import com.gmail.tequlia2pop.todo.repository.ToDoRepository;

import java.util.Collection;

/**
 * 负责处理用户交互与命令执行。
 * 
 * @author tequlia2pop
 */
public class CommandLineInputHandler {

	private ToDoRepository toDoRepository = new InMemoryToDoRepository();

	/**
	 * 打印所有的菜单选项。
	 */
	public void printOptions() {
		System.out.println("\n--- To Do Application ---");
		System.out.println("Please make a choice:");
		System.out.println("(a)ll items");
		System.out.println("(f)ind a specific item");
		System.out.println("(i)nsert a new item");
		System.out.println("(u)pdate an existing item");
		System.out.println("(d)elete an existing item");
		System.out.println("(e)xit");
	}

	/**
	 * 读取用户的输入。
	 * 
	 * @return
	 */
	public String readInput() {
		return System.console().readLine("> ");
	}

	/**
	 * 根据用户的输入进行相应的处理。
	 * 
	 * @param input
	 */
	public void processInput(CommandLineInput input) {
		if (input == null) {
			handleUnknownInput();
		} else {
			switch (input) {
			case FIND_ALL:
				printAllToDoItems();
				break;
			case FIND_BY_ID:
				printToDoItem();
				break;
			case INSERT:
				insertToDoItem();
				break;
			case UPDATE:
				updateToDoItem();
				break;
			case DELETE:
				deleteToDoItem();
				break;
			case EXIT:
				break;
			default:
				handleUnknownInput();
			}
		}
	}

	private void printAllToDoItems() {
		Collection<ToDoItem> toDoItems = toDoRepository.findAll();

		if (toDoItems.isEmpty()) {
			System.out.println("Nothing to do. Go relax!");
		} else {
			for (ToDoItem toDoItem : toDoItems) {
				System.out.println(toDoItem);
			}
		}
	}

	private void printToDoItem() {
		ToDoItem toDoItem = findToDoItem();
		if (toDoItem != null) {
			System.out.println(toDoItem);
		}
	}

	private void insertToDoItem() {
		ToDoItem toDoItem = askForNewToDoAction();
		Long id = toDoRepository.insert(toDoItem);
		System.out.println("Successfully inserted to do item with ID " + id + ".");
	}

	private void updateToDoItem() {
		ToDoItem toDoItem = findToDoItem();
		if (toDoItem != null) {
			System.out.println(toDoItem);
			System.out.println("Please enter the name of the item:");
			toDoItem.setName(readInput());
			System.out.println("Please enter the done status the item:");
			toDoItem.setCompleted(Boolean.parseBoolean(readInput()));
			toDoRepository.update(toDoItem);
			System.out.println("Successfully updated to do item with ID " + toDoItem.getId() + ".");
		}
	}

	private void deleteToDoItem() {
		ToDoItem toDoItem = findToDoItem();
		if (toDoItem != null) {
			toDoRepository.delete(toDoItem);
			System.out.println("Successfully deleted to do item with ID " + toDoItem.getId() + ".");
		}
	}

	private void handleUnknownInput() {
		System.out.println("Please select a valid option!");
	}

	private ToDoItem findToDoItem() {
		Long id = askForItemId();
		ToDoItem toDoItem = toDoRepository.findById(id);
		if (toDoItem == null) {
			System.err.println("To do item with ID " + id + " could not be found.");
		}
		return toDoItem;
	}

	private Long askForItemId() {
		System.out.println("Please enter the item ID:");
		String input = readInput();
		return Long.parseLong(input);
	}

	private ToDoItem askForNewToDoAction() {
		ToDoItem toDoItem = new ToDoItem();
		System.out.println("Please enter the name of the item:");
		toDoItem.setName(readInput());
		return toDoItem;
	}
}