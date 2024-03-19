package com.griddynamics.jsondatabase.server.input;

public class InputValidator {
    private static final String SET_COMMAND_REGEX = "^set\\s([1-9][0-9]*)\\s.+";
    private static final String GET_COMMAND_REGEX = "^get\\s([1-9][0-9]*)$";
    private static final String DELETE_COMMAND_REGEX = "^delete\\s([1-9][0-9]*)$";
    private static final String EXIT_COMMAND_REGEX = "exit";

    public static Boolean isCommandValid(String command) {
        return  command.matches(SET_COMMAND_REGEX) ||
                command.matches(GET_COMMAND_REGEX) ||
                command.matches(DELETE_COMMAND_REGEX) ||
                command.matches(EXIT_COMMAND_REGEX);
    }
}
