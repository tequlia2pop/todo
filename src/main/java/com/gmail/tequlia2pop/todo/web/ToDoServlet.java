package com.gmail.tequlia2pop.todo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.tequlia2pop.todo.model.ToDoItem;
import com.gmail.tequlia2pop.todo.repository.InMemoryToDoRepository;
import com.gmail.tequlia2pop.todo.repository.ToDoRepository;

/**
 * 负责接收 HTTP 请求，执行一个映射到某个 URL 的增删改查操作，并将请求转发到一个 JSP 页面。
 * 
 * @author tequlia2pop
 */
public class ToDoServlet extends HttpServlet {
	
	public static final String FIND_ALL_SERVLET_PATH = "/all";
	
	public static final String INDEX_PAGE = "/jsp/todo-list.jsp";
	
	private ToDoRepository toDoRepository = new InMemoryToDoRepository();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的 URL 路径，路径以 / 开头
		String servletPath = request.getServletPath();
		String view = processRequest(servletPath, request);
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		// 从 Servlet 转发请求到 JSP
		dispatcher.forward(request, response);
	}

	private String processRequest(String servletPath, HttpServletRequest request) {
		// 为每个映射 URL 实现 CRUD 操作
		if (servletPath.equals(FIND_ALL_SERVLET_PATH)) {
			List<ToDoItem> toDoItems = toDoRepository.findAll();
			request.setAttribute("toDoItems", toDoItems);
			request.setAttribute("stats", determineStats(toDoItems));
			request.setAttribute("filter", "all");
			return INDEX_PAGE;
		} else if (servletPath.equals("/active")) {
			List<ToDoItem> toDoItems = toDoRepository.findAll();
			request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, true));
			request.setAttribute("stats", determineStats(toDoItems));
			request.setAttribute("filter", "active");
			return INDEX_PAGE;
		} else if (servletPath.equals("/completed")) {
			List<ToDoItem> toDoItems = toDoRepository.findAll();
			request.setAttribute("toDoItems", filterBasedOnStatus(toDoItems, false));
			request.setAttribute("stats", determineStats(toDoItems));
			request.setAttribute("filter", "completed");
			return INDEX_PAGE;
		}
		
		if (servletPath.equals("/insert")) {
			ToDoItem toDoItem = new ToDoItem();
			toDoItem.setName(request.getParameter("name"));
			toDoRepository.insert(toDoItem);
			return "/" + request.getParameter("filter");
		} else if (servletPath.equals("/update")) {
			ToDoItem toDoItem = toDoRepository.findById(Long.parseLong(request.getParameter("id")));
			if (toDoItem != null) {
				toDoItem.setName(request.getParameter("name"));
				toDoRepository.update(toDoItem);
			}
			return "/" + request.getParameter("filter");
		} else if (servletPath.equals("/delete")) {
			ToDoItem toDoItem = toDoRepository.findById(Long.parseLong(request.getParameter("id")));
			if (toDoItem != null) {
				toDoRepository.delete(toDoItem);
			}
			return "/" + request.getParameter("filter");
		} else if (servletPath.equals("/toggleStatus")) {
			ToDoItem toDoItem = toDoRepository.findById(Long.parseLong(request.getParameter("id")));
			if (toDoItem != null) {
				boolean completed = "on".equals(request.getParameter("toggle")) ? true : false;
				toDoItem.setCompleted(completed);
				toDoRepository.update(toDoItem);
			}
			return "/" + request.getParameter("filter");
		} else if (servletPath.equals("/clearCompleted")) {
			List<ToDoItem> toDoItems = toDoRepository.findAll();
			for (ToDoItem toDoItem : toDoItems) {
				if (toDoItem.isCompleted()) {
					toDoRepository.delete(toDoItem);
				}
			}
			return "/" + request.getParameter("filter");
		}

		// 当输入的请求 URL 不满足任何一种处理方式时，则跳转到 /all
		return FIND_ALL_SERVLET_PATH;
	}

	private ToDoListStats determineStats(List<ToDoItem> toDoItems) {
		ToDoListStats toDoListStats = new ToDoListStats();

		for (ToDoItem toDoItem : toDoItems) {
			if (toDoItem.isCompleted()) {
				toDoListStats.addCompleted();
			} else {
				toDoListStats.addActive();
			}
		}

		return toDoListStats;
	}

	/**
	 * 基于状态对项目进行删选。
	 * 
	 * @param toDoItems
	 * @param active
	 * @return
	 */
	private List<ToDoItem> filterBasedOnStatus(List<ToDoItem> toDoItems, boolean active) {
		List<ToDoItem> filteredToDoItems = new ArrayList<>();

		for (ToDoItem toDoItem : toDoItems) {
			if (toDoItem.isCompleted() != active) {
				filteredToDoItems.add(toDoItem);
			}
		}

		return filteredToDoItems;
	}

	/**
	 * to-do 列表统计。
	 */
	public class ToDoListStats {
		/**
		 * 活动的项目数。
		 */
		private int active;
		/**
		 * 完成的项目数。
		 */
		private int completed;

		public int getActive() {
			return active;
		}

		public int getCompleted() {
			return completed;
		}

		public int getAll() {
			return active + completed;
		}

		private void addActive() {
			active++;
		}

		private void addCompleted() {
			completed++;
		}
	}
}