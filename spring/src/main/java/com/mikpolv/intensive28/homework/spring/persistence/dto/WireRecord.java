package com.mikpolv.intensive28.homework.spring.persistence.dto;

import java.math.BigDecimal;

public record WireRecord(
    Integer id,
    String name,
    String partNumber,
    String brandName,
    String awg,
    BigDecimal outsideDiameter) {}
