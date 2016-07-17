package com.malex.service;

import com.malex.model.enums.OperationName;

import java.math.BigDecimal;

public interface Operation {

    OperationName getOperationName();

    BigDecimal execute(BigDecimal numberOne, BigDecimal numberTwo);
}
