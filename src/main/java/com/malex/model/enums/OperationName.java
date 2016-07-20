package com.malex.model.enums;

import com.malex.exception.impl.NoSuchOperationException;

/**
 *
 */
public enum OperationName {

    ADD("add"),
    SUBTRACTION("subtraction"),
    DIVISION("division"),
    MULTIPLICATION("multiplication"),
    PERCENT("percent");

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
        throw new NoSuchOperationException("OperationName: \'" + description + "\' with this name doesn't exist!");   //TODO Exception -> NoSuchOperationException + тесты нужны
    }
}
