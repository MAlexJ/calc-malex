package com.malex.model;

import com.malex.exception.impl.NoSuchOperationException;
import com.malex.model.enums.OperationName;
import com.malex.service.Operation;

import java.math.BigDecimal;
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
    public String calculate(String operationName, String numberOne, String numberTwo) {
        OperationName name = OperationName.get(operationName);
        if (name == null) {
            throw new NoSuchOperationException("Invalid operation name !");
        }

        Operation operation = operations.get(name);
        BigDecimal result = operation.execute(new BigDecimal(numberOne), new BigDecimal(numberTwo));
        return result.stripTrailingZeros().toPlainString();
    }

}
