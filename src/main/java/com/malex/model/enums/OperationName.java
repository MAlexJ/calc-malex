package com.malex.model.enums;

/**
 *
 */
public enum OperationName {

    ADD("add"),
    SUBTRACTION("subtraction"),
    DIVISION("division"),
    MULTIPLICATION("multiplication");

    private String description;

    OperationName(String description) {
        this.description = description;
    }

    public static OperationName get(String description) {
        for (OperationName name : OperationName.values()) {
            if (name.description.equals(description)) {
                return name;
            }
        }
        return null; //TODO NPE !!!
    }
}
