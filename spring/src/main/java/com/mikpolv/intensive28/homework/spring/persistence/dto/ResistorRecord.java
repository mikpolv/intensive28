package com.mikpolv.intensive28.homework.spring.persistence.dto;

public record ResistorRecord(
    Integer id,
    String name,
    String partNumber,
    String brandName,
    Long resistance,
    Integer voltage) {}
