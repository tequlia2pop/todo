package com.gmail.tequlia2pop.todo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 表示一组操作命令的枚举类。
 * 
 * @author tequlia2pop
 */
public enum CommandLineInput {
	FIND_ALL('a'), //
	FIND_BY_ID('f'), //
	INSERT('i'), //
	UPDATE('u'), //
	DELETE('d'), //
	EXIT('e');

	private final static Map<Character, CommandLineInput> INPUTS;

	static {
		INPUTS = new HashMap<>();

		for (CommandLineInput input : values()) {
			INPUTS.put(input.getShortCmd(), input);
		}
	}

	public static CommandLineInput getCommandLineInputForInput(char input) {
		return INPUTS.get(input);
	}

	private final char shortCmd;

	private CommandLineInput(char shortCmd) {
		this.shortCmd = shortCmd;
	}

	public char getShortCmd() {
		return shortCmd;
	}
}