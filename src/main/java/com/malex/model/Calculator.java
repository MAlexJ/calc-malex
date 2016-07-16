package com.malex.model;


import com.malex.model.enums.OperationName;
import com.malex.service.Operation;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Calculator {

    /**
     * хранение операций.
     */
    private Map<OperationName, Operation> operations;

    /**
     * инициализация объекта.
     */
    public Calculator() {
        this.operations = new HashMap<>();
    }

    /**
     * добавить операцию +, -, /, * ;
     *
     * @param operation оператор соотвествующий конкретной операции: +, -, /, *
     */
    public void addOperation(Operation operation) {
        operations.put(operation.getOperationName(), operation);
    }

    /**
     * Вычисление результата заданной операции двух чисел.
     *
     * @param operationName знак операции
     * @param numberOne     первое число
     * @param numberTwo     второе число
     * @return результат операции над числами
     */
    public int calculate(String operationName, int numberOne, int numberTwo) {
        OperationName name = OperationName.get(operationName);
        if (name == null) {
            throw new IllegalArgumentException("Invalid operation name");
        }
        Operation operation = operations.get(name);

        if (operation == null) {
            throw new IllegalArgumentException("OperationName with this name doesn't exist!");
        }
        return operation.execute(numberOne, numberTwo);
    }

}
