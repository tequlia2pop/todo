package com.gmail.tequlia2pop.todo;

import org.apache.commons.lang3.CharUtils;

import com.gmail.tequlia2pop.todo.utils.CommandLineInput;
import com.gmail.tequlia2pop.todo.utils.CommandLineInputHandler;

/**
 * 应用程序入口。
 * 
 * @author tequlia2pop
 */
public class ToDoApp {
    public static final char DEFAULT_INPUT = '\u0000';

    public static void main(String args[]) {
        CommandLineInputHandler commandLineInputHandler = new CommandLineInputHandler();
        char command = DEFAULT_INPUT;

        // 只要用户输入 exit 命令，应用就停止运行。
        while (CommandLineInput.EXIT.getShortCmd() != command) {
            commandLineInputHandler.printOptions();
            String input = commandLineInputHandler.readInput();
            command = CharUtils.toChar(input, DEFAULT_INPUT);
            // 输入的单个字符和对应命令对象的映射。
            CommandLineInput commandLineInput = CommandLineInput.getCommandLineInputForInput(command);
            // 执行 CRUD 命令。
            commandLineInputHandler.processInput(commandLineInput);
        }
    }
}